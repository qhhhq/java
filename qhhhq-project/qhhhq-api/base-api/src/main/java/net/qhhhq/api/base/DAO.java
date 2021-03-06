package net.qhhhq.api.base;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import net.qhhhq.base.QueryResult;

public interface DAO<T> {

	public T save(T entity);

	public void update(T eitity);

	public void delete(Serializable... entityIds);

	public T find(Object entityId);

	public long getCount();

	public QueryResult<T> getScrollData();

	public QueryResult<T> getScrollData(int firstResult, int maxResult);

	public QueryResult<T> getScrollData(int firstResult, int maxResult, LinkedHashMap<String, String> orderBy);

	public QueryResult<T> getScrollData(int firstResult, int maxResult, Map<String, Object> where);

	public QueryResult<T> getScrollData(int firstResult, int maxResult, Map<String, Object> where, LinkedHashMap<String, String> orderBy);
}
