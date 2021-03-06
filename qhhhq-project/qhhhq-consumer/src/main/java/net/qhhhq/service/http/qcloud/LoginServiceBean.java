package net.qhhhq.service.http.qcloud;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidAlgorithmParameterException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qcloud.weapp.ConfigurationException;
import com.qcloud.weapp.authorization.Constants;
import com.qcloud.weapp.authorization.LoginService;
import com.qcloud.weapp.authorization.LoginServiceException;
import com.qcloud.weapp.authorization.UserInfo;
import com.qcloud.weapp.tunnel.TunnelService;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import net.qhhhq.api.user.UserService;
import net.qhhhq.http.HttpClient;
import net.qhhhq.http.JsonResponseHandler;
import net.qhhhq.service.common.HandlerChain;
import net.qhhhq.service.common.HttpRequestHandler;
import net.qhhhq.utils.AES;

@Component
public class LoginServiceBean implements HttpRequestHandler {

	private static Logger log = Logger.getLogger(LoginServiceBean.class);


	public void handle(Map<String, Object> paramMap, HandlerChain paramHandlerChain, JSONObject data) {
		ChannelHandlerContext ctx = (ChannelHandlerContext) paramMap.get("ctx");
		FullHttpRequest fhr = (FullHttpRequest) paramMap.get("fhr");
		String uri = fhr.uri();
		log.info("LoginServiceBean start ......"+ uri);
		if(fhr != null && uri != null && uri.equals("/login")) {		//登录
			/*HttpHeaders headers = (HttpHeaders) paramMap.get("headers");
			String code = headers.get(Constants.WX_HEADER_CODE);
			String encryptedData = headers.get(Constants.WX_HEADER_ENCRYPTED_DATA);
			String iv = headers.get(Constants.WX_HEADER_IV);
			try {
				JSONObject open = getOpenId(code);
				byte[] resultByte  = AES.decrypt(Base64.decodeBase64(encryptedData),
	                    Base64.decodeBase64(open.getString("session_key")),
	                    Base64.decodeBase64(iv));
	                if(null != resultByte && resultByte.length > 0){
	                    String strUser = new String(resultByte, "UTF-8");
	                    long userId = save(strUser);
	            		JSONObject json = prepareResponseJson();
	            		JSONObject session = new JSONObject();
	            		JSONObject userInfo = null;
	        			session.put("id", userId);
	        			session.put("skey", userId);
	        			json.put("session", session);
	        			data.put("response", json);
	                } else {
	                	log.error("用户信息解密失败");
	                }
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidAlgorithmParameterException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}*/

			LoginService service = new LoginService(fhr, ctx);
			try {
				UserInfo userInfo = service.login();
				log.info(userInfo);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (LoginServiceException e) {
				e.printStackTrace();
			} catch (ConfigurationException e) {
				e.printStackTrace();
			}
		}

		paramHandlerChain.doHandler(paramMap);
	}

}
