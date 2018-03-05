package net.qhhhq.service.user;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import net.qhhhq.http.HttpClient;
import net.qhhhq.http.JsonResponseHandler;
import net.qhhhq.service.common.AppHead;
import net.qhhhq.service.common.HandlerChain;
import net.qhhhq.service.common.MessageHandler;
import net.qhhhq.service.common.SysHead;

public class UserServiceBean implements MessageHandler {

	private static Logger log = Logger.getLogger(UserServiceBean.class);

	public void handle(Map<String, Object> paramMap, HandlerChain paramHandlerChain, SysHead sysHead, AppHead appHead,
			JSONObject data) {
		log.info("UserServiceBean start ......");
		if((sysHead.getServiceCode()+sysHead.getMessageType()+sysHead.getMessageCode()).equals("hhq12000001")) {
			JSONObject user = new JSONObject(paramMap.get("userInfo").toString());
			try {
				getOpenId(user.getString("code"));
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void getOpenId(String code) throws URISyntaxException, IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", "wx275a30f644ebc4c9");
		params.put("secret", "");
		params.put("js_code", code);
		params.put("grant_type", "authorization_code");
		JsonResponseHandler handler = new JsonResponseHandler();
		Object obj = HttpClient.doGet("https://api.weixin.qq.com/sns/jscode2session", params, handler);
		JSONObject json = (JSONObject) obj;
		log.info(json);
	}

}
