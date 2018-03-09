package net.qhhhq.service.shop.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

import net.qhhhq.api.common.IdentityService;
import net.qhhhq.base.dao.BaseMapper;
import net.qhhhq.base.dao.DaoSupport;
import net.qhhhq.dao.shop.ShopInfoMapper;
import net.qhhhq.model.shop.ShopInfo;
import net.qhhhq.service.shop.ShopService;

@Component
@Service
public class ShopServiceImpl extends DaoSupport<ShopInfo> implements ShopService {

	@Autowired
	private ShopInfoMapper shopInfoMapper;
	@Autowired
	private IdentityService identityService;

	@Override
	public BaseMapper<ShopInfo> getMapper() {
		return shopInfoMapper;
	}

	@Override
	public void save(ShopInfo entity) {
		entity.setId(identityService.nextId());
		super.save(entity);
	}


}
