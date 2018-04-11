package net.qhhhq.service.http.qcloud;

import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.qcloud.weapp.Hash;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import net.qhhhq.service.common.HandlerChain;
import net.qhhhq.service.common.HttpRequestHandler;

@Component
public class WsPushServiceBean implements HttpRequestHandler {

	private static Logger log = Logger.getLogger(WsPushServiceBean.class);

	public void handle(Map<String, Object> paramMap, HandlerChain paramHandlerChain, JSONObject data) {
		FullHttpRequest fhr = (FullHttpRequest) paramMap.get("fhr");
		String uri = fhr.uri();
		log.info("WsPushServiceBean start ......"+ uri);
		if(fhr != null && uri != null && uri.equals("/ws/push")) {
			String reqData = (String) paramMap.get("DATA");
			JSONObject reqJson = new JSONObject(reqData);
			String signature = reqJson.getString("signature");
			String tcId = reqJson.getString("tcId");
			JSONArray dataJson = new JSONArray(reqJson.getString("data"));
			for(int i = 0; i<dataJson.length(); i++) {
				JSONObject tunnelJson = dataJson.getJSONObject(i);
				String type = tunnelJson.getString("type");
				JSONArray tunnelIds = tunnelJson.getJSONArray("tunnelIds");
				JSONObject content = new JSONObject(tunnelJson.getString("content"));
				for(int j = 0; j<tunnelIds.length(); j++) {
					String tunnelId = tunnelIds.getString(j);
					ChannelHandlerContext ctx = TunnelChannel.getChannel(tunnelId);
					ctx.channel().write(new TextWebSocketFrame("用户上线"));
				}
			}
			data.put("code", 0);
			log.info(reqJson);
		}
		paramHandlerChain.doHandler(paramMap);
	}

	private String signature(String data, String key) {
		return Hash.sha1(data + key);
	}

}
