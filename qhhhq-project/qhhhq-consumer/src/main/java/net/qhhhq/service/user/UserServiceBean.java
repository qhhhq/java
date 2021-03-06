package net.qhhhq.service.user;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;

import net.qhhhq.api.user.UserService;
import net.qhhhq.base.QueryResult;
import net.qhhhq.global.service.Service;
import net.qhhhq.http.HttpClient;
import net.qhhhq.http.JsonResponseHandler;
import net.qhhhq.model.user.UserInfo;
import net.qhhhq.service.common.AppHead;
import net.qhhhq.service.common.HandlerChain;
import net.qhhhq.service.common.SysHead;

@Component
public class UserServiceBean implements Service {

	private static Logger log = Logger.getLogger(UserServiceBean.class);
	@Reference
	static UserService userService;


	public void execute(Map<String, Object> paramMap, HandlerChain paramHandlerChain, SysHead sysHead, AppHead appHead,
			JSONObject data) {
		log.info("UserServiceBean start ......");
		if(sysHead.getMessageCode().equals("0001")) {	//新增用户
			save(paramMap, paramHandlerChain, sysHead);
		} else if(sysHead.getMessageCode().equals("0002")) {
			QueryResult<UserInfo> result = userService.getScrollData();
			data.put("result", new JSONObject(result));
		}
	}

	private void save(Map<String, Object> paramMap, HandlerChain paramHandlerChain, SysHead sysHead) {
		JSONObject userInfo = new JSONObject(paramMap.get("DATA").toString());
		if(userInfo != null) {
			JSONObject userJson = userInfo.getJSONObject("userInfo");
			if(userJson != null) {
				try {
					UserInfo user = new UserInfo();
					JSONObject open = getOpenId(userJson.getString("code"));
					String openId = open.getString("openid");
					if(!userService.hasOpenId(openId)) {
						user.setOpenid(openId);
						if(open.has("unionid"))
							user.setUnionid(open.getString("unionid"));
						user.setAvatarurl(userJson.getString("avatarUrl"));
						user.setCity(userJson.getString("city"));
						user.setCountry(userJson.getString("country"));
						user.setGender(String.valueOf(userJson.getInt("gender")));
						user.setLanguage(userJson.getString("language"));
						user.setNickname(userJson.getString("nickName"));
						user.setProvince(userJson.getString("province"));
						userService.save(user);
					} else {
						sysHead.setFail("000101", "用户信息已经存在");
					}
					UserInfo u = userService.getUserByOpenId(openId);
					sysHead.setUserId(String.valueOf(u.getId()));
				} catch (Exception e) {
					sysHead.setFail("000100", "用户保存失败");
					e.printStackTrace();
				}
			} else {
				sysHead.setFail("000050", "请求数据不完整");
			}
		}

	}

	private JSONObject getOpenId(String code) throws URISyntaxException, IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", "wx275a30f644ebc4c9");
		params.put("secret", "ae0c581b7a6bf0792c87d00a1a8a1d9b");
		params.put("js_code", code);
		params.put("grant_type", "authorization_code");
		JsonResponseHandler handler = new JsonResponseHandler();
		Object obj = HttpClient.doGet("https://api.weixin.qq.com/sns/jscode2session", params, handler);
		JSONObject json = (JSONObject) obj;
		return json;
	}

}
