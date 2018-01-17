package net.qhhhq.dao.merachant;

import java.util.List;
import java.util.Map;

import net.qhhhq.model.merachant.Merachant;

public interface MerachantMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Merachant record);

    int insertSelective(Merachant record);

    Merachant selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Merachant record);

    int updateByPrimaryKey(Merachant record);

    List<Merachant> queryAllMerachant(Map<String, Object> map);
}