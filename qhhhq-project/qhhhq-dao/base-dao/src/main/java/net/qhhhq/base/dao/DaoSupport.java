package net.qhhhq.base.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.qhhhq.api.base.DAO;
import net.qhhhq.base.QueryResult;

public abstract class DaoSupport<T> implements DAO<T> {

	public abstract BaseMapper<T> getMapper();

	public T save(T entity) {
		getMapper().insertSelective(entity);
		return entity;
	}

	public void update(T eitity) {
		getMapper().updateByPrimaryKey(eitity);
	}

	public void delete(Serializable... entityIds) {
		for(Object id : entityIds) {
			getMapper().deleteByPrimaryKey(id);
		}
	}

	public T find(Object entityId) {
		return getMapper().selectByPrimaryKey(entityId);
	}

	public long getCount() {
		return getMapper().getCount();
	}

	public QueryResult<T> getScrollData() {
		return getScrollData(-1, -1, null, null);
	}

	public QueryResult<T> getScrollData(int firstResult, int maxResult) {
		return getScrollData(firstResult, maxResult, null, null);
	}

	public QueryResult<T> getScrollData(int firstResult, int maxResult, LinkedHashMap<String, String> orderBy) {
		return getScrollData(firstResult, maxResult, null, orderBy);
	}

	public QueryResult<T> getScrollData(int firstResult, int maxResult, Map<String, Object> where) {
		return getScrollData(firstResult, maxResult, where, null);
	}

	public QueryResult<T> getScrollData(int firstResult, int maxResult, Map<String, Object> where,
			LinkedHashMap<String, String> orderBy) {
		if(where == null) {
			where = new HashMap<String, Object>();
		}
		if(firstResult != -1) {
			where.put("firstResult", firstResult);
		}
		if(maxResult != -1) {
			where.put("maxResult", maxResult);
		}
		if(orderBy != null) {
			String odb = buildOrderBy(orderBy);
			if(odb.length() <= 60) {
				where.put("orderBy", buildOrderBy(orderBy));
			}
		}
		List<T> records = getMapper().queryAllRecords(where);
		long totalRecords = getMapper().queryTotalRecords(where);
		QueryResult<T> result = new QueryResult<T>();
		result.setRecordTotal(totalRecords);
		result.setResultList(records);
		return result;
	}

	protected static String buildOrderBy(LinkedHashMap<String, String> orderBy) {
		StringBuffer orderbysql = new StringBuffer("");
		if(orderBy != null && orderBy.size() > 0) {
			for(String key : orderBy.keySet()) {
				orderbysql.append(key).append(" ").append(orderBy.get(key)).append(",");
			}
			orderbysql.deleteCharAt(orderbysql.length() - 1);
		}
		return orderbysql.toString();
	}

}
