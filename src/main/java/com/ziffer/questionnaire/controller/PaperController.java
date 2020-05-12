package com.ziffer.questionnaire.controller;

import com.ziffer.questionnaire.dto.GeneralMessage;
import com.ziffer.questionnaire.intercepter.AuthToken;
import com.ziffer.questionnaire.mapper.PaperDao;
import com.ziffer.questionnaire.model.Paper;
import com.ziffer.questionnaire.model.Question;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/paper")
public class PaperController {
    @Resource
    private PaperDao paperDao;
    @RequestMapping(value = "/post",method = RequestMethod.POST)
    @AuthToken
    public GeneralMessage postPaper(Paper paper,
                                    @RequestParam("question") List<Question> questionList){
        GeneralMessage message = new GeneralMessage();
        System.out.println(questionList);
        return message;
    }
    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public GeneralMessage test(@RequestParam("question") String params){
        GeneralMessage message = new GeneralMessage();
        System.out.println(params);
        //传输的为：
//        let data = [
//                  [1,1,1,1],
//                  [2,1,1,1],
//                  [3,1,1,1],
//                  [4,1,1,1]
//            ];
        //接收的参数形式为1,1,1,1,2,1,1,1,3,1,1,1,4,1,1,1
        return message;
    }
}
