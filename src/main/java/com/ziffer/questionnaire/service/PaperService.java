package com.ziffer.questionnaire.service;

import com.ziffer.questionnaire.model.*;

import java.util.List;
import java.util.Map;


public interface PaperService {
    int post(Paper paper);//返回插入的ID
    int insertQuestion(Question question);
    int insertOption(Option option);
    List<Paper> getMyPaper(String username);
    Paper getByPaperID(Integer paperID);
    boolean openPaper(Integer paperID);
    boolean closePaper(Integer paperID);
    boolean deletePaper(Integer paperid);
    Map<Result, List<Answer> > getResult(Integer paperID);
    Map<Question, List<Option>> getQuestion(Integer paperid);
    List<Question> getQuestionWithoutOption(Integer paperid);
    void answer(Integer paperid, List<String> answerList, String ip, String username);
    int getFillNum(Integer paperid, String ip);
    int getFillNumToday(Integer paperid, String ip);
}
