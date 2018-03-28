package net.qhhhq.dao.shop;

import net.qhhhq.model.shop.ShopManager;

public interface ShopManagerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ShopManager record);

    int insertSelective(ShopManager record);

    ShopManager selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ShopManager record);

    int updateByPrimaryKey(ShopManager record);
}