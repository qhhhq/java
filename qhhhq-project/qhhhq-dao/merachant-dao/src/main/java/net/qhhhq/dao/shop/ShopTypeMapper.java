package net.qhhhq.dao.shop;

import java.util.List;

import net.qhhhq.base.dao.BaseMapper;
import net.qhhhq.model.shop.ShopType;

public interface ShopTypeMapper extends BaseMapper<ShopType> {

    List<ShopType> selectByParent(Long parent);
}