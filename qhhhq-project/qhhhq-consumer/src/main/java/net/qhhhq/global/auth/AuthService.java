package net.qhhhq.global.auth;

import net.qhhhq.global.auth.config.AuthServiceConfig;
import net.qhhhq.service.common.SysHead;

public interface AuthService {

	public boolean doHandler(SysHead sysHead, AuthServiceConfig service);
}
