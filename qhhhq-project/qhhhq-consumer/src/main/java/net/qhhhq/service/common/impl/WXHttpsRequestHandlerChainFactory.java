package net.qhhhq.service.common.impl;

import java.util.ArrayList;
import java.util.List;

import net.qhhhq.service.common.HandlerChain;
import net.qhhhq.service.common.HttpRequestHandler;

/**
 * http请求处理链条工厂
 * @author bankqh-ldr
 *
 */
public class WXHttpsRequestHandlerChainFactory {

	private static List<HttpRequestHandler> handlers = new ArrayList<HttpRequestHandler>();
	private static HandlerChain HandlerChain;

	public void setHandlers(List<HttpRequestHandler> handlers) {
		this.handlers = handlers;
	}

	public static HandlerChain createHandlerChain() {
		List<HttpRequestHandler> hds = new ArrayList<HttpRequestHandler>();
		for (HttpRequestHandler handlerClass : handlers) {
			hds.add(handlerClass);
		}
		HandlerChain = new HttpRequestHandlerChainImpl(hds);
		return HandlerChain;
	}

}
