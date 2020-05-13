package com.ziffer.questionnaire.mapper;

import com.ziffer.questionnaire.model.User;

import java.util.List;

public interface UserDao {
    int deleteByPrimaryKey(String email);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String email);

    User selectByUsername(String username);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

}