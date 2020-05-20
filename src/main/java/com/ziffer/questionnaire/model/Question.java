package com.ziffer.questionnaire.model;

import java.io.Serializable;
import lombok.Data;

/**
 * question
 * @author 
 */
@Data
public class Question implements Serializable {
    private Integer questionid;

    private Integer paperid;

    private Byte type;

    private String content;

    private static final long serialVersionUID = 1L;
}