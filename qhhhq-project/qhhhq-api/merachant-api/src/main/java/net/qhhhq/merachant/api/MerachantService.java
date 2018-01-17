package net.qhhhq.merachant.api;

import java.util.List;
import java.util.Map;

import net.qhhhq.model.merachant.Merachant;

public interface MerachantService {

	public void save(Merachant merachant);

	public void delete(Integer id);

	public void update(Merachant merachant);

	public List<Merachant> listMerachant(Map<String, Object> map);
}
