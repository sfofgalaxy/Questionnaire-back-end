package com.ziffer.questionnaire.controller;

import com.ziffer.questionnaire.dto.GeneralMessage;
import com.ziffer.questionnaire.intercepter.AuthToken;
import com.ziffer.questionnaire.mapper.PaperDao;
import com.ziffer.questionnaire.model.Question;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/paper")
public class PaperController {
    @Resource
    private PaperDao paperDao;
    @RequestMapping(value = "/post",method = RequestMethod.POST)
    @AuthToken
    public GeneralMessage postPaper(@RequestParam("username") String username,
                                    @RequestParam("title") String title,
                                    @RequestParam("description") String description,
                                    @RequestParam("mode") Byte mode,
                                    @RequestParam("fillNumber") Integer fillNumber,
                                    @RequestParam("start") Date start,
                                    @RequestParam("end") Date end,
                                    @RequestParam("question") List<Question> questionList){
        GeneralMessage message = new GeneralMessage();
        System.out.println(questionList);
        return message;
    }
    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public GeneralMessage test(@RequestParam("question") List<Question> questionList){
        GeneralMessage message = new GeneralMessage();
        System.out.println(questionList);
        return message;
    }
}
