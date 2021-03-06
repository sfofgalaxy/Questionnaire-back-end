package com.ziffer.questionnaire.mapper;

import com.ziffer.questionnaire.model.Option;

import java.util.List;

public interface OptionDao {
    int deleteByPrimaryKey(Integer optionid);

    int insert(Option record);

    int insertSelective(Option record);

    Option selectByPrimaryKey(Integer optionid);

    int updateByPrimaryKeySelective(Option record);

    int updateByPrimaryKey(Option record);

    List<Option> selectByQuestionid(Integer questionid);
}