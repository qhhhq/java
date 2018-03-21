package net.qhhhq.service.shop.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

import net.qhhhq.api.common.IdentityService;
import net.qhhhq.base.dao.BaseMapper;
import net.qhhhq.base.dao.DaoSupport;
import net.qhhhq.dao.shop.ShopTypeMapper;
import net.qhhhq.model.shop.ShopType;
import net.qhhhq.service.shop.ShopTypeService;

@Component
@Service
public class ShopTypeServiceImpl extends DaoSupport<ShopType> implements ShopTypeService {

	@Autowired
	private ShopTypeMapper shopTypeMapper;
	@Autowired
	private IdentityService identityService;

	@Override
	public BaseMapper<ShopType> getMapper() {
		return shopTypeMapper;
	}

	@Override
	public void save(ShopType entity) {
		entity.setId(identityService.nextId());
		super.save(entity);
	}

	public List<ShopType> getShopTypeByParent(long parent) {
		return shopTypeMapper.selectByParent(parent);
	}

}
