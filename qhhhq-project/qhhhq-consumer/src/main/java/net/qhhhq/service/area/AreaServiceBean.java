package net.qhhhq.service.area;

import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;

import net.qhhhq.api.common.AreaService;
import net.qhhhq.base.QueryResult;
import net.qhhhq.global.service.Service;
import net.qhhhq.model.common.Area;
import net.qhhhq.service.common.AppHead;
import net.qhhhq.service.common.HandlerChain;
import net.qhhhq.service.common.SysHead;

@Component
public class AreaServiceBean implements Service {

	private static Logger log = Logger.getLogger(AreaServiceBean.class);

	@Reference
	static AreaService areaService;

	public void save() {
		QueryResult<Area> result = areaService.getScrollData();
	}

	public void execute(Map<String, Object> paramMap, HandlerChain paramHandlerChain, SysHead sysHead, AppHead appHead,
			JSONObject data) {
		log.info("AreaServiceBean start ......");
	}

}
