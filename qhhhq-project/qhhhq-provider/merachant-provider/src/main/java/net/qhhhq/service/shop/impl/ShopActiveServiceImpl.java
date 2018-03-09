package net.qhhhq.service.shop.impl;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

import net.qhhhq.api.common.IdentityService;
import net.qhhhq.base.QueryResult;
import net.qhhhq.base.dao.BaseMapper;
import net.qhhhq.base.dao.DaoSupport;
import net.qhhhq.dao.shop.ShopActiveMapper;
import net.qhhhq.model.enterprise.Enterprise;
import net.qhhhq.model.shop.ShopActive;
import net.qhhhq.service.shop.ShopActiveService;

@Component
@Service
public class ShopActiveServiceImpl extends DaoSupport<ShopActive> implements ShopActiveService {

	@Autowired
	private ShopActiveMapper shopActiveMapper;
	@Autowired
	private IdentityService identityService;

	@Override
	public BaseMapper<ShopActive> getMapper() {
		return shopActiveMapper;
	}

	@Override
	public void save(ShopActive entity) {
		entity.setId(identityService.nextId());
		super.save(entity);
	}


}
