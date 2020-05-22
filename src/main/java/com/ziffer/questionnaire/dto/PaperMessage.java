package com.ziffer.questionnaire.dto;

import com.ziffer.questionnaire.model.Option;
import com.ziffer.questionnaire.model.Question;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PaperMessage {
    private boolean state;
    private String message;
    private String title;
    private String description;
    Map<Question, List<Option>> question;
}
