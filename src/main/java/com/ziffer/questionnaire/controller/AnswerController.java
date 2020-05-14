package com.ziffer.questionnaire.controller;

import com.ziffer.questionnaire.dto.GeneralMessage;
import com.ziffer.questionnaire.intercepter.AuthToken;
import com.ziffer.questionnaire.model.Answer;
import com.ziffer.questionnaire.model.Paper;
import com.ziffer.questionnaire.model.Result;
import com.ziffer.questionnaire.service.PaperServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
public class AnswerController {
    @Resource
    PaperServiceImpl paperServiceImpl;

    @ApiOperation("获取问卷的结果")
    @RequestMapping(value = "/result/{paperid}",method = RequestMethod.GET)
    @AuthToken
    public Map<Result, List<Answer>> getPaperResult(@PathVariable("paperid") Integer paperid,
                                                    @RequestParam("username") String username){
        Paper paper = paperServiceImpl.getByPaperID(paperid);
        //证明是本人的问卷，可以看见结果
        if(paper.getAuthor().equals(username)){
            return paperServiceImpl.getResult(paperid);
        }else{
            return null;
        }
    }

    /*unfinished*/
    @ApiOperation("回答问卷")
    @RequestMapping(value = "/answer/{paperid}",method = RequestMethod.GET)
    public GeneralMessage answer(@PathVariable("paperid") String paperid,
                                 @RequestParam("answer") List<String> answerList){
        GeneralMessage message = new GeneralMessage();

        return message;
    }

    @ApiOperation("用于测试数据传输格式的接口")
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public List<Paper> test(@RequestParam("username") String username){
        List<Paper> res = paperServiceImpl.getMyPaper(username);
        System.out.println(res.get(0).getTitle());
        return res;
    }
}
