package net.qhhhq.api.common;

import java.util.List;

import net.qhhhq.api.base.DAO;
import net.qhhhq.model.common.Area;

public interface AreaService extends DAO<Area> {

	public List<Area> getProvinceList();

	public List<Area> getAreaListByParent(int parent);
}
