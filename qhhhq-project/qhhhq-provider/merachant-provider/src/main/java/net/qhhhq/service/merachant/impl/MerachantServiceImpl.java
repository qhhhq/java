package net.qhhhq.service.merachant.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

import net.qhhhq.base.dao.BaseMapper;
import net.qhhhq.base.dao.DaoSupport;
import net.qhhhq.dao.merachant.MerachantMapper;
import net.qhhhq.merachant.api.MerachantService;
import net.qhhhq.model.merachant.Merachant;

@Component
@Service
public class MerachantServiceImpl extends DaoSupport<Merachant> implements MerachantService {

	@Autowired
	private MerachantMapper merachantMapper;

	@Override
	public BaseMapper<Merachant> getMapper() {
		return merachantMapper;
	}


}
