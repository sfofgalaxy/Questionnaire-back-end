package com.ziffer.questionnaire.mapper;

import com.ziffer.questionnaire.model.Question;

import java.util.List;

public interface QuestionDao {
    int deleteByPrimaryKey(Integer questionid);

    int insert(Question record);

    int insertSelective(Question record);

    Question selectByPrimaryKey(Integer questionid);

    int updateByPrimaryKeySelective(Question record);

    int updateByPrimaryKey(Question record);

    List<Question> selectByPaperid(Integer paperid);
}