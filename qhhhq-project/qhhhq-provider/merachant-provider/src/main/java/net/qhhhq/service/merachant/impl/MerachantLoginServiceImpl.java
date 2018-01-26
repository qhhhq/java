package net.qhhhq.service.merachant.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

import net.qhhhq.api.common.IdentityService;
import net.qhhhq.base.dao.BaseMapper;
import net.qhhhq.base.dao.DaoSupport;
import net.qhhhq.dao.merachant.MerachantLoginMapper;
import net.qhhhq.merachant.api.MerachantLoginService;
import net.qhhhq.model.merachant.MerachantLogin;

@Component
@Service
public class MerachantLoginServiceImpl extends DaoSupport<MerachantLogin> implements MerachantLoginService {

	@Autowired
	MerachantLoginMapper merachantLoginMapper;
	@Autowired
	private IdentityService identityService;

	@Override
	public BaseMapper<MerachantLogin> getMapper() {
		return merachantLoginMapper;
	}

	@Override
	public void save(MerachantLogin entity) {
		entity.setId(identityService.nextId());
		super.save(entity);
	}

}
