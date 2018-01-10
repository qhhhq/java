package net.qhhhq.api.common;

import java.util.List;

import net.qhhhq.model.common.Area;

public interface AreaService {

	public void save(Area area);

	public void delete(Integer id);

	public List<Area> listProvince();

	public List<Area> listAreaByParent(Integer parent);
}
