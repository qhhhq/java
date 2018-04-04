package net.qhhhq.dao.user;

import java.util.Map;

import net.qhhhq.base.dao.BaseMapper;
import net.qhhhq.model.user.UserInfo;

public interface UserInfoMapper extends BaseMapper<UserInfo> {

	UserInfo selectByOpenId(String openid);

	UserInfo selectBySkey(Map<String, Object> map);
}