package net.qhhhq.global.auth.impl;

import org.apache.log4j.Logger;

import net.qhhhq.global.auth.AuthService;
import net.qhhhq.global.auth.config.AuthServiceConfig;
import net.qhhhq.service.common.SysHead;

public class UserIdAuthServiceImpl implements AuthService {

	private static Logger log = Logger.getLogger(UserIdAuthServiceImpl.class);

	public boolean doHandler(SysHead sysHead, AuthServiceConfig service) {
		log.info("UserIdAuthServiceImpl start ......");
		boolean result = true;
		if(sysHead.getUserId() == null || sysHead.getUserId().trim().equals("")) {
			sysHead.setTranStatus("F");
			sysHead.setRetCode("000010");
			sysHead.setRetMsg("该交易不允许用户ID为空");
			result = false;
		}
		return result;
	}

}
