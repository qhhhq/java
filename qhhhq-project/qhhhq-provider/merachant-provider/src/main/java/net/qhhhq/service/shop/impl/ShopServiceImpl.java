package net.qhhhq.service.shop.impl;

import java.util.List;

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
	public ShopInfo save(ShopInfo entity) {
		entity.setId(identityService.nextId());
		shopInfoMapper.insertSelective(entity);
		return entity;
	}

	public boolean hasUserShops(long userId) {
		return shopInfoMapper.getUserShopsCount(userId) > 0;
	}

	public List<ShopInfo> getShopListByUserId(long userId) {
		return shopInfoMapper.getUserShops(userId);
	}


}
