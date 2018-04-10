package net.qhhhq.server.netty.handler;

import java.io.UnsupportedEncodingException;
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
import net.qhhhq.server.util.WXRequestParser;
import net.qhhhq.service.common.HandlerChain;
import net.qhhhq.service.common.impl.WXHttpsRequestHandlerChainFactory;
import net.qhhhq.utils.JSONUtils;

/**
 * 处理微信请求相关业务
 * @author bankqh-ldr
 *
 */
public class WXHttpsServerHandler extends ChannelInboundHandlerAdapter {

	private static Logger log = Logger.getLogger(WXHttpsServerHandler.class);

	private HttpRequest request;
	private Map<String, Object> parmMap;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
    	HandlerChain handlerChain = WXHttpsRequestHandlerChainFactory.createHandlerChain();
    	JSONObject responseMsg = null;
    	String uri = "";
    	if(msg instanceof FullHttpRequest) {
    		FullHttpRequest fhr = (FullHttpRequest)msg;
    		uri = fhr.uri();
        	parmMap = new WXRequestParser(fhr).parse();
        	parmMap.put("ctx", ctx);
        	parmMap.put("fhr", fhr);
        	handlerChain.doHandler(parmMap);
        	responseMsg = handlerChain.getMessage();
    	}
    	if (msg instanceof HttpRequest) {
            request = (HttpRequest) msg;
    	}
    	if (msg instanceof HttpContent) {
           /* HttpContent content = (HttpContent) msg;
            ByteBuf buf = content.content();
            buf.release();*/
    		if(!uri.equals("/tunnel")) {
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

}
