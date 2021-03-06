package net.qhhhq.service.http.qcloud;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidAlgorithmParameterException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import net.qhhhq.api.user.UserService;
import net.qhhhq.http.HttpClient;
import net.qhhhq.http.JsonResponseHandler;
import net.qhhhq.model.user.UserInfo;
import net.qhhhq.service.common.HandlerChain;
import net.qhhhq.service.common.HttpRequestHandler;
import net.qhhhq.utils.AES;

@Component
public class MinaAuthServiceBean implements HttpRequestHandler {

	private static Logger log = Logger.getLogger(MinaAuthServiceBean.class);
	private FullHttpRequest fhr = null;
	@Reference
	static UserService userService;

	public void handle(Map<String, Object> paramMap, HandlerChain paramHandlerChain, JSONObject data) {
		fhr = (FullHttpRequest) paramMap.get("fhr");
		String uri = fhr.uri();
		log.info("MinaAuthServiceBean start ......"+ uri);
		if(fhr != null && uri != null && uri.equals("/mina_auth")) {
			JSONObject json = new JSONObject((String) paramMap.get("DATA"));
			JSONObject intface = json.getJSONObject("interface");
			String interfaceName = intface.getString("interfaceName");
			JSONObject para = intface.getJSONObject("para");
			if(interfaceName.equals("qcloud.cam.id_skey")) { //登录
				String code = para.getString("code");
				String encryptedData = para.getString("encrypt_data");
				String iv = para.getString("iv");
				try {
					JSONObject open = getOpenId(code);
					String sessionKey = open.getString("session_key");
					byte[] resultByte  = AES.decrypt(Base64.decodeBase64(encryptedData),
		                    Base64.decodeBase64(sessionKey),
		                    Base64.decodeBase64(iv));
		                if(null != resultByte && resultByte.length > 0){
		                    String strUser = new String(resultByte, "UTF-8");
		                    String skey = UUID.randomUUID().toString().replaceAll("-", "");
		                    String sid = UUID.randomUUID().toString().replaceAll("-", "");
		                    net.qhhhq.model.user.UserInfo userInfo = save(strUser, skey, sid);
		                    JSONObject returnData = new JSONObject();
		                    returnData.put("id", sid);
		                    returnData.put("skey", skey);
		                    String u = com.alibaba.fastjson.JSONObject.toJSONString(userInfo);
		                    returnData.put("user_info", new JSONObject(u));
		                    data.put("returnData", returnData);
		                    data.put("returnMessage", "success");
		                    data.put("returnCode", 0);
		                } else {
		                	log.error("用户信息解密失败");
		                }
				} catch (URISyntaxException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (InvalidAlgorithmParameterException e) {
					e.printStackTrace();
				}
			} else if(interfaceName.equals("qcloud.cam.auth")) {
				String skey = para.getString("skey");
				String id = para.getString("id");
				UserInfo user = userService.getUserBySkey(skey, id);
                JSONObject returnData = new JSONObject();
                data.put("returnMessage", "success");
                data.put("returnCode", 0);
                com.alibaba.fastjson.JSONObject.toJSONString(user);
                returnData.put("user_info", new JSONObject(com.alibaba.fastjson.JSONObject.toJSONString(user)));
                data.put("returnData", returnData);
			}
		}
		paramHandlerChain.doHandler(paramMap);
	}

	private net.qhhhq.model.user.UserInfo save(String strUser, String skey, String sid) {
		net.qhhhq.model.user.UserInfo user = null;
		JSONObject userJson = new JSONObject(strUser);
		if(userJson.has("openId")) {
			String openId = userJson.getString("openId");
			if(!userService.hasOpenId(openId)) {
				user = new net.qhhhq.model.user.UserInfo();
				user.setOpenid(openId);
				if(userJson.has("unionid"))
					user.setUnionid(userJson.getString("unionid"));
				user.setAvatarurl(userJson.getString("avatarUrl"));
				user.setCity(userJson.getString("city"));
				user.setCountry(userJson.getString("country"));
				user.setGender(String.valueOf(userJson.getInt("gender")));
				user.setLanguage(userJson.getString("language"));
				user.setNickname(userJson.getString("nickName"));
				user.setProvince(userJson.getString("province"));
				user.setSkey(skey);
				user.setSid(sid);
				user = userService.save(user);
			} else {
				user = userService.getUserByOpenId(openId);
				user.setSid(sid);
				user.setSkey(skey);
				userService.update(user);
			}
		}
		return user;
	}


	/**
	 * 获取openid和session_key
	 * @param code
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	private JSONObject getOpenId(String code) throws URISyntaxException, IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", "wx275a30f644ebc4c9");
		params.put("secret", "ae0c581b7a6bf0792c87d00a1a8a1d9b");
		params.put("js_code", code);
		params.put("grant_type", "authorization_code");
		JsonResponseHandler handler = new JsonResponseHandler();
		Object obj = HttpClient.doGet("https://api.weixin.qq.com/sns/jscode2session", params, handler);
		JSONObject json = (JSONObject) obj;
		log.info(json);
		return json;
	}

}
