package com.ziffer.questionnaire.model;

import java.io.Serializable;
import lombok.Data;

/**
 * answer
 * @author 
 */
@Data
public class Answer extends AnswerKey implements Serializable {
    private String content;

    private static final long serialVersionUID = 1L;
}