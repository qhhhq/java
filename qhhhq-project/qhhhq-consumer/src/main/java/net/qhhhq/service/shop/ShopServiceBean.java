package net.qhhhq.service.shop;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;

import net.qhhhq.global.service.Service;
import net.qhhhq.model.shop.ShopInfo;
import net.qhhhq.model.shop.ShopManager;
import net.qhhhq.service.common.AppHead;
import net.qhhhq.service.common.HandlerChain;
import net.qhhhq.service.common.SysHead;

@Component
public class ShopServiceBean implements Service {

	private static Logger log = Logger.getLogger(ShopServiceBean.class);
	@Reference
	static ShopService shopService;
	@Reference
	static ShopManagerService shopManagerService;

	public void execute(Map<String, Object> paramMap, HandlerChain paramHandlerChain, SysHead sysHead, AppHead appHead,
			JSONObject data) {
		log.info("ShopServiceBean start ......");
		String messageCode = sysHead.getMessageCode();
		if (messageCode.equals("0001")) {
			getUserShops(sysHead, data);
		} else if(messageCode.equals("0002")) {
			merge(paramMap, sysHead, data);
		} else if(messageCode.equals("0003")) {
			JSONObject json = new JSONObject(paramMap.get("DATA").toString());
			if(json.has("shopId")) {
				ShopInfo shop = shopService.find(Long.valueOf(json.getString("shopId")));
				data.put("shop", com.alibaba.fastjson.JSONObject.toJSONString(shop));
			} else {
				sysHead.setFail("000015", "请求错误");
			}

		}
	}

	/**
	 * 获取用户拥有的店铺列表
	 *
	 * @param sysHead
	 * @param data
	 */
	private void getUserShops(SysHead sysHead, JSONObject data) {
		long userId = Long.valueOf(sysHead.getUserId());
		boolean has = shopService.hasUserShops(userId);
		if (has) {
			List<ShopInfo> list = shopService.getShopListByUserId(userId);
			data.put("shops", com.alibaba.fastjson.JSONObject.toJSONString(list));
			data.put("count", list.size());
		} else {
			data.put("count", 0);
		}
	}

	/**
	 * 新增商户信息
	 * @param paramMap
	 * @param sysHead
	 * @param data
	 */
	private void merge(Map<String, Object> paramMap, SysHead sysHead, JSONObject data) {
		JSONObject json = new JSONObject(paramMap.get("DATA").toString());
		ShopInfo shop = new ShopInfo();
		try {
			shop.setAddress(json.getString("address"));
			shop.setCity(json.getString("city"));
			shop.setContactEmail(json.getString("contactEmail"));
			shop.setContactName(json.getString("contactName"));
			shop.setContactPhone(json.getString("contactPhone"));
			shop.setCreateUser(sysHead.getUserId());
			shop.setManager(json.getString("manager"));
			shop.setDimension(json.getDouble("dimension"));
			shop.setEnterpriseId(Long.valueOf(json.getString("enterpriseId")));
			shop.setLongitude(json.getDouble("longitude"));
			shop.setName(json.getString("name"));
			shop.setProvince(json.getString("province"));
			shop.setDistrict(json.getString("district"));
			shop.setType(Long.valueOf(json.getString("type")));
			if (json.has("class1"))
				shop.setClass1(Long.valueOf(json.getString("class1")));
			if (json.has("class2"))
				shop.setClass2(Long.valueOf(json.getString("class2")));
			if (json.has("class3"))
				shop.setClass3(Long.valueOf(json.getString("class3")));
			if (json.has("class4"))
				shop.setClass4(Long.valueOf(json.getString("class4")));
			if (json.has("class5"))
				shop.setClass5(Long.valueOf(json.getString("class5")));
			if (json.has("class6"))
				shop.setClass6(Long.valueOf(json.getString("class6")));
			if(json.has("id")) {
				shop.setId(Long.valueOf(json.getString("id")));
				shopService.update(shop);
			} else {
				ShopInfo si = shopService.save(shop);

				//添加商户的用户默认添加为管理员
				ShopManager sm = new ShopManager();
				log.info("shopId:"+si.getId());
				sm.setShopId(si.getId());
				sm.setUserId(Long.valueOf(sysHead.getUserId()));
				shopManagerService.save(sm);
			}
		} catch (JSONException e) {
			sysHead.setFail("000013", "请求数据不正确");
		}
	}

}
