package net.qhhhq.service.merachant.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

import net.qhhhq.dao.merachant.MerachantMapper;
import net.qhhhq.merachant.api.MerachantService;
import net.qhhhq.model.merachant.Merachant;

@Component
@Service
public class MerachantServiceImpl implements MerachantService {

	@Autowired
	private MerachantMapper merachantMapper;

	public void save(Merachant merachant) {
		merachantMapper.insert(merachant);
	}

	public void delete(Integer id) {
		merachantMapper.deleteByPrimaryKey(id);
	}

	public void update(Merachant merachant) {
		merachantMapper.updateByPrimaryKey(merachant);
	}

	public List<Merachant> listMerachant(Map<String, Object> map) {
		return merachantMapper.queryAllMerachant(map);
	}

}
