package net.qhhhq.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

import net.qhhhq.api.common.IdentityService;
import net.qhhhq.api.user.UserService;
import net.qhhhq.base.dao.BaseMapper;
import net.qhhhq.base.dao.DaoSupport;
import net.qhhhq.dao.user.UserInfoMapper;
import net.qhhhq.model.user.UserInfo;

@Component
@Service
public class UserServiceImpl extends DaoSupport<UserInfo> implements UserService {

	@Autowired
	private UserInfoMapper userInfoMapper;
	@Autowired
	private IdentityService identityService;

	@Override
	public BaseMapper<UserInfo> getMapper() {
		return userInfoMapper;
	}

	@Override
	public UserInfo save(UserInfo entity) {
		entity.setId(identityService.nextId());
		return super.save(entity);
	}

	public boolean hasOpenId(String openId) {
		boolean result = true;
		UserInfo user = userInfoMapper.selectByOpenId(openId);
		if(user == null) {
			result = false;
		}
		return result;
	}

	public UserInfo getUserByOpenId(String openId) {
		return userInfoMapper.selectByOpenId(openId);
	}

}