package net.qhhhq.server.netty.handler;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpHeaders.Names;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import net.qhhhq.service.common.HandlerChain;
import net.qhhhq.service.common.impl.HttpRequestHandlerChainFactory;
import net.qhhhq.utils.JSONUtils;

/**
 * 处理微信请求相关业务
 * @author bankqh-ldr
 *
 */
public class HttpServerHandler extends ChannelInboundHandlerAdapter {

	private static Logger log = Logger.getLogger(HttpServerHandler.class);

	private HttpRequest request;
	private Map<String, Object> parmMap = new HashMap<String, Object>();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
    	HandlerChain handlerChain = HttpRequestHandlerChainFactory.createHandlerChain();
    	JSONObject responseMsg = null;
    	if(msg instanceof FullHttpRequest) {
    		FullHttpRequest fhr = (FullHttpRequest)msg;
    		ByteBuf buf = fhr.content();
    		byte[] req = new byte[buf.readableBytes()];
    		buf.readBytes(req);
    		String body = new String(req,"UTF-8");
    		parmMap.put("fhr", fhr);
    		parmMap.put("DATA", body);
        	handlerChain.doHandler(parmMap);
        	responseMsg = handlerChain.getMessage();
    	}
    	if (msg instanceof HttpRequest) {
            request = (HttpRequest) msg;
    	}
        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            ByteBuf buf = content.content();
            buf.release();
            log.info("response data is:"+JSONUtils.formatJson(responseMsg.toString()));
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
            		HttpResponseStatus.OK, Unpooled.wrappedBuffer(responseMsg.toString().getBytes("UTF-8")));
            response.headers().set(Names.CONTENT_TYPE, "application/json");
            response.headers().set(Names.CONTENT_LENGTH,
                    response.content().readableBytes());
            if (HttpHeaders.isKeepAlive(request)) {
                response.headers().set(Names.CONNECTION, Values.KEEP_ALIVE);
            }
            ctx.write(response);
            ctx.flush();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
    }

    /**
     * 返回响应报文
     * @param ctx
     * @param responseMsg
     */
    private void sendResponse(ChannelHandlerContext ctx, JSONObject responseMsg) {
    	FullHttpResponse response;
		try {
			response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
					HttpResponseStatus.OK, Unpooled.wrappedBuffer(responseMsg.toString().getBytes("UTF-8")));
	        response.headers().set(Names.CONTENT_TYPE, "text/plain");
	        response.headers().set(Names.CONTENT_LENGTH,
	                response.content().readableBytes());
	        if (HttpHeaders.isKeepAlive(request)) {
	            response.headers().set(Names.CONNECTION, Values.KEEP_ALIVE);
	        }
	        ctx.write(response);
	        ctx.flush();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    }

}
