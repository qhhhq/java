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
import net.qhhhq.utils.JSONUtils;

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
			log.info(JSONUtils.formatJson(reqJson.toString()));
			JSONArray dataArray = new JSONArray(reqJson.getString("data"));
			String signature = reqJson.getString("signature");
			String tcId = reqJson.getString("tcId");
			String dataEncode = reqJson.getString("dataEncode");
			for(int i = 0; i<dataArray.length(); i++) {
				JSONObject dataJson = dataArray.getJSONObject(i);
				String type = dataJson.getString("type");
				JSONObject content = new JSONObject(dataJson.getString("content"));
				JSONArray tunnelIdArray = dataJson.getJSONArray("tunnelIds");
				String contentType = content.getString("type");
				JSONObject message = new JSONObject();
				JSONObject contentContent = content.getJSONObject("content");
				message.put("type", type);
				message.put("content", contentContent);
				for(int j = 0; j<tunnelIdArray.length(); j ++) {
					String tunnelId = tunnelIdArray.getString(j);
					ChannelHandlerContext ctx = TunnelChannel.getChannel(tunnelId);
					if(contentType.equals("speak")) {
						ctx.channel().writeAndFlush(new TextWebSocketFrame(type+":"+message.toString()));
					} else if(contentType.equals("people")) {
						ctx.channel().writeAndFlush(new TextWebSocketFrame("用户上线"));
					}
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
