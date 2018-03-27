package net.qhhhq.weixin.service;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class AccessToken {

	private static final String ACCESS_TOKEN = "access_token";
	private static final String ACCESS_TOKEN_URL_SUFFIX = "https://api.weixin.qq.com/cgi-bin/token";
	private static Map<String, String> tokenMap = new HashMap<String, String>();

	private WXConfig config;
	private WXRequest wxRequest;
	private String grant_type = "client_credential";

	public AccessToken(WXConfig config) {
		this.config = config;
		this.wxRequest = new WXRequest();
	}

	/**
	 * 获取access——token
	 * @return
	 */
	public String getToken() {
		String token = null;
		if (tokenMap.get(ACCESS_TOKEN) != null) {
			token = tokenMap.get(ACCESS_TOKEN);
		} else {
			try {
				StringBuffer data = new StringBuffer();
				data.append("grant_type=").append(grant_type)
				.append("&appid=").append(config.getAppID())
				.append("&secret=").append(config.getKey());
				String jsonToken = wxRequest.get(ACCESS_TOKEN_URL_SUFFIX, data.toString(), config.getHttpConnectTimeoutMs(),
						config.getHttpReadTimeoutMs());
				if(jsonToken != null) {
					JSONObject json = new JSONObject(jsonToken);
					if(json.has("access_token")) {
						token = json.getString("access_token");
						tokenMap.put(ACCESS_TOKEN, token);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return token;
	}

	/**
	 * 删除token
	 */
	public static void removeToken() {
		String token = tokenMap.get(ACCESS_TOKEN);
		if(token != null) {
			tokenMap.remove(ACCESS_TOKEN);
		}
	}

}
