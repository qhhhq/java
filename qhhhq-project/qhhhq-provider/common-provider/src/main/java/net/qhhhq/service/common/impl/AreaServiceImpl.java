package net.qhhhq.service.common.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

import net.qhhhq.api.common.AreaService;
import net.qhhhq.base.dao.BaseMapper;
import net.qhhhq.base.dao.DaoSupport;
import net.qhhhq.dao.common.AreaMapper;
import net.qhhhq.model.common.Area;

@Component
@Service
public class AreaServiceImpl extends DaoSupport<Area> implements AreaService {

	@Autowired
	private AreaMapper areaMapper;

	@Override
	public BaseMapper<Area> getMapper() {
		return areaMapper;
	}

	public List<Area> getProvinceList() {
		return areaMapper.queryAllProvince();
	}

	public List<Area> getAreaListByParent(int parent) {
		return areaMapper.queryAreaByParent(parent);
	}
}
