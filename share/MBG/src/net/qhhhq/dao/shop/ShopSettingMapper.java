package net.qhhhq.dao.shop;

import net.qhhhq.model.shop.ShopSetting;

public interface ShopSettingMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ShopSetting record);

    int insertSelective(ShopSetting record);

    ShopSetting selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ShopSetting record);

    int updateByPrimaryKey(ShopSetting record);
}