package net.qhhhq.base.dao;

import java.util.List;
import java.util.Map;

public interface BaseMapper<T> {
	int deleteByPrimaryKey(Object id);

    int insert(T record);

    int insertSelective(T record);

    T selectByPrimaryKey(Object id);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);

    long getCount();

    long queryTotalRecords(Map<String, Object> map);

    List<T> queryAllRecords(Map<String, Object> map);
}
