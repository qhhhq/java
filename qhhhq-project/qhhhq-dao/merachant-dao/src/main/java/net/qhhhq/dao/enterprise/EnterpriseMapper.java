package net.qhhhq.dao.enterprise;

import net.qhhhq.base.dao.BaseMapper;
import net.qhhhq.model.enterprise.Enterprise;

public interface EnterpriseMapper extends BaseMapper<Enterprise> {

	Enterprise selectByUserId(long userId);
}