package net.qhhhq.merachant.api;

import java.util.List;

import net.qhhhq.api.base.DAO;
import net.qhhhq.model.merachant.MerachantType;

public interface MerachantTypeService extends DAO<MerachantType> {

	public List<MerachantType> getMerachantTypeByParent(Integer parent);
}