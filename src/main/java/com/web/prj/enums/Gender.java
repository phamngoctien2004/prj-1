package com.web.prj.enums;

public enum Gender {
    MALE(0), // 0 - male
    FEMALE(1); // 1 - female

    private final int value;

    Gender(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Gender fromValue(int value) {
        for (Gender gender : Gender.values()) {
            if (gender.value == value) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Invalid gender value: " + value);
    }
}
