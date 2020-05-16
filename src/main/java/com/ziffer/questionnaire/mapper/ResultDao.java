package com.ziffer.questionnaire.mapper;

import com.ziffer.questionnaire.model.Result;

import java.util.List;

public interface ResultDao {
    int deleteByPrimaryKey(Integer resultid);

    int insert(Result record);

    int insertSelective(Result record);

    Result selectByPrimaryKey(Integer resultid);

    int updateByPrimaryKeySelective(Result record);

    int updateByPrimaryKey(Result record);

    List<Result> selectByPaperid(Integer paperid);

    int selectResultNumByIP(Integer paperid, String ip);

    int selectResultNumTodayByIP(Integer paperid, String ip);
}