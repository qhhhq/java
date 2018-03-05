package net.qhhhq.global.auth.impl;

import org.apache.log4j.Logger;

import net.qhhhq.global.auth.AuthService;
import net.qhhhq.global.auth.config.AuthServiceConfig;
import net.qhhhq.service.common.SysHead;

public class RoleAuthServiceImpl implements AuthService {

	private static Logger log = Logger.getLogger(RoleAuthServiceImpl.class);

	public boolean doHandler(SysHead sysHead, AuthServiceConfig service) {
		log.info("RoleAuthServiceImpl start ......");
		boolean result = true;
		if(!sysHead.getUserId().equals("666666")) {
			sysHead.setTranStatus("F");
			sysHead.setRetCode("000011");
			sysHead.setRetMsg("用户没有该交易的权限");
			result = false;
		}
		return result;
	}

}
