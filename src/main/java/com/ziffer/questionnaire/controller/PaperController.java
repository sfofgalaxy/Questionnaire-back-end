package com.ziffer.questionnaire.controller;

import com.ziffer.questionnaire.dto.GeneralMessage;
import com.ziffer.questionnaire.intercepter.AuthToken;
import com.ziffer.questionnaire.service.PaperServiceImpl;
import com.ziffer.questionnaire.model.Option;
import com.ziffer.questionnaire.model.Paper;
import com.ziffer.questionnaire.model.Question;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/paper")
public class PaperController {
    @Resource
    private PaperServiceImpl paperServiceImpl;

    @RequestMapping(value = "/post",method = RequestMethod.POST)
    @AuthToken
    public GeneralMessage postPaper(Paper paper,
                                    @RequestParam("question") List<String> questionList,
                                    @RequestParam("option") List<String> optionList){
        GeneralMessage message = new GeneralMessage();
        Option option = new Option();
        Question question = new Question();
        int paperID = paperServiceImpl.post(paper),questionStartID=0;;
        int questionNum = questionList.size()/2,optionNum=optionList.size()/2;
        for(int i=0;i<questionNum;i++){
            question.setPaperid(paperID);
            //注意parentID这里，第一个question不能设置级联
            int parentID = Integer.parseInt(questionList.get(i*2));
            if(parentID==0) question.setParentid(0);
            else question.setParentid(questionStartID+paperID-1);
            question.setType((byte)Integer.parseInt(questionList.get(i*2+1)));
            if(i==0)questionStartID = paperServiceImpl.insertQuestion(question);

        }
        for(int i=0;i<optionNum;i++){
            option.setQuestionid(questionStartID+Integer.parseInt(optionList.get(2*i))-1);
            option.setContent(optionList.get(i*2+1));
            paperServiceImpl.insertOption(option);
        }
        message.setState(true);
        message.setMessage("发布成功");
        return message;
    }
    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public GeneralMessage test(Paper paper,
                               @RequestParam("question") List<String> questionList,
                               @RequestParam("option") List<String> optionList){
        GeneralMessage message = new GeneralMessage();
        Option option = new Option();
        Question question = new Question();
        int paperID = paperServiceImpl.post(paper), questionStartID=0;;
        int questionNum = questionList.size()/2,optionNum=optionList.size()/2;
        for(int i=0;i<questionNum;i++){
            question.setPaperid(paperID);
            //注意parentID这里，第一个question不能设置级联
            int parentID = Integer.parseInt(questionList.get(i*2));
            if(parentID==0) question.setParentid(0);
            else question.setParentid(questionStartID+paperID-1);
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
        message.setMessage("发布成功");
        return message;
    }
}
