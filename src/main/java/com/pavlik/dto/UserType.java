package com.pavlik.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum UserType {
    UNREGISTERED, REGISTERED, MODERATOR, TEAM_ADMIN, DOES_NOT_EXIST;

    @JsonCreator
    public static UserType forValue(String value) {
        return UserType.valueOf(value.toUpperCase());
    }
}
