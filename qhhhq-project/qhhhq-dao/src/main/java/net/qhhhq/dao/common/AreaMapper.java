package net.qhhhq.dao.common;

import java.util.List;

import net.qhhhq.model.common.Area;

public interface AreaMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Area record);

    int insertSelective(Area record);

    Area selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Area record);

    int updateByPrimaryKey(Area record);

    List<Area> queryAllProvince();

    List<Area> queryAreaByParent(Integer parent);
}