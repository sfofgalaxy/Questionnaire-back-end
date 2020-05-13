package com.ziffer.questionnaire.service;

import com.ziffer.questionnaire.mapper.UserDao;
import com.ziffer.questionnaire.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserDao userDao;

    @Override
    public User getByUsername(String username) {
        return userDao.selectByUsername(username);
    }

    @Override
    public int register(User user) {
        return userDao.insert(user);
    }

    @Override
    public boolean updatePassword(User user) {
        if(userDao.updateByPrimaryKey(user)>0)return true;
        else return false;
    }
}
