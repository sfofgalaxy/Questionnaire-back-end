package com.ziffer.questionnaire.model;

import java.io.Serializable;
import lombok.Data;

/**
 * paper
 * @author 
 */
@Data
public class Paper implements Serializable {
    private Integer paperid;

    private String author;

    private String title;

    private String description;

    private Byte mode;

    /**
     * 一个人能填写的次数
     */
    private Integer fillnumber;

    private Boolean open;

    private static final long serialVersionUID = 1L;
}