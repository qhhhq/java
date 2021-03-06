package net.qhhhq.service.base;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;

import net.qhhhq.api.common.IdentityService;
import net.qhhhq.service.common.AppHead;
import net.qhhhq.service.common.HandlerChain;
import net.qhhhq.service.common.MessageHandler;
import net.qhhhq.service.common.SysHead;
import net.qhhhq.utils.JSONUtils;

/**
 *
 * @author bankqh-ldr
 *
 * 初始化系统头中的相关数据
 */

@Component
public class DataInitService implements MessageHandler {

	private static Logger log = Logger.getLogger(DataInitService.class);

	@Reference
	static IdentityService identityService;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public void handle(Map<String, Object> paramMap, HandlerChain paramHandlerChain, SysHead sysHead,
			AppHead appHead, JSONObject data) {
		log.info("DataInitService start ......");
		if(sysHead.getSeqNo() == null) {
			sysHead.setSeqNo(String.valueOf(identityService.nextId()));
		}
		if(sysHead.getTranDate() == null) {
			sysHead.setTranDate(sdf.format(new Date()));
		}
		JSONObject reqJson = new JSONObject();
		JSONObject headJson = new JSONObject(sysHead);
		reqJson.put("SYS_HEAD", headJson);
		if(paramMap.get("DATA") != null) {
			JSONObject dataJson = new JSONObject(paramMap.get("DATA").toString());
			reqJson.put("DATA", dataJson);
		}
		log.info("request data is:" + JSONUtils.formatJson(reqJson.toString()));
		sysHead.setTranStatus("S");
		sysHead.setRetCode("000000");
		sysHead.setRetMsg("SUCCESS");
		paramHandlerChain.doHandler(paramMap);
	}

}
