package net.qhhhq.service.common;

import java.util.Map;

import org.json.JSONObject;

public interface HandlerChain {

	public void doHandler(Map<String, Object> paramMap);

	public JSONObject getMessage();
}