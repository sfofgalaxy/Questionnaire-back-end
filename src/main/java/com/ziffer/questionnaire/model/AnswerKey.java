package com.ziffer.questionnaire.model;

import java.io.Serializable;
import lombok.Data;

/**
 * answer
 * @author 
 */
@Data
public class AnswerKey implements Serializable {
    private Integer resultid;

    private Integer questionid;

    private static final long serialVersionUID = 1L;
}