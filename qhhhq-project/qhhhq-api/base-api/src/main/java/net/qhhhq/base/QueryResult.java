package net.qhhhq.base;

import java.io.Serializable;
import java.util.List;

public class QueryResult<T> implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -4304223690006791009L;
	private List<T> resultList;
	private long recordTotal;

	public List<T> getResultList() {
		return resultList;
	}
	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}
	public long getRecordTotal() {
		return recordTotal;
	}
	public void setRecordTotal(long recordTotal) {
		this.recordTotal = recordTotal;
	}
}
