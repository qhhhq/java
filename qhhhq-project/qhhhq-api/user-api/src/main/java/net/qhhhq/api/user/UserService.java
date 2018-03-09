package net.qhhhq.api.user;

import net.qhhhq.api.base.DAO;
import net.qhhhq.model.user.UserInfo;

public interface UserService extends DAO<UserInfo> {

	public boolean hasOpenId(String openId);

	public UserInfo getUserByOpenId(String openId);
}
