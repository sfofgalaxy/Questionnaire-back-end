package com.ziffer.questionnaire.mapper;

import com.ziffer.questionnaire.model.Answer;
import com.ziffer.questionnaire.model.AnswerKey;

import java.util.List;

public interface AnswerDao {
    int deleteByPrimaryKey(AnswerKey key);

    int insert(Answer record);

    int insertSelective(Answer record);

    Answer selectByPrimaryKey(AnswerKey key);

    int updateByPrimaryKeySelective(Answer record);

    int updateByPrimaryKey(Answer record);

    List<Answer> selectByResultid(Integer resultid);
}