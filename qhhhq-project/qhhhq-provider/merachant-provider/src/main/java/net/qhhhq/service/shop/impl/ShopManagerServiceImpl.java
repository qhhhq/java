package net.qhhhq.service.shop.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

import net.qhhhq.api.common.IdentityService;
import net.qhhhq.base.dao.BaseMapper;
import net.qhhhq.base.dao.DaoSupport;
import net.qhhhq.dao.shop.ShopManagerMapper;
import net.qhhhq.model.shop.ShopManager;
import net.qhhhq.service.shop.ShopManagerService;

@Component
@Service
public class ShopManagerServiceImpl extends DaoSupport<ShopManager> implements ShopManagerService {

	@Autowired
	private ShopManagerMapper shopManagerMapper;
	@Autowired
	private IdentityService identityService;

	@Override
	public BaseMapper<ShopManager> getMapper() {
		return shopManagerMapper;
	}


	@Override
	public ShopManager save(ShopManager entity) {
		entity.setId(identityService.nextId());
		return super.save(entity);
	}


}
