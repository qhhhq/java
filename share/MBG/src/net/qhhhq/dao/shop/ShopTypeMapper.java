package net.qhhhq.dao.shop;

import net.qhhhq.model.shop.ShopType;

public interface ShopTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ShopType record);

    int insertSelective(ShopType record);

    ShopType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ShopType record);

    int updateByPrimaryKey(ShopType record);
}