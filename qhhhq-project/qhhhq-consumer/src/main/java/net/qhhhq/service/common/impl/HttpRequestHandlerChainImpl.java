package net.qhhhq.service.common.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import net.qhhhq.service.common.HandlerChain;
import net.qhhhq.service.common.HttpRequestHandler;

public class HttpRequestHandlerChainImpl implements HandlerChain {

	private Iterator<HttpRequestHandler> iterator;
	private JSONObject data;

	public HttpRequestHandlerChainImpl(List<HttpRequestHandler> handlers) {
		this.iterator = handlers.iterator();
	}

	public void doHandler(Map<String, Object> paramMap) {
		if(data == null) {
			data = new JSONObject();
		}
		if (iterator.hasNext()) {
			HttpRequestHandler handler = (HttpRequestHandler) iterator.next();
			handler.handle(paramMap, this, data);
		}
	}

	public JSONObject getMessage() {
		return data;
	}

}
