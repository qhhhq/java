package net.qhhhq.service.enterprise;

import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;

import net.qhhhq.global.service.Service;
import net.qhhhq.model.enterprise.Enterprise;
import net.qhhhq.service.common.AppHead;
import net.qhhhq.service.common.HandlerChain;
import net.qhhhq.service.common.SysHead;

@Component
public class EnterpriseServiceBean implements Service {

	private static Logger log = Logger.getLogger(EnterpriseServiceBean.class);
	@Reference
	static EnterpriseService enterpriseService;

	public void execute(Map<String, Object> paramMap, HandlerChain paramHandlerChain, SysHead sysHead, AppHead appHead,
			JSONObject data) {
		log.info("EnterpriseServiceBean start ......");
		if(sysHead.getMessageCode().trim().equals("0001")) {
			merge(paramMap, sysHead, data);
		} else if(sysHead.getMessageCode().trim().equals("0002")) {
			Enterprise enterprise = enterpriseService.getEnterpriseByUserId(Long.valueOf(sysHead.getUserId()));
			data.put("enterprise", com.alibaba.fastjson.JSONObject.toJSONString(enterprise));
			boolean has = enterprise == null ? false : true;
			data.put("hasEnterprise", has);
		}
	}

	/**
	 * merge企业信息
	 * @param paramMap
	 * @param sysHead
	 * @param data
	 */
	private void merge(Map<String, Object> paramMap, SysHead sysHead, JSONObject data) {
		Enterprise enterprise = enterpriseService.getEnterpriseByUserId(Long.valueOf(sysHead.getUserId()));
		JSONObject json = new JSONObject(paramMap.get("DATA").toString());
		Enterprise e = new Enterprise();
		e.setAddress(json.getString("address"));
		e.setCode(json.getString("code"));
		e.setImg(json.getString("img"));
		e.setImg1(json.getString("img1"));
		e.setLegal(json.getString("legal"));
		e.setLegalDocId(json.getString("legalDocId"));
		e.setName(json.getString("name"));
		e.setPhone(json.getString("phone"));
		e.setUserId(Long.valueOf(sysHead.getUserId()));
		if(enterprise != null) {
			e.setId(enterprise.getId());
			enterpriseService.update(e);
		} else {
			enterpriseService.save(e);
		}
		data.put("enterpriseId", e.getId().toString());
	}

}
