package net.qhhhq.dao.merachant;

import java.util.List;

import net.qhhhq.model.merachant.MerachantType;

public interface MerachantTypeMapper {

    int deleteByPrimaryKey(Integer id);

    int deleteByPrimaryKeys(Integer[] ids);

    int insert(MerachantType record);

    int insertSelective(MerachantType record);

    MerachantType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MerachantType record);

    int updateByPrimaryKey(MerachantType record);

    List<MerachantType> queryAllMerachantType();

    List<MerachantType> queryMerachantTypeByParent(Integer parent);
}