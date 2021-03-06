package net.qhhhq.weixin.service.accesstoken;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import net.qhhhq.weixin.service.WXRequest;

public class AccessTokenServiceBean {

	private static Logger log = Logger.getLogger(AccessTokenServiceBean.class);

	private static final String ACCESS_TOKEN = "access_token";
	private static final String ACCESS_TOKEN_URL_SUFFIX = "https://api.weixin.qq.com/cgi-bin/token";
	private static Map<String, String> tokenMap = new HashMap<String, String>();

	private AccessTokenConfig config;
	private WXRequest wxRequest;

	public AccessTokenServiceBean(AccessTokenConfig config) {
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
				data.append("grant_type=").append(config.getGrant_type())
				.append("&appid=").append(config.getAppid())
				.append("&secret=").append(config.getSecret());
				String jsonToken = wxRequest.get(ACCESS_TOKEN_URL_SUFFIX, data.toString(), config.getHttpConnectTimeoutMs(),
						config.getHttpReadTimeoutMs());
				if(jsonToken != null) {
					JSONObject json = new JSONObject(jsonToken);
					if(json.has(ACCESS_TOKEN)) {
						token = json.getString(ACCESS_TOKEN);
						tokenMap.put(ACCESS_TOKEN, token);
					} else {
						log.error("get access token fail"+ json);
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
