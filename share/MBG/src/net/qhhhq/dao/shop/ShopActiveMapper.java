package net.qhhhq.dao.shop;

import net.qhhhq.model.shop.ShopActive;

public interface ShopActiveMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ShopActive record);

    int insertSelective(ShopActive record);

    ShopActive selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ShopActive record);

    int updateByPrimaryKeyWithBLOBs(ShopActive record);

    int updateByPrimaryKey(ShopActive record);
}