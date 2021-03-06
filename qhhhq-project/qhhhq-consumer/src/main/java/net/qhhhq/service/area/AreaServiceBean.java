package net.qhhhq.service.area;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;

import net.qhhhq.api.common.AreaService;
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

	public void execute(Map<String, Object> paramMap, HandlerChain paramHandlerChain, SysHead sysHead, AppHead appHead,
			JSONObject data) {
		log.info("AreaServiceBean start ......");
		if(sysHead.getMessageCode().equals("0001")) {
			data.put("provinces", getProvinceList());
		} else if(sysHead.getMessageCode().equals("0002")) {
			JSONObject json = new JSONObject(paramMap.get("DATA").toString());
			if(json.has("parent")) {
				List<Area> areas = getAreaByParent(json.getInt("parent"));
				data.put("areas", areas);
			} else {
				sysHead.setFail("000012", "parent area is null");
			}
		}
	}

	private List<Area> getProvinceList() {
		return areaService.getProvinceList();
	}

	private List<Area> getAreaByParent(int parent) {
		return areaService.getAreaListByParent(parent);
	}

}
