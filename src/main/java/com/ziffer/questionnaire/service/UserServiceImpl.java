package com.ziffer.questionnaire.service;

import com.ziffer.questionnaire.mapper.UserMapper;
import com.ziffer.questionnaire.model.User;
import com.ziffer.questionnaire.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;

    @Override
    public List<User> listAll() {
        return userMapper.selectAll();
    }

    @Override
    public User selectByUsername(String username){
        return userMapper.selectByUsername(username);
    }

    @Override
    public int insertUser(User user) {
        return userMapper.insert(user);
    }
}
