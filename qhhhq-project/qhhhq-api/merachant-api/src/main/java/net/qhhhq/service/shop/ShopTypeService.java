package net.qhhhq.service.shop;

import java.util.List;

import net.qhhhq.api.base.DAO;
import net.qhhhq.model.shop.ShopType;

public interface ShopTypeService extends DAO<ShopType> {

	public List<ShopType> getShopTypeByParent(long parent);
}
