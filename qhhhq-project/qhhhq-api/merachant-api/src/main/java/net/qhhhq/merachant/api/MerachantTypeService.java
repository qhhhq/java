package net.qhhhq.merachant.api;

import java.util.List;

import net.qhhhq.model.merachant.MerachantType;

public interface MerachantTypeService {

	public void save(MerachantType type);

	public void delete(Integer id);

	public void delete(Integer[] ids);

	public void update(MerachantType type);

	public List<MerachantType> listMerachantType();

	public List<MerachantType> listMerachantTypeByParent(Integer parent);
}
