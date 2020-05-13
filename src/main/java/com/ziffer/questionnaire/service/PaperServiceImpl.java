package com.ziffer.questionnaire.service;

import com.ziffer.questionnaire.mapper.OptionDao;
import com.ziffer.questionnaire.mapper.PaperDao;
import com.ziffer.questionnaire.mapper.QuestionDao;
import com.ziffer.questionnaire.model.Option;
import com.ziffer.questionnaire.model.Paper;
import com.ziffer.questionnaire.model.Question;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PaperServiceImpl implements PaperService {
    @Resource
    PaperDao paperDao;
    @Resource
    QuestionDao questionDao;
    @Resource
    OptionDao optionDao;
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
}
