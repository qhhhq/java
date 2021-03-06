package net.qhhhq.service.common.impl;

import java.util.ArrayList;
import java.util.List;

import net.qhhhq.service.common.HandlerChain;
import net.qhhhq.service.common.MessageHandler;

public class HandlerChainFactory {

	private static List<MessageHandler> handlers = new ArrayList<MessageHandler>();
	private static HandlerChain HandlerChain;

	public void setHandlers(List<MessageHandler> handlers) {
		this.handlers = handlers;
	}

	public static HandlerChain createHandlerChain() {
		List<MessageHandler> hds = new ArrayList<MessageHandler>();
		for (MessageHandler handlerClass : handlers) {
			hds.add(handlerClass);
		}
		HandlerChain = new HandlerChainImpl(hds);
		return HandlerChain;
	}

}
