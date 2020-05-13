package com.ziffer.questionnaire.mapper;

import com.ziffer.questionnaire.model.Paper;

import java.util.List;

public interface PaperDao {
    int deleteByPrimaryKey(Integer paperid);

    int insert(Paper record);

    int insertSelective(Paper record);

    Paper selectByPrimaryKey(Integer paperid);

    int updateByPrimaryKeySelective(Paper record);

    int updateByPrimaryKey(Paper record);

    List<Paper> selectByAuthor(String username);
}