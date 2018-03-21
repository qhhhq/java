package net.qhhhq.dao.common;

import java.util.List;

import net.qhhhq.base.dao.BaseMapper;
import net.qhhhq.model.common.Area;

public interface AreaMapper extends BaseMapper<Area> {

    List<Area> queryAreaByParent(Integer parent);

    List<Area> queryAllProvince();
}