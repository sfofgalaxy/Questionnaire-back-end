package com.ziffer.questionnaire.model;

import java.io.Serializable;
import lombok.Data;

/**
 * option
 * @author 
 */
@Data
public class Option implements Serializable {
    private Integer optionid;

    private Integer questionid;

    private String content;

    private static final long serialVersionUID = 1L;
}