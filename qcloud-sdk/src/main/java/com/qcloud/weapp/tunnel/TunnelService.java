package com.qcloud.weapp.tunnel;

import java.io.IOException;
import java.net.URI;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.qcloud.weapp.ConfigurationException;
import com.qcloud.weapp.ConfigurationManager;
import com.qcloud.weapp.Hash;
import com.qcloud.weapp.authorization.LoginService;
import com.qcloud.weapp.authorization.LoginServiceException;
import com.qcloud.weapp.authorization.UserInfo;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpHeaders.Names;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;


/**
 * 提供信道服务
 * */
public class TunnelService {
	private static Logger log = Logger.getLogger(TunnelService.class);
	private FullHttpRequest request;
	private ChannelHandlerContext ctx;

	/**
	 * 从 Servlet Request 和 Servlet Response 实例化一个信道服务
	 * */
	public TunnelService(FullHttpRequest request, ChannelHandlerContext ctx) {
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

	private JSONObject getJsonForError(Exception error, int errorCode) {
		JSONObject json = new JSONObject();
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
	 * 处理 WebSocket 信道请求
	 * @param handler 指定信道处理器处理信道事件
	 * @param options 指定信道服务的配置
	 */
	public void handle(TunnelHandler handler, TunnelHandleOptions options, String content) throws ConfigurationException {
		if (request == null || request.getMethod().toString().toUpperCase() == "POST") {
			handlePost(handler, options, content);
		} else if (request.getMethod().toString().toUpperCase() == "GET") {
			handleGet(handler, options);
		}
	}

	/**
	 * 处理 GET 请求
	 *
	 * GET 请求表示客户端请求进行信道连接，此时会向 SDK 申请信道连接地址，并且返回给客户端
	 * 如果配置指定了要求登陆，还会调用登陆服务来校验登陆态并获得用户信息
	 */
	private void handleGet(TunnelHandler handler, TunnelHandleOptions options) throws ConfigurationException {
		Tunnel tunnel = null;
		UserInfo user = null;

		if (options != null && options.isCheckLogin()) {
			try {
				LoginService loginService = new LoginService(request, ctx);
				user = loginService.check();
			} catch (Exception e) {
				return;
			}
		}

		TunnelAPI api = new TunnelAPI();
		try {
			String receiveUrl = buildReceiveUrl();
			tunnel = api.requestConnect(receiveUrl, user.getId());
		} catch (Exception e) {
			writeJson(getJsonForError(e));
			return;
		}

		JSONObject result = new JSONObject();
		try {
			result.put("url", tunnel.getConnectUrl());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		log.info("response data is:"+ result);
		writeJson(result);
		handler.onTunnelRequest(tunnel, user);
	}

	private String buildReceiveUrl() throws ConfigurationException {
		URI tunnelServerUri = URI.create(ConfigurationManager.getCurrentConfiguration().getTunnelServerUrl());
		String schema = tunnelServerUri.getScheme();
		String host = ConfigurationManager.getCurrentConfiguration().getServerHost();
		String path = request.getUri();
		return schema + "://" + host + path;
	}

	private void handlePost(TunnelHandler handler, TunnelHandleOptions options, String content) throws ConfigurationException {

		// 1. 读取报文内容

		// 2. 读取报文内容成 JSON 并保存在 body 变量中
		JSONObject body = null;
		String data = null, signature = null;
		try {
			body = new JSONObject(content);
			data = body.getString("data");
			signature = body.getString("signature");
			// String signature = body.getString("signature");
		} catch (JSONException e) {
			JSONObject errJson = new JSONObject();
			try {
				errJson.put("code", 9001);
				errJson.put("message", "Cant not parse the request body: invalid json");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			writeJson(errJson);
		}

		// 3. 检查报文签名
		String computedSignature = Hash.sha1(data + TunnelClient.getKey());
		if (!computedSignature.equals(signature)) {
			JSONObject json = new JSONObject();
			try {
				json.put("code", 9003);
				json.put("message", "Bad Request - 签名错误");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			writeJson(json);
			return;
		}

		// 4. 解析报文中携带的数据
		JSONObject packet;
		String tunnelId = null;
		String packetType = null;
		String packetContent = null;
		try {
			packet = new JSONObject(data);
			tunnelId = packet.getString("tunnelId");
			packetType = packet.getString("type");
			if (packet.has("content")) {
				packetContent = packet.getString("content");
			}

			JSONObject response = new JSONObject();
			response.put("code", 0);
			response.put("message", "OK");
			//writeJson(response);
		} catch (JSONException e) {
			JSONObject response = new JSONObject();
			try {
				response.put("code", 9004);
				response.put("message", "Bad Request - 无法解析的数据包");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			writeJson(response);
			e.printStackTrace();
		}

		// 5. 交给客户处理实例处理报文
		Tunnel tunnel = Tunnel.getById(tunnelId);
		if (packetType.equals("connect")) {
			handler.onTunnelConnect(tunnel);
		}
		else if (packetType.equals("message")) {
			handler.onTunnelMessage(tunnel, new TunnelMessage(packetContent));
		} else if (packetType.equals("close")) {
			handler.onTunnelClose(tunnel);
		}
	}

}
