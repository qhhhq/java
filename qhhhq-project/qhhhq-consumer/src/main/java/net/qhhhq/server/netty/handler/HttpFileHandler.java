package net.qhhhq.server.netty.handler;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpHeaders.Names;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.FileUpload;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder.EndOfDataDecoderException;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;
import net.qhhhq.service.common.HandlerChain;
import net.qhhhq.service.common.impl.HandlerChainFactory;
import net.qhhhq.utils.JSONUtils;

public class HttpFileHandler extends SimpleChannelInboundHandler<HttpObject> {

	private static Logger log = Logger.getLogger(HttpFileHandler.class);

	private static final HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE);
	private HttpRequest request = null;
	private HttpPostRequestDecoder decoder;
	private Map<String, Object> parmMap = new HashMap<String, Object>();
	private JSONObject responseMsg;
	private String filePath;

	public HttpFileHandler() {

	}

	public HttpFileHandler(String filePath) {
		this.filePath = filePath;
		parmMap.put("filePath", filePath);
	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
		if (msg instanceof HttpRequest) {
			request = (HttpRequest) msg;
			if (request.getMethod() == HttpMethod.POST) {
				if (decoder != null) {
					decoder.cleanFiles();
					decoder = null;
				}
				try {
					decoder = new HttpPostRequestDecoder(factory, request);
				} catch (Exception e) {
					e.printStackTrace();
					writeResponse(ctx.channel(), HttpResponseStatus.INTERNAL_SERVER_ERROR, e.toString());
					ctx.channel().close();
					return;
				}
			}
		}
		if (decoder != null && msg instanceof HttpContent) {
			HttpContent chunk = (HttpContent) msg;
			try {
				decoder.offer(chunk);
			} catch (Exception e) {
				e.printStackTrace();
				writeResponse(ctx.channel(), HttpResponseStatus.INTERNAL_SERVER_ERROR, e.toString());
				ctx.channel().close();
				return;
			}
			readHttpDataChunkByChunk();
			if (chunk instanceof LastHttpContent) {
		    	HandlerChain handlerChain = HandlerChainFactory.createHandlerChain();
		    	parmMap.put("decoder", decoder);
	        	handlerChain.doHandler(parmMap);
	        	responseMsg = handlerChain.getMessage();
	        	log.info("response data is:"+JSONUtils.formatJson(responseMsg.toString()));
				writeResponse(ctx.channel(), HttpResponseStatus.OK, responseMsg.toString());
				reset();
				return;
			}
		}
	}

	private void reset() {
		request = null;
		// 销毁decoder释放所有的资源
		if (decoder != null) {
			// decoder.destroy();
			decoder = null;
		}
	}

	/**
	 * 通过chunk读取request，获取chunk数据
	 *
	 * @throws IOException
	 */
	private void readHttpDataChunkByChunk() throws IOException {
		try {
			while (decoder.hasNext()) {
				InterfaceHttpData data = decoder.next();
				if (data != null) {
					try {
						writeHttpData(data);
					} finally {
						data.release();
					}
				}
			}
		} catch (EndOfDataDecoderException e1) {
			System.out.println("end chunk");
		}
	}

	/**
	 * 上传文件
	 * @param data
	 * @throws IOException
	 */
	private void writeHttpData(InterfaceHttpData data) throws IOException {
		if (data.getHttpDataType() == HttpDataType.FileUpload) {
			FileUpload fileUpload = (FileUpload) data;
			if (fileUpload.isCompleted()) {
				String path = filePath + "temp/";
				File dirFile = new File(path);
				if(!dirFile.exists()) {
					dirFile.mkdirs();
				}
				String fileName = path + fileUpload.getFilename();
				fileUpload.renameTo(new File(fileName));
				parmMap.put("file", fileName);
			}
		} else if (data.getHttpDataType() == HttpDataType.Attribute) {
			Attribute attribute = (Attribute) data;
			parmMap.put(attribute.getName(), attribute.getValue());
		}
	}

	private void writeResponse(Channel channel, HttpResponseStatus httpResponseStatus, String returnMsg) {
		// 将请求响应的内容转换成ChannelBuffer.
		ByteBuf buf = null;
		try {
			buf = Unpooled.wrappedBuffer(returnMsg.toString().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 判断是否关闭请求响应连接
		boolean close = HttpHeaders.Values.CLOSE.equalsIgnoreCase(request.headers().get(Names.CONNECTION))
				|| request.getProtocolVersion().equals(HttpVersion.HTTP_1_0)
						&& !HttpHeaders.Values.KEEP_ALIVE.equalsIgnoreCase(request.headers().get(Names.CONNECTION));

		// 构建请求响应对象
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, httpResponseStatus, buf);
		response.headers().set(Names.CONTENT_TYPE, "text/plain; charset=UTF-8");

		if (!close) {
			// 若该请求响应是最后的响应，则在响应头中没有必要添加'Content-Length'
			response.headers().set(Names.CONTENT_LENGTH, buf.readableBytes());
		}

		// 发送请求响应
		ChannelFuture future = channel.writeAndFlush(response);
		// 发送请求响应操作结束后关闭连接
		if (close) {
			future.addListener(ChannelFutureListener.CLOSE);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.getCause().printStackTrace();
		writeResponse(ctx.channel(), HttpResponseStatus.INTERNAL_SERVER_ERROR,
				"数据文件通过过程中出现异常：" + cause.getMessage().toString());
		ctx.channel().close();
	}

}
