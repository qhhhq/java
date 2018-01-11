package net.qhhhq.service.common.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

import net.qhhhq.api.common.AreaService;
import net.qhhhq.dao.common.AreaMapper;
import net.qhhhq.model.common.Area;

@Component
@Service
public class AreaServiceImpl implements AreaService {

	@Autowired
	private AreaMapper areaMapper;

	public void save(Area area) {
		areaMapper.insert(area);
	}

	public void delete(Integer id) {
		areaMapper.deleteByPrimaryKey(id);
	}

	public List<Area> listProvince() {
		return areaMapper.queryAllProvince();
	}

	public List<Area> listAreaByParent(Integer parent) {
		return areaMapper.queryAreaByParent(parent);
	}

}