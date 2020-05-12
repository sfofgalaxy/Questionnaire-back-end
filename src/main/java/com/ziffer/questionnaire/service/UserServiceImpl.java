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
    public List<User> listAll() {
        return userDao.selectAll();
    }
}
