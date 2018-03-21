package net.qhhhq.service.enterprise.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

import net.qhhhq.api.common.IdentityService;
import net.qhhhq.base.dao.BaseMapper;
import net.qhhhq.base.dao.DaoSupport;
import net.qhhhq.dao.enterprise.EnterpriseMapper;
import net.qhhhq.model.enterprise.Enterprise;
import net.qhhhq.service.enterprise.EnterpriseService;

@Component
@Service
public class EnterpriseServiceImpl extends DaoSupport<Enterprise> implements EnterpriseService {

	@Autowired
	private EnterpriseMapper enterpriseMapper;
	@Autowired
	private IdentityService identityService;

	@Override
	public BaseMapper<Enterprise> getMapper() {
		return enterpriseMapper;
	}

	@Override
	public void save(Enterprise entity) {
		entity.setId(identityService.nextId());
		super.save(entity);
	}

}
