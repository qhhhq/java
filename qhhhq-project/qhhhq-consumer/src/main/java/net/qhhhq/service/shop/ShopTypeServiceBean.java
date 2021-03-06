package net.qhhhq.service.shop;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;

import net.qhhhq.global.service.Service;
import net.qhhhq.model.shop.ShopType;
import net.qhhhq.service.common.AppHead;
import net.qhhhq.service.common.HandlerChain;
import net.qhhhq.service.common.SysHead;

@Component
public class ShopTypeServiceBean implements Service {

	private static Logger log = Logger.getLogger(ShopTypeServiceBean.class);

	@Reference
	static ShopTypeService shopTypeService;

	public void execute(Map<String, Object> paramMap, HandlerChain paramHandlerChain, SysHead sysHead, AppHead appHead,
			JSONObject data) {
		log.info("ShopTypeServiceBean start ......");
		if(sysHead.getMessageCode().equals("0001")) {
			JSONObject json = new JSONObject(paramMap.get("DATA").toString());
			if(json.has("parent")) {
				List<ShopType> list = shopTypeService.getShopTypeByParent(Long.valueOf(json.getString("parent")));
				data.put("types", com.alibaba.fastjson.JSONObject.toJSONString(list));
			}
		}
	}

}
