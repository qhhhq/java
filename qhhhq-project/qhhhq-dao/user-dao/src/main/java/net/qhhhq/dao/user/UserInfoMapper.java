package net.qhhhq.dao.user;

import net.qhhhq.base.dao.BaseMapper;
import net.qhhhq.model.user.UserInfo;

public interface UserInfoMapper extends BaseMapper<UserInfo> {

	UserInfo selectByOpenId(String openid);
}