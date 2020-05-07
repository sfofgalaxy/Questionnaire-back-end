package com.ziffer.questionnaire.model;

import java.io.Serializable;
import lombok.Data;

/**
 * user
 * @author 彭子帆
 */
@Data
public class User implements Serializable {
    private String email;

    private String username;

    private String password;

    private static final long serialVersionUID = 1L;
}