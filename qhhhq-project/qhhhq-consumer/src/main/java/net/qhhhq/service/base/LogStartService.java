package net.qhhhq.service.base;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;

import net.qhhhq.api.common.TranLogService;
import net.qhhhq.model.common.TranLog;
import net.qhhhq.service.common.AppHead;
import net.qhhhq.service.common.HandlerChain;
import net.qhhhq.service.common.MessageHandler;
import net.qhhhq.service.common.SysHead;

/**
 *
 * @author bankqh-ldr
 *
 * 业务开始执行时记录系统日志
 */

@Component
public class LogStartService implements MessageHandler {

	private static Logger log = Logger.getLogger(LogStartService.class);

	@Reference
	static TranLogService tranLogService;

	public void handle(Map<String, Object> paramMap, HandlerChain paramHandlerChain, SysHead sysHead, AppHead appHead,
			JSONObject data) {
		log.info("LogStartService start ......");
		TranLog tranLog = new TranLog();
		tranLog.setSeqNo(sysHead.getSeqNo());
		tranLog.setSourceType(sysHead.getSourceType());
		tranLog.setUserId(sysHead.getUserId());
		tranLog.setTranDate(sysHead.getTranDate());
		tranLog.setServiceCode(sysHead.getServiceCode());
		tranLog.setMessageType(sysHead.getMessageType());
		tranLog.setMessageCode(sysHead.getMessageCode());
		tranLog.setStartDate(new Date());
		tranLogService.save(tranLog);
		paramHandlerChain.doHandler(paramMap);
	}

}
