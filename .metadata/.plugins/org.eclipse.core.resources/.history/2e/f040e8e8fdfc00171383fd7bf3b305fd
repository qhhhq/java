package net.qhhhq.service.merachant.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

import net.qhhhq.base.dao.BaseMapper;
import net.qhhhq.base.dao.DaoSupport;
import net.qhhhq.dao.merachant.MerachantTypeMapper;
import net.qhhhq.merachant.api.MerachantTypeService;
import net.qhhhq.model.merachant.MerachantType;

@Component
@Service
public class MerachantTypeServiceIml extends DaoSupport<MerachantType> implements MerachantTypeService {

	@Autowired
	private MerachantTypeMapper merachantTypeMapper;

	@Override
	public BaseMapper<MerachantType> getMapper() {
		return merachantTypeMapper;
	}



}
