package com.web.prj.enums;

import lombok.Getter;

@Getter
public enum ROLE {
    ADMIN("admin"),
    USER("user");

    private final String level;

    ROLE(String level) {
        this.level = level;
    }


}
