package com.ziffer.questionnaire.dto;

import com.ziffer.questionnaire.model.Answer;
import com.ziffer.questionnaire.model.Question;
import com.ziffer.questionnaire.model.Result;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class ResultMessage {
    private boolean state;
    private String message;
    private List<Question> questionList;
    private Map<Result, List<Answer> > resultListMap;
}
