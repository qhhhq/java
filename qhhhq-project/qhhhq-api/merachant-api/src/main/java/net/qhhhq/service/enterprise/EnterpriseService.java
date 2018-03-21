package net.qhhhq.service.enterprise;

import net.qhhhq.api.base.DAO;
import net.qhhhq.model.enterprise.Enterprise;

public interface EnterpriseService extends DAO<Enterprise> {

	public Enterprise getEnterpriseByUserId(long userId);
}
