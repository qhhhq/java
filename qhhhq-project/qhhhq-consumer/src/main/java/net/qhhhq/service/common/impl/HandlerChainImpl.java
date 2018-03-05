package net.qhhhq.service.common.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import net.qhhhq.service.common.AppHead;
import net.qhhhq.service.common.HandlerChain;
import net.qhhhq.service.common.MessageHandler;
import net.qhhhq.service.common.SysHead;

public class HandlerChainImpl implements HandlerChain {

	private static final String SYS_HEAD = "SYS_HEAD";
	private static final String APP_HEAD = "APP_HEAD";

	private Iterator<MessageHandler> iterator;
	private SysHead sysHead;
	private AppHead appHead;
	private JSONObject data;
	private JSONObject message = new JSONObject();

	public HandlerChainImpl(List<MessageHandler> handlers) {
		this.iterator = handlers.iterator();
	}

	public void doHandler(Map<String, Object> paramMap) {
		if(sysHead == null) {
			sysHead = getSysHeader(paramMap);
		}
		if(appHead == null) {
			appHead = getAppHead(paramMap);
		}
		if(data == null) {
			data = new JSONObject();
		}
		if (iterator.hasNext()) {
			MessageHandler handler = (MessageHandler) iterator.next();
			handler.handle(paramMap, this, sysHead, appHead, data);
		}
	}

	public JSONObject getMessage() {
		message.put("SYS_HEAD", new JSONObject(sysHead));
		if(appHead != null) {
			message.put("APP_HEAD", new JSONObject(appHead));
		}
		message.put("DATA", data);
		return message;
	}

	private SysHead getSysHeader(Map<String, Object> paramMap) {
		Object obj = paramMap.get(SYS_HEAD);
		SysHead sysHead = null;
		if(obj != null) {
			JSONObject sysJson = new JSONObject(obj.toString());
			sysHead = new SysHead();
			if(sysJson.has("SEQ_NO")) {
				sysHead.setSeqNo(sysJson.getString("SEQ_NO"));
			}
			if(sysJson.has("SOURCE_TYPE")) {
				sysHead.setSourceType(sysJson.getString("SOURCE_TYPE"));
			}
			if(sysJson.has("USER_ID")) {
				sysHead.setUserId(sysJson.getString("USER_ID"));
			}
			if(sysJson.has("TRAN_DATE")) {
				sysHead.setTranDate(sysJson.getString("TRAN_DATE"));
			}
			if(sysJson.has("SERVICE_CODE")) {
				sysHead.setServiceCode(sysJson.getString("SERVICE_CODE"));
			}
			if(sysJson.has("MESSAGE_TYPE")) {
				sysHead.setMessageType(sysJson.getString("MESSAGE_TYPE"));
			}
			if(sysJson.has("MESSAGE_CODE")) {
				sysHead.setMessageCode(sysJson.getString("MESSAGE_CODE"));
			}
		}
		return sysHead;
	}

	private AppHead getAppHead(Map<String, Object> paramMap) {
		Object obj = paramMap.get(APP_HEAD);
		AppHead appHead = null;
		if(obj != null) {
			JSONObject appJson = new JSONObject(obj);
			appHead = new AppHead();
			if(appJson.has("PAGE_NO")) {
				appHead.setPageNo(appJson.getInt("PAGE_NO"));
			}
			if(appJson.has("PAGE_SIZE")) {
				appHead.setPageSize(appJson.getInt("PAGE_SIZE"));
			}
		}
		return appHead;
	}

}
