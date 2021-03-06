package net.qhhhq.server.netty.handler;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.qcloud.weapp.Hash;
import com.qcloud.weapp.tunnel.TunnelHandleOptions;
import com.qcloud.weapp.tunnel.TunnelService;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;
import net.qhhhq.service.http.qcloud.ChatTunnelHandler;
import net.qhhhq.service.http.qcloud.TunnelChannel;

public class WebSocketServerHandler extends SimpleChannelInboundHandler {

	private static Logger log = Logger.getLogger(WebSocketServerHandler.class);

	private WebSocketServerHandshaker handshaker;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}


	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
    	if (msg instanceof FullHttpRequest) { // 传统的HTTP接入
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) { // WebSocket接入
            handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
	}


    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) {
        // 如果HTTP解码失败，返回HHTP异常
        if (!request.getDecoderResult().isSuccess() || (!"websocket".equals(request.headers().get("Upgrade")))) {
            sendHttpResponse(ctx, request, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }

        String wsUrl = "wss://"+request.headers().get("Host")+request.uri();
        String tunnelId = request.uri().replaceAll("/", "");
        // 正常WebSocket的Http连接请求，构造握手响应返回
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(wsUrl, null, false);
        handshaker = wsFactory.newHandshaker(request);
        if (handshaker == null) { // 无法处理的websocket版本
            WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
        } else { // 向客户端发送websocket握手,完成握手
            handshaker.handshake(ctx.channel(), request);
            TunnelChannel.addTunnelChannel(tunnelId, ctx);
            //推送连接消息
            request.setMethod(HttpMethod.POST);
            TunnelService tunnelService = new TunnelService(request, ctx);
			TunnelHandleOptions options = new TunnelHandleOptions();
			options.setCheckLogin(true);
			try {
				JSONObject json = new JSONObject();
				JSONObject data = new JSONObject();
				data.put("tunnelId", tunnelId);
				data.put("type", "connect");
				json.put("data", data.toString());
				json.put("signature", signature(data.toString(), "20180402"));
				tunnelService.handle(new ChatTunnelHandler(), options, json.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
            // 记录管道处理上下文，便于服务器推送数据到客户端
        }
    }


	private String signature(String data, String key) {
		return Hash.sha1(data + key);
	}

    private void handleWebSocketFrame(ChannelHandlerContext ctx,
                                      WebSocketFrame frame) {
    	WebSocketServerHandler handler = (WebSocketServerHandler) ctx.handler();
    	String uri = handler.handshaker.uri();
    	String tunnelId = uri.substring(uri.lastIndexOf("/") + 1, uri.length());
    	log.info(tunnelId);
        // 判断是否是关闭链路的指令
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(),
                    (CloseWebSocketFrame) frame.retain());
            TunnelChannel.removeTunnelChannel(tunnelId);
            TunnelService tunnelService = new TunnelService(buildHttpRequest(), ctx);
			TunnelHandleOptions options = new TunnelHandleOptions();
			options.setCheckLogin(true);
			try {
				JSONObject json = new JSONObject();
				JSONObject data = new JSONObject();
				data.put("tunnelId", tunnelId);
				data.put("type", "close");
				json.put("data", data.toString());
				String signature = signature(data.toString(), "20180402");
				log.info(signature);
				json.put("signature", signature);
				tunnelService.handle(new ChatTunnelHandler(), options, json.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
            return;
        }
        // 判断是否是Ping消息
        if (frame instanceof PingWebSocketFrame) {
        	String s = frame.content().retain().toString();
            ctx.channel().write(
                    new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // 本例程仅支持文本消息，不支持二进制消息
        if (!(frame instanceof TextWebSocketFrame)) {
            throw new UnsupportedOperationException(String.format(
                    "%s frame types not supported", frame.getClass().getName()));
        }

        // 返回应答消息
        String requestMsg = ((TextWebSocketFrame) frame).text();
        log.info(String.format("%s received %s", ctx.channel(), requestMsg));
        if(requestMsg.equals("ping")) {
        	ctx.channel().write(new TextWebSocketFrame("pong"));
        } else if(requestMsg.indexOf("message:") != -1) {
        	JSONObject message = new JSONObject(requestMsg.replaceAll("message:", ""));
        	String type = message.getString("type");
        	JSONObject content = message.getJSONObject("content");
        	JSONObject sendContent = new JSONObject();
        	JSONObject word = new JSONObject();
        	word.put("word", content.getString("content"));
        	sendContent.put("type", "speak");
        	sendContent.put("content", word);


            TunnelService tunnelService = new TunnelService(buildHttpRequest(), ctx);
			TunnelHandleOptions options = new TunnelHandleOptions();
			options.setCheckLogin(true);
			try {
				JSONObject json = new JSONObject();
				JSONObject data = new JSONObject();
				data.put("tunnelId", tunnelId);
				data.put("type", type);
				data.put("content", sendContent.toString());
				json.put("data", data.toString());
				String signature = signature(data.toString(), "20180402");
				log.info(signature);
				json.put("signature", signature);
				tunnelService.handle(new ChatTunnelHandler(), options, json.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    }

    private FullHttpRequest buildHttpRequest() {
    	DefaultFullHttpRequest request = null;
		try {
			request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST,
			        "/", Unpooled.wrappedBuffer("".getBytes("UTF-8")));
	        // 构建http请求
	        request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
	        request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, request.content().readableBytes());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return request;
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx,
                                         FullHttpRequest req, FullHttpResponse res) {
        // 返回应答给客户端
        if (res.getStatus().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(),
                    CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            HttpUtil.setContentLength(res, res.content().readableBytes());
        }

        // 如果是非Keep-Alive，关闭连接
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!HttpUtil.isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

}
