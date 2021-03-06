package net.qhhhq.service.common.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

import net.qhhhq.api.common.TranLogService;
import net.qhhhq.base.dao.BaseMapper;
import net.qhhhq.base.dao.DaoSupport;
import net.qhhhq.dao.common.TranLogMapper;
import net.qhhhq.model.common.TranLog;

@Component
@Service
public class TranLogServiceImpl extends DaoSupport<TranLog> implements TranLogService {

	@Autowired
	TranLogMapper tranLogMapper;

	@Override
	public BaseMapper<TranLog> getMapper() {
		return tranLogMapper;
	}

	@Override
	public void update(TranLog eitity) {
		tranLogMapper.updateByPrimaryKeySelective(eitity);
	}

}
