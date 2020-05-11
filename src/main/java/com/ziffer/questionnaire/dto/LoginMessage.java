package com.ziffer.questionnaire.dto;

import lombok.Data;

@Data
public class LoginMessage {
    private boolean state;
    private String message;//message存token
    private String username;
}
