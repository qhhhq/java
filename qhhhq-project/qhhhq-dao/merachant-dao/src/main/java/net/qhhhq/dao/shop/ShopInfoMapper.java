package net.qhhhq.dao.shop;

import java.util.List;

import net.qhhhq.base.dao.BaseMapper;
import net.qhhhq.model.shop.ShopInfo;

public interface ShopInfoMapper extends BaseMapper<ShopInfo> {

	long getUserShopsCount(long userId);

	List<ShopInfo> getUserShops(long userId);
}