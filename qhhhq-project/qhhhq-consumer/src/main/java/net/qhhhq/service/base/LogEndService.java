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
 * 业务执行结束后记录系统日志
 */
@Component
public class LogEndService implements MessageHandler {

	private static Logger log = Logger.getLogger(LogEndService.class);

	@Reference
	static TranLogService tranLogService;

	public void handle(Map<String, Object> paramMap, HandlerChain paramHandlerChain, SysHead sysHead, AppHead appHead,
			JSONObject data) {
		log.info("LogEndService start ......");
		TranLog tranLog = new TranLog();
		tranLog.setSeqNo(sysHead.getSeqNo());
		tranLog.setEndDate(new Date());
		tranLog.setTranStatus(sysHead.getTranStatus());
		tranLog.setRetCode(sysHead.getRetCode());
		tranLog.setRetMsg(sysHead.getRetMsg());
		tranLogService.update(tranLog);
		paramHandlerChain.doHandler(paramMap);
	}

}
