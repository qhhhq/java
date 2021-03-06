package net.qhhhq.service.base;

import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import net.qhhhq.global.auth.AuthService;
import net.qhhhq.global.auth.config.AuthConfig;
import net.qhhhq.global.auth.config.AuthList;
import net.qhhhq.global.auth.config.AuthServiceConfig;
import net.qhhhq.global.auth.factory.AuthFactory;
import net.qhhhq.service.common.AppHead;
import net.qhhhq.service.common.HandlerChain;
import net.qhhhq.service.common.MessageHandler;
import net.qhhhq.service.common.SysHead;

/**
 *
 * @author bankqh-ldr
 *
 *         校验接口调用权限
 */

public class AuthCheckService implements MessageHandler {

	private static Logger log = Logger.getLogger(AuthCheckService.class);

	public void handle(Map<String, Object> paramMap, HandlerChain paramHandlerChain, SysHead sysHead, AppHead appHead,
			JSONObject data) {
		log.info("AuthCheckService start ......");
		AuthList list = AuthFactory.getAuthList();
		for (AuthConfig auth : list.getTrans()) {
			if (auth.getServiceCode().equals(sysHead.getServiceCode()) && auth.getMessageType()
					.equals(sysHead.getMessageType()) && auth.getMessageCode().equals(sysHead.getMessageCode())) {
				for(AuthServiceConfig service : auth.getService()) {
					try {
						AuthService authService = service.getAuthService();
						boolean result = authService.doHandler(sysHead, service);
						if(!result) {
							return;
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
		paramHandlerChain.doHandler(paramMap);
	}

}
