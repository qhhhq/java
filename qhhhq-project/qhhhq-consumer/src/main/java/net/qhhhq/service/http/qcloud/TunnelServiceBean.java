package net.qhhhq.service.http.qcloud;

import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.qcloud.weapp.ConfigurationException;
import com.qcloud.weapp.tunnel.TunnelHandleOptions;
import com.qcloud.weapp.tunnel.TunnelService;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import net.qhhhq.service.common.HandlerChain;
import net.qhhhq.service.common.HttpRequestHandler;

@Component
public class TunnelServiceBean implements HttpRequestHandler {

	private static Logger log = Logger.getLogger(TunnelServiceBean.class);

	public void handle(Map<String, Object> paramMap, HandlerChain paramHandlerChain, JSONObject data) {
		ChannelHandlerContext ctx = (ChannelHandlerContext) paramMap.get("ctx");
		FullHttpRequest fhr = (FullHttpRequest) paramMap.get("fhr");
		String uri = fhr.uri();
		log.info("TunnelServiceBean start ......"+ uri);
		if(fhr != null && uri != null && uri.equals("/tunnel")) {		//会话
			TunnelService tunnelService = new TunnelService(fhr, ctx);
			TunnelHandleOptions options = new TunnelHandleOptions();
			options.setCheckLogin(true);
			try {
				tunnelService.handle(new ChatTunnelHandler(), options);
			} catch (ConfigurationException e) {
				e.printStackTrace();
			}
		}
		paramHandlerChain.doHandler(paramMap);
	}

}
