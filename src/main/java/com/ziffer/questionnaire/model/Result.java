package com.ziffer.questionnaire.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * result
 * @author 
 */
@Data
public class Result implements Serializable {
    private Integer resultid;

    private String username;

    private String ip;

    private Integer paperid;

    private Date submittime;

    private static final long serialVersionUID = 1L;
}