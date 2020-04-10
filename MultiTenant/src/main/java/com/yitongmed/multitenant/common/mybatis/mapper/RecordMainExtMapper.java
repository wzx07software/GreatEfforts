package com.yitongmed.multitenant.common.mybatis.mapper;

import com.yitongmed.multitenant.common.mybatis.model.RecordMainExt;
import com.yitongmed.multitenant.common.mybatis.model.RecordMainExtCriteria;
import com.yitongmed.multitenant.common.mybatis.model.RecordMainExtWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RecordMainExtMapper {
    long countByExample(RecordMainExtCriteria example);

    int deleteByExample(RecordMainExtCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(RecordMainExtWithBLOBs record);

    int insertSelective(RecordMainExtWithBLOBs record);

    List<RecordMainExtWithBLOBs> selectByExampleWithBLOBs(RecordMainExtCriteria example);

    List<RecordMainExt> selectByExample(RecordMainExtCriteria example);

    RecordMainExtWithBLOBs selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RecordMainExtWithBLOBs record, @Param("example") RecordMainExtCriteria example);

    int updateByExampleWithBLOBs(@Param("record") RecordMainExtWithBLOBs record, @Param("example") RecordMainExtCriteria example);

    int updateByExample(@Param("record") RecordMainExt record, @Param("example") RecordMainExtCriteria example);

    int updateByPrimaryKeySelective(RecordMainExtWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(RecordMainExtWithBLOBs record);

    int updateByPrimaryKey(RecordMainExt record);
}