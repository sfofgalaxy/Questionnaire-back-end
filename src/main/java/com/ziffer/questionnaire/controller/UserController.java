package com.ziffer.questionnaire.controller;

import com.ziffer.questionnaire.dto.GeneralMessage;
import com.ziffer.questionnaire.dto.LoginMessage;
import com.ziffer.questionnaire.intercepter.AuthToken;
import com.ziffer.questionnaire.model.User;
import com.ziffer.questionnaire.service.UserServiceImpl;
import com.ziffer.questionnaire.utils.EncrypteUtils;
import com.ziffer.questionnaire.utils.RedisUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {
//    @Resource
//    @Autowired
    @Resource
    UserServiceImpl userServiceImpl;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    private EncrypteUtils encrypteUtils;

    //等价@PostMapping("/login")
    @ApiOperation("登录")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public LoginMessage login(@RequestParam("username") String username,
                              @RequestParam("password") String password){
        LoginMessage message = new LoginMessage();
        if(username==""||password==""){
            message.setState(false);
            message.setMessage("密码或用户名不能为空");
        } else if(username.length()<6){
            message.setState(false);
            message.setMessage("用户名至少6位");
        } else if(password.length()<6){
            message.setState(false);
            message.setMessage("密码至少6位");
        } else {
            User user = userServiceImpl.getByUsername(username);
            if(user==null){
                message.setState(false);
                message.setMessage("用户名或密码错误");
            }else if(!user.getPassword().equals(password)){
                message.setState(false);
                message.setMessage("用户名或密码错误");
            }else{
                String token = encrypteUtils.getMD5Code(username,password);
                redisUtils.set(token,username);
                message.setUsername(username);
                message.setState(true);
                message.setMessage(token);
            }
        }
        return message;
    }

    @ApiOperation("注册")
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public LoginMessage register(@RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 @RequestParam("email") String email){
        LoginMessage message = new LoginMessage();
        if(username==""||password==""){
            message.setState(false);
            message.setMessage("密码或用户名不能为空");
        }else if(username.length()<6){
            message.setState(false);
            message.setMessage("用户名至少6位");
        } else if(password.length()<6){
            message.setState(false);
            message.setMessage("密码至少6位");
        }else {
            User userSelect = userServiceImpl.getByUsername(username);
            if(userSelect!=null){
                message.setState(false);
                message.setMessage("用户名已存在");
            }else{
                User user = new User();
                user.setEmail(email);
                user.setPassword(password);
                user.setUsername(username);
                //notice that 插入需要主键，自动生成的mybatis不包含主键
                if(userServiceImpl.register(user)>0){
                    String token = encrypteUtils.getMD5Code(username,password);
                    redisUtils.set(token,username);
                    message.setUsername(username);
                    message.setState(true);
                    message.setMessage(token);
                }else{
                    message.setState(false);
                    message.setMessage("邮箱已存在");
                }
            }
        }
        return message;
    }

    @ApiOperation("修改密码")
    @RequestMapping(value = "/modifypwd",method = RequestMethod.PUT)
    @AuthToken
    public GeneralMessage modifypwd(@RequestParam("username") String username,
                                    @RequestParam("password") String password){
        GeneralMessage message = new GeneralMessage();
        User user = userServiceImpl.getByUsername(username);
        if(password.length()<6){
            message.setMessage("密码长度至少6位");
            message.setState(false);
        } else if(user!=null&&!user.getPassword().equals(password)){
            user.setPassword(password);
            userServiceImpl.updatePassword(user);
            message.setState(true);
            message.setMessage("修改成功");
        }else{
            message.setMessage("不能和原密码相同");
            message.setState(false);
        }
        return message;
    }
}
