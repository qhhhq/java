package com.qcloud.weapp.authorization;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.qcloud.weapp.ConfigurationException;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.HttpHeaders.Names;
import io.netty.handler.codec.http.HttpHeaders.Values;

/**
 * 提供登录服务
 * */
public class LoginService {
	private FullHttpRequest request;
	private ChannelHandlerContext ctx;

	/**
	 * 从 Servlet Request 和 Servlet Response 创建登录服务
	 * @param request Servlet Request
	 * @param response Servlet Response
	 * */
	public LoginService(FullHttpRequest request, ChannelHandlerContext ctx) {
		this.request = request;
		this.ctx = ctx;
	}

	private void writeJson(JSONObject json) {
		try {
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
            		HttpResponseStatus.OK, Unpooled.wrappedBuffer(json.toString().getBytes("UTF-8")));
            response.headers().set(Names.CONTENT_TYPE, "application/json");
            response.headers().set(Names.CONTENT_LENGTH,
                    response.content().readableBytes());
            if (HttpHeaders.isKeepAlive(request)) {
                response.headers().set(Names.CONNECTION, Values.KEEP_ALIVE);
            }
            ctx.write(response);
            ctx.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private JSONObject prepareResponseJson() {
		JSONObject json = new JSONObject();
		try {
			json.put(Constants.WX_SESSION_MAGIC_ID, 1);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	private JSONObject getJsonForError(Exception error, int errorCode) {
		JSONObject json = prepareResponseJson();
		try {
			json.put("code", errorCode);
			if (error instanceof LoginServiceException) {
				json.put("error", ((LoginServiceException) error).getType());
			}
			json.put("message", error.getMessage());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	private JSONObject getJsonForError(Exception error) {
		return getJsonForError(error, -1);
	}

	/**
	 * 处理登录请求
	 * @return 登录成功将返回用户信息
	 * */
	public UserInfo login() throws IllegalArgumentException, LoginServiceException, ConfigurationException {
		String code = getHeader(Constants.WX_HEADER_CODE);
		String encryptedData = getHeader(Constants.WX_HEADER_ENCRYPTED_DATA);
		String iv = getHeader(Constants.WX_HEADER_IV);

		AuthorizationAPI api = new AuthorizationAPI();
		JSONObject loginResult;

		try {
			loginResult = api.login(code, encryptedData, iv);
		} catch (AuthorizationAPIException apiError) {
			LoginServiceException error = new LoginServiceException(Constants.ERR_LOGIN_FAILED, apiError.getMessage(), apiError);
			writeJson(getJsonForError(error));
			throw error;
		}

		JSONObject json = prepareResponseJson();
		JSONObject session = new JSONObject();
		JSONObject userInfo = null;
		try {
			session.put("id", loginResult.get("id"));
			session.put("skey", loginResult.get("skey"));
			json.put("session", session);
			writeJson(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			userInfo = loginResult.getJSONObject("user_info");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return UserInfo.BuildFromJson(userInfo);
	}

	/**
	 * 检查当前请求的会话状态
	 * @return 如果包含可用会话，将会返回会话对应的用户信息
	 * */
	public UserInfo check() throws LoginServiceException, ConfigurationException {
		String id = getHeader(Constants.WX_HEADER_ID);
		String skey = getHeader(Constants.WX_HEADER_SKEY);

		AuthorizationAPI api = new AuthorizationAPI();
		JSONObject checkLoginResult = null;
		try {
			checkLoginResult = api.checkLogin(id, skey);
		} catch (AuthorizationAPIException apiError) {
			String errorType = Constants.ERR_CHECK_LOGIN_FAILED;
			if (apiError.getCode() == 60011 || apiError.getCode() == 60012) {
				errorType = Constants.ERR_INVALID_SESSION;
			}
			LoginServiceException error = new LoginServiceException(errorType, apiError.getMessage(), apiError);
			writeJson(getJsonForError(error));
			throw error;
		}
		JSONObject userInfo = null;
		try {
			userInfo = checkLoginResult.getJSONObject("user_info");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return UserInfo.BuildFromJson(userInfo);
	}

	private String getHeader(String key) throws LoginServiceException {
		String value = request.headers().get(key);
		if (value == null || value.isEmpty()) {
			LoginServiceException error = new LoginServiceException("INVALID_REQUEST", String.format("请求头不包含 %s，请配合客户端 SDK 使用", key));
			writeJson(getJsonForError(error));
			throw error;
		}
		return value;
	}

}
