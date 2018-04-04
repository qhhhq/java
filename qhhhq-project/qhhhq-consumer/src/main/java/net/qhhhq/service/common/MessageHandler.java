package net.qhhhq.service.common;

import java.util.Map;

import org.json.JSONObject;

/**
 * 业务请求处理接口
 * @author bankqh-ldr
 *
 */
public interface MessageHandler {

	public abstract void handle(Map<String, Object> paramMap, HandlerChain paramHandlerChain, SysHead sysHead, AppHead appHead, JSONObject data);
}
