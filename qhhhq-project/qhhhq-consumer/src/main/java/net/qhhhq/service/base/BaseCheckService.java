package net.qhhhq.service.base;

import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import net.qhhhq.service.common.AppHead;
import net.qhhhq.service.common.HandlerChain;
import net.qhhhq.service.common.MessageHandler;
import net.qhhhq.service.common.SysHead;

/**
 *
 * @author bankqh-ldr
 *
 * 校验系统头数据完整性
 */
public class BaseCheckService implements MessageHandler {

	private static Logger log = Logger.getLogger(BaseCheckService.class);

	public void handle(Map<String, Object> paramMap, HandlerChain paramHandlerChain, SysHead sysHead,
			AppHead appHead, JSONObject data) {
		log.info("BaseCheckService start ......");
		if(sysHead == null) {
			sysHead = new SysHead();
			sysHead.setTranStatus("F");
			sysHead.setRetCode("000001");
			sysHead.setRetMsg("sys head is null");
			log.error("sys header is null");
		} else if(sysHead.getServiceCode() == null || sysHead.getServiceCode().trim().equals("")) {
			sysHead.setTranStatus("F");
			sysHead.setRetCode("000002");
			sysHead.setRetMsg("serviceCode is null");
			log.error("serviceCode is null");
		} else if(sysHead.getMessageType() == null || sysHead.getMessageType().trim().equals("")) {
			sysHead.setTranStatus("F");
			sysHead.setRetCode("000003");
			sysHead.setRetMsg("messageType is null");
			log.error("messageType is null");
		} else if(sysHead.getMessageCode() == null || sysHead.getMessageCode().trim().equals("")) {
			sysHead.setTranStatus("F");
			sysHead.setRetCode("000004");
			sysHead.setRetMsg("messageCode is null");
			log.error("messageCode is null");
		} else if(sysHead.getTranDate() == null || sysHead.getTranDate().trim().equals("")) {
			sysHead.setTranStatus("F");
			sysHead.setRetCode("000005");
			sysHead.setRetMsg("tranDate is null");
			log.error("tranDate is null");
		} else if(sysHead.getSeqNo() == null || sysHead.getSeqNo().trim().equals("")) {
			sysHead.setTranStatus("F");
			sysHead.setRetCode("000006");
			sysHead.setRetMsg("seqNo is null");
			log.error("seqNo is null");
		} else {
			paramHandlerChain.doHandler(paramMap);
		}
	}

}
