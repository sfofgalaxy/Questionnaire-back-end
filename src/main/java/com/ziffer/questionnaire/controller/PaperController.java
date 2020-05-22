package com.ziffer.questionnaire.controller;

import com.ziffer.questionnaire.dto.GeneralMessage;
import com.ziffer.questionnaire.dto.PaperMessage;
import com.ziffer.questionnaire.intercepter.AuthToken;
import com.ziffer.questionnaire.model.*;
import com.ziffer.questionnaire.service.PaperServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/paper")
public class PaperController {
    @Resource
    private PaperServiceImpl paperServiceImpl;

    @ApiOperation("发布问卷")
    @RequestMapping(value = "/post",method = RequestMethod.POST)
    @AuthToken
    public GeneralMessage postPaper(Paper paper,
                                    @RequestParam("question") List<String> questionList,
                                    @RequestParam("option") List<String> optionList){
        GeneralMessage message = new GeneralMessage();
        Option option = new Option();
        Question question = new Question();
        int paperID = paperServiceImpl.post(paper), questionStartID=0;;
        int questionNum = questionList.size()/2,optionNum=optionList.size()/2;
        for(int i=0;i<questionNum;i++){
            question.setPaperid(paperID);
            question.setContent(questionList.get(i*2));
            question.setType((byte)Integer.parseInt(questionList.get(i*2+1)));
            if(i==0)questionStartID = paperServiceImpl.insertQuestion(question);
            else paperServiceImpl.insertQuestion(question);
        }
        for(int i=0;i<optionNum;i++){
            option.setQuestionid(questionStartID+Integer.parseInt(optionList.get(2*i))-1);
            option.setContent(optionList.get(i*2+1));
            paperServiceImpl.insertOption(option);
        }
        message.setState(true);
        message.setMessage(""+paperID);
        return message;
    }

    @ApiOperation("获取我的所有问卷")
    @RequestMapping(value = "/myquestionnaire",method = RequestMethod.GET)
    @AuthToken
    public List<Paper> getMyQuestionnaire(@RequestParam("username") String username){
        return paperServiceImpl.getMyPaper(username);
    }

    @ApiOperation("查看问卷")
    @RequestMapping(value = "/{paperid}",method = RequestMethod.GET)
    public PaperMessage checkPaper(@PathVariable("paperid") Integer paperid){
        Paper paper = paperServiceImpl.getByPaperID(paperid);
        PaperMessage message = new PaperMessage();
        //先查看是否开
        message.setState(paper.getOpen());
        if(message.isState()){
            //再查看模式
            byte mode = paper.getMode();
            //仅限注册用户
            if(mode==0){
                //获取题目
                Map<Question, List<Option>> questionListMap;
                questionListMap = paperServiceImpl.getQuestion(paperid);
                message.setMessage("获取问卷成功");
                message.setTitle(paper.getTitle());
                message.setDescription(paper.getDescription());
                message.setQuestion(questionListMap);
            }
            //无需注册，可以填写n次或者每天填写n次
            else{
                message.setMessage("请登陆后作答");
            }
        }else{
            message.setMessage("问卷已关闭");
        }
        return message;
    }

    @ApiOperation("开放问卷")
    @RequestMapping(value = "/open/{paperid}",method = RequestMethod.PUT)
    @AuthToken
    public GeneralMessage openPaper(@PathVariable("paperid") Integer paperid){
        GeneralMessage message = new GeneralMessage();
        if(paperServiceImpl.openPaper(paperid)){
            message.setState(true);
            message.setMessage("打开成功");
        }else{
            message.setState(false);
            message.setMessage("打开失败，请重试");
        }
        return message;
    }

    @ApiOperation("关闭问卷")
    @RequestMapping(value = "/close/{paperid}",method = RequestMethod.PUT)
    @AuthToken
    public GeneralMessage closePaper(@PathVariable("paperid") Integer paperid){
        GeneralMessage message = new GeneralMessage();
        if(paperServiceImpl.closePaper(paperid)){
            message.setState(true);
            message.setMessage("关闭成功");
        }else{
            message.setState(false);
            message.setMessage("关闭失败，请重试");
        }
        return message;
    }

    @ApiOperation("删除问卷")
    @RequestMapping(value = "/delete/{paperid}",method = RequestMethod.DELETE)
    @AuthToken
    public GeneralMessage deletePaper(@PathVariable("paperid") Integer paperid){
        GeneralMessage message = new GeneralMessage();
        if(paperServiceImpl.deletePaper(paperid)){
            message.setState(true);
            message.setMessage("删除成功");
        }else{
            message.setState(false);
            message.setMessage("删除失败，请重试");
        }
        return message;
    }
}
