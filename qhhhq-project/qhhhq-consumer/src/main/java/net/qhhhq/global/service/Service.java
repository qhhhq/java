package net.qhhhq.global.service;

import java.util.Map;

import org.json.JSONObject;

import net.qhhhq.service.common.AppHead;
import net.qhhhq.service.common.HandlerChain;
import net.qhhhq.service.common.SysHead;

public interface Service {

	public void execute(Map<String, Object> paramMap, HandlerChain paramHandlerChain, SysHead sysHead, AppHead appHead,
			JSONObject data);
}
