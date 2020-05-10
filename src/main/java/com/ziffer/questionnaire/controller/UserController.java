package com.ziffer.questionnaire.controller;

import com.ziffer.questionnaire.dto.GeneralMessage;
import com.ziffer.questionnaire.intercepter.AuthToken;
import com.ziffer.questionnaire.model.User;
import com.ziffer.questionnaire.service.UserService;
import com.ziffer.questionnaire.utils.EncrypteUtils;
import com.ziffer.questionnaire.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    //@Autowired
    UserService userService;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    private EncrypteUtils encrypteUtils;
    //等价@GetMapping("/listAll")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public List<User> list(){
        return userService.listAll();
    }

    //等价@PostMapping("/login")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public GeneralMessage login(@RequestParam("username") String username,
                                @RequestParam("password") String password){
        GeneralMessage message = new GeneralMessage();
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
            User user = userService.selectByUsername(username);
            if(user==null){
                message.setState(false);
                message.setMessage("用户名或密码错误");
            }else if(!user.getPassword().equals(password)){
                message.setState(false);
                message.setMessage("用户名或密码错误");
            }else{
                String token = encrypteUtils.getMD5Code(username,password);
                redisUtils.set(token,username,86400);
                message.setState(true);
                message.setMessage(token);
            }
        }
        return message;
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public GeneralMessage register(@RequestParam("username") String username,
                                    @RequestParam("password") String password,
                                    @RequestParam("email") String email){
        GeneralMessage message = new GeneralMessage();
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
            User userSelect = userService.selectByUsername(username);
            if(userSelect!=null){
                message.setState(false);
                message.setMessage("用户名已存在");
            }else{
                User user = new User();
                user.setEmail(email);
                user.setPassword(password);
                user.setUsername(username);
                if(userService.insertUser(user)>0){
                    String token = encrypteUtils.getMD5Code(username,password);
                    redisUtils.set(token,username);
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

    @RequestMapping("/modifypwd")
    @AuthToken
    public GeneralMessage modifypwd(@RequestParam("token") String token,
                                    @RequestParam("password") String password){
        GeneralMessage message = new GeneralMessage();

        return message;
    }
}
