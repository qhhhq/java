package net.qhhhq.service.merachant.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

import net.qhhhq.api.common.IdentityService;
import net.qhhhq.base.dao.BaseMapper;
import net.qhhhq.base.dao.DaoSupport;
import net.qhhhq.dao.merachant.MerachantContactMapper;
import net.qhhhq.merachant.api.MerachantContactService;
import net.qhhhq.model.merachant.MerachantContact;

@Component
@Service
public class MerachantContactServiceImpl extends DaoSupport<MerachantContact> implements MerachantContactService {

	@Autowired
	MerachantContactMapper merachantContactMapper;
	@Autowired
	private IdentityService identityService;

	@Override
	public BaseMapper<MerachantContact> getMapper() {
		return merachantContactMapper;
	}

	public void save(MerachantContact entity) {
		entity.setId(identityService.nextId());
		super.save(entity);
	}


}
