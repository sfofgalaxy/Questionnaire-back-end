package com.ziffer.questionnaire.service;

import com.ziffer.questionnaire.mapper.*;
import com.ziffer.questionnaire.model.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaperServiceImpl implements PaperService {
    @Resource
    PaperDao paperDao;
    @Resource
    QuestionDao questionDao;
    @Resource
    OptionDao optionDao;
    @Resource
    ResultDao resultDao;
    @Resource
    AnswerDao answerDao;
    @Override
    public int post(Paper paper) {
        paperDao.insert(paper);
        return paper.getPaperid();
    }

    @Override
    public int insertQuestion(Question question) {
        questionDao.insert(question);
        return question.getQuestionid();
    }

    @Override
    public int insertOption(Option option) {
        optionDao.insert(option);
        return option.getOptionid();
    }

    @Override
    public List<Paper> getMyPaper(String username) {
        return paperDao.selectByAuthor(username);
    }

    @Override
    public Paper getByPaperID(Integer paperID) {
        return paperDao.selectByPrimaryKey(paperID);
    }

    @Override
    public boolean openPaper(Integer paperID) {
        return paperDao.updateStateByPrimaryKey(paperID, true)>0;
    }

    @Override
    public boolean closePaper(Integer paperID) {
        return paperDao.updateStateByPrimaryKey(paperID,false)>0;
    }

    @Override
    public Map<Result, List<Answer>> getResult(Integer paperid) {
        Map<Result, List<Answer>> resultListMap = new HashMap<>();
        List<Result> results = resultDao.selectByPaperid(paperid);
        List<Answer> answers;
        for(int i=0;i<results.size();i++){
            Result result = results.get(i);
            answers = answerDao.selectByResultid(result.getResultid());
            resultListMap.put(result,answers);
        }
        return  resultListMap;
    }

    @Override
    public Map<Question, List<Option>> getQuestion(Integer paperid) {
        Map<Question, List<Option>> questionListMap = new HashMap<>();
        List<Question> questions = questionDao.selectByPaperid(paperid);
        List<Option> options;
        for(int i=0;i<questions.size();i++){
            Question question = questions.get(i);
            options = optionDao.selectByQuestionid(question.getQuestionid());
            questionListMap.put(question,options);
        }
        return  questionListMap;
    }
}
