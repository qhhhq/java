package net.qhhhq.service.merachant.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

import net.qhhhq.dao.merachant.MerachantTypeMapper;
import net.qhhhq.merachant.api.MerachantTypeService;
import net.qhhhq.model.merachant.MerachantType;

@Component
@Service
public class MerachantTypeServiceIml implements MerachantTypeService {

	@Autowired
	private MerachantTypeMapper merachantTypeMapper;

	public void save(MerachantType type) {
		merachantTypeMapper.insert(type);
	}

	public void delete(Integer id) {
		merachantTypeMapper.deleteByPrimaryKey(id);
	}

	public void delete(Integer[] ids) {
		merachantTypeMapper.deleteByPrimaryKeys(ids);
	}

	public void update(MerachantType type) {
		merachantTypeMapper.updateByPrimaryKey(type);
	}

	public List<MerachantType> listMerachantType() {
		return merachantTypeMapper.queryAllMerachantType();
	}

	public List<MerachantType> listMerachantTypeByParent(Integer parent) {
		return merachantTypeMapper.queryMerachantTypeByParent(parent);
	}

}
