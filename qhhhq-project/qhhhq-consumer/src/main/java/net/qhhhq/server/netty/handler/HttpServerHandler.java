package net.qhhhq.server.netty.handler;

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
import io.netty.handler.codec.http.websocketx.WebSocketFrameDecoder;
import io.netty.handler.codec.http.websocketx.WebSocketFrameEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
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
	private WebSocketServerHandshaker handshaker;
    private ChannelHandlerContext ctx;

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
        	parmMap.put("ctx", ctx);
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
     * 处理Http请求，完成WebSocket握手<br/>
     * 注意：WebSocket连接第一次请求使用的是Http
     * @param ctx
     * @param request
     * @throws Exception
     */
    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        // 如果HTTP解码失败，返回HHTP异常
       /* if (!request.getDecoderResult().isSuccess() || (!"websocket".equals(request.headers().get("Upgrade")))) {
            sendHttpResponse(ctx, request, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }  */

        // 正常WebSocket的Http连接请求，构造握手响应返回
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://" + request.headers().get(HttpHeaders.Names.HOST), null, false);
        //handshaker = wsFactory.newHandshaker(request);
        handshaker = new WebSocketServerHandshaker(WebSocketVersion.V13,"ws://10.255.105.225", null, 2048) {

			@Override
			protected WebSocketFrameDecoder newWebsocketDecoder() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			protected WebSocketFrameEncoder newWebSocketEncoder() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			protected FullHttpResponse newHandshakeResponse(FullHttpRequest paramFullHttpRequest,
					HttpHeaders paramHttpHeaders) {
				// TODO Auto-generated method stub
				return null;
			}
		};
        if (handshaker == null) { // 无法处理的websocket版本
            WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
        } else { // 向客户端发送websocket握手,完成握手
            handshaker.handshake(ctx.channel(), request);
            // 记录管道处理上下文，便于服务器推送数据到客户端
            this.ctx = ctx;
        }
    }

}
