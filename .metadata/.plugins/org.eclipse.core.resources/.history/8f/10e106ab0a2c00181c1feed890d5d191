package net.qhhhq.service.shop.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;

import net.qhhhq.api.common.IdentityService;
import net.qhhhq.base.dao.BaseMapper;
import net.qhhhq.base.dao.DaoSupport;
import net.qhhhq.dao.shop.ShopSettingMapper;
import net.qhhhq.model.shop.ShopSetting;
import net.qhhhq.service.shop.ShopSettingService;

@Component
@Service
public class ShopSettingServiceImpl extends DaoSupport<ShopSetting> implements ShopSettingService {

	@Autowired
	private ShopSettingMapper shopSettingMapper;
	@Autowired
	private IdentityService identityService;

	@Override
	public BaseMapper<ShopSetting> getMapper() {
		return shopSettingMapper;
	}

	@Override
	public void save(ShopSetting entity) {
		entity.setId(identityService.nextId());
		super.save(entity);
	}


}
