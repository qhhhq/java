package net.qhhhq.dao.shop;

import net.qhhhq.model.shop.ShopInfo;

public interface ShopInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ShopInfo record);

    int insertSelective(ShopInfo record);

    ShopInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ShopInfo record);

    int updateByPrimaryKey(ShopInfo record);
}