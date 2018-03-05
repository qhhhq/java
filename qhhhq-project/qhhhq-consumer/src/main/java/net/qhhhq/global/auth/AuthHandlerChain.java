package net.qhhhq.global.auth;

import net.qhhhq.service.common.SysHead;

public interface AuthHandlerChain {

	public void doHandler(SysHead sysHead);
}
