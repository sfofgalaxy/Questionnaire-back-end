package com.ziffer.questionnaire.mapper;

import com.ziffer.questionnaire.model.Result;

public interface ResultDao {
    int deleteByPrimaryKey(Integer resultid);

    int insert(Result record);

    int insertSelective(Result record);

    Result selectByPrimaryKey(Integer resultid);

    int updateByPrimaryKeySelective(Result record);

    int updateByPrimaryKey(Result record);
}