package net.qhhhq.service.http.qcloud;

import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.qcloud.weapp.Hash;

import io.netty.handler.codec.http.FullHttpRequest;
import net.qhhhq.service.common.HandlerChain;
import net.qhhhq.service.common.HttpRequestHandler;

@Component
public class TunnelConnectionServiceBean implements HttpRequestHandler {

	private static Logger log = Logger.getLogger(TunnelConnectionServiceBean.class);

	public void handle(Map<String, Object> paramMap, HandlerChain paramHandlerChain, JSONObject data) {
		FullHttpRequest fhr = (FullHttpRequest) paramMap.get("fhr");
		String uri = fhr.uri();
		log.info("TunnelConnectionServiceBean start ......"+ uri);
		if(fhr != null && uri != null && uri.equals("/get/wsurl")) {
			String reqData = (String) paramMap.get("DATA");
			JSONObject reqJson = new JSONObject(reqData);
			String tcKey = reqJson.getString("tcKey");
			String signature = reqJson.getString("signature");
			String tcId = reqJson.getString("tcId");
			String dataJson = reqJson.getString("data");
			if(signature(dataJson, tcKey).equals(signature)) {
				String tunnelId = UUID.randomUUID().toString().replaceAll("-", "");
				String connectUrl = "wss://10.255.105.225:10010/"+ tunnelId;
				JSONObject json = new JSONObject();
				json.put("tunnelId", tunnelId);
				json.put("connectUrl", connectUrl);
				data.put("data", json.toString());
				data.put("code", 0);
			}
		}
		paramHandlerChain.doHandler(paramMap);
	}

	private String signature(String data, String key) {
		return Hash.sha1(data + key);
	}

}