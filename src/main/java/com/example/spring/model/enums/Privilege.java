package com.example.spring.model.enums;

import org.springframework.stereotype.Component;


public enum Privilege {
CAN_READ("CAN_READ"),
CAN_WRITE("CAN_WRITE"),
CAN_DELETE("CAN_DELETE"),
CAN_UPDATE("CAN_UPDATE");


    private final String displayName;

    Privilege(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
