package com.ziffer.questionnaire.controller;

import com.ziffer.questionnaire.dto.GeneralMessage;
import com.ziffer.questionnaire.dto.ResultMessage;
import com.ziffer.questionnaire.intercepter.AuthToken;
import com.ziffer.questionnaire.model.*;
import com.ziffer.questionnaire.service.PaperServiceImpl;
import com.ziffer.questionnaire.service.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
public class AnswerController {
    @Resource
    PaperServiceImpl paperServiceImpl;
    @Resource
    UserServiceImpl userServiceImpl;

    @ApiOperation("获取问卷的结果")
    @RequestMapping(value = "/result/{paperid}",method = RequestMethod.GET)
    @AuthToken
    public ResultMessage getPaperResult(@PathVariable("paperid") Integer paperid,
                                        @RequestParam("username") String username){
        ResultMessage message = new ResultMessage();
        Paper paper = paperServiceImpl.getByPaperID(paperid);
        List<Question> questions = paperServiceImpl.getQuestionWithoutOption(paperid);
        Map<Result, List<Answer> > resultListMap = paperServiceImpl.getResult(paperid);
        //证明是本人的问卷，可以看见结果
        if(paper.getAuthor().equals(username)){
            message.setMessage("获取成功");
            message.setState(true);
            message.setQuestionList(questions);
            message.setResultListMap(resultListMap);
        }else{
            message.setMessage("获取失败");
            message.setState(false);
        }
        return message;
    }

    @ApiOperation("回答问卷")
    @RequestMapping(value = "/answer/{paperid}",method = RequestMethod.POST)
    public GeneralMessage answer(@PathVariable("paperid") Integer paperid,
                                 @RequestParam("answer") List<String> answerList,
                                 @RequestParam("ip")String ip,
                                 //如果无需注册则username设为""
                                 @RequestParam("username")String username){
        GeneralMessage message = new GeneralMessage();
        //先获取paper并查询paper模式
        Paper paper = paperServiceImpl.getByPaperID(paperid);
        byte mode = paper.getMode();
        //判断是否可以回答问卷
        if(mode==0){
            //仅注册可答
            //未查询到直接返回null
            User user = userServiceImpl.getByUsername(username);
            if(user!=null){
                paperServiceImpl.answer(paperid,answerList,ip,username);
                message.setState(true);
                message.setMessage("作答成功");
            }else{
                message.setState(false);
                message.setMessage("登录信息有误，请重新登录");
            }
        }else if(mode==1){
            //无需注册，可填写n次
            if(paperServiceImpl.getFillNum(paperid,ip)<paper.getFillnumber()){
                paperServiceImpl.answer(paperid,answerList,ip,username);
                message.setState(true);
                message.setMessage("作答成功");
            }else{
                message.setState(false);
                message.setMessage("回答次数超过上限");
            }
        }else{
            //无需注册，每天可填写n次
            if(paperServiceImpl.getFillNumToday(paperid,ip)<paper.getFillnumber()){
                paperServiceImpl.answer(paperid,answerList,ip,username);
                message.setState(true);
                message.setMessage("作答成功");
            }else{
                message.setState(false);
                message.setMessage("今日回答次数超过上限");
            }
            paperServiceImpl.answer(paperid,answerList,ip,username);
            message.setState(true);
            message.setMessage("作答成功");
        }

        return message;
    }
}
