package net.qhhhq.weixin.service.templatemessage;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import net.qhhhq.weixin.service.WXConfig;
import net.qhhhq.weixin.service.WXRequest;
import net.qhhhq.weixin.service.accesstoken.AccessTokenServiceBean;

public class TemplatemessageServiceBean {

	private static Logger log = Logger.getLogger(TemplatemessageServiceBean.class);
	private static final String TEMPLATE_MESSAGE_URL_SUFFIX = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send";
	private AccessTokenServiceBean accessTokenServiceBean;
	private WXRequest wxRequest;
	private WXConfig config = new TemplateMessageConfig();

	public TemplatemessageServiceBean(AccessTokenServiceBean accessTokenServiceBean) {
		this.accessTokenServiceBean = accessTokenServiceBean;
		wxRequest = new WXRequest();
	}


	/**
	 * 发送模板消息
	 * @param message
	 * @return
	 */
	public JSONObject send(JSONObject message) {
		JSONObject json = null;
		String urlSuffix = TEMPLATE_MESSAGE_URL_SUFFIX + "?access_token=" + accessTokenServiceBean.getToken();
		try {
			String jsonResult = wxRequest.post(urlSuffix, message.toString(), config.getHttpConnectTimeoutMs(), config.getHttpReadTimeoutMs());
			if(jsonResult != null) {
				json = new JSONObject(jsonResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
}
