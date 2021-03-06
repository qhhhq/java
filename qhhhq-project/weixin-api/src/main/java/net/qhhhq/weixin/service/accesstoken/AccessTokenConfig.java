package net.qhhhq.weixin.service.accesstoken;

import net.qhhhq.weixin.service.WXConfig;

public class AccessTokenConfig extends WXConfig {

	private String appid;
	private String grant_type = "client_credential";
	private String secret;

	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getGrant_type() {
		return grant_type;
	}
	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}

}
