package net.qhhhq.service.base;

import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import net.qhhhq.global.service.Service;
import net.qhhhq.global.service.config.ServiceItemConfig;
import net.qhhhq.global.service.config.factory.ServiceConfigFactory;
import net.qhhhq.service.common.AppHead;
import net.qhhhq.service.common.HandlerChain;
import net.qhhhq.service.common.MessageHandler;
import net.qhhhq.service.common.SysHead;

public class ExecuteService implements MessageHandler {

	private static Logger log = Logger.getLogger(ExecuteService.class);

	public void handle(Map<String, Object> paramMap, HandlerChain paramHandlerChain, SysHead sysHead, AppHead appHead,
			JSONObject data) {
		log.info("ExecuteService start ......");
		Map<String, ServiceItemConfig> map = ServiceConfigFactory.getServiceMap();
		if(map != null && map.size() > 0) {
			ServiceItemConfig serviceConfig = map.get(sysHead.getServiceCode()+sysHead.getMessageType());
			if(serviceConfig != null) {
				try {
					Service service = serviceConfig.getService();
					service.execute(paramMap, paramHandlerChain, sysHead, appHead, data);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			} else {
				sysHead.setFail("999999", "service is not register");
			}
		} else {
			sysHead.setFail("999999", "service map is null");
		}
		paramHandlerChain.doHandler(paramMap);
	}

}
