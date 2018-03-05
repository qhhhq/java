package net.qhhhq.service.area;

import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;

import net.qhhhq.api.common.AreaService;
import net.qhhhq.base.QueryResult;
import net.qhhhq.model.common.Area;
import net.qhhhq.service.common.AppHead;
import net.qhhhq.service.common.HandlerChain;
import net.qhhhq.service.common.MessageHandler;
import net.qhhhq.service.common.SysHead;

@Component
public class AreaServiceBean implements MessageHandler {

	private static Logger log = Logger.getLogger(AreaServiceBean.class);

	@Reference
	static AreaService areaService;

	public void save() {
		QueryResult<Area> result = areaService.getScrollData();
	}

	public void handle(Map<String, Object> paramMap, HandlerChain paramHandlerChain, SysHead sysHead,
			AppHead appHead, JSONObject data) {
		log.info("AreaServiceBean start ......");
		if(sysHead.getMessageCode().equals("1000")) {
			log.debug("service 1000 start");
			QueryResult<Area> result = areaService.getScrollData();
			data.put("result", new JSONObject(result));
		}
		paramHandlerChain.doHandler(paramMap);
	}

}
