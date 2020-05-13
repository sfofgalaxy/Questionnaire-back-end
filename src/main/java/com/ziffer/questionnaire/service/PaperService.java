package com.ziffer.questionnaire.service;

import com.ziffer.questionnaire.model.Option;
import com.ziffer.questionnaire.model.Paper;
import com.ziffer.questionnaire.model.Question;


public interface PaperService {
    int post(Paper paper);//返回插入的ID
    int insertQuestion(Question question);
    int insertOption(Option option);
}
