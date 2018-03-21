package net.qhhhq.service.shop;

import java.util.List;

import net.qhhhq.api.base.DAO;
import net.qhhhq.model.shop.ShopInfo;

public interface ShopService extends DAO<ShopInfo> {

	public boolean hasUserShops(long userId);

	public List<ShopInfo> getShopListByUserId(long userId);

}
