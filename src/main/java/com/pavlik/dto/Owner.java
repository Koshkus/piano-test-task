package com.pavlik.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Owner {
    private int acceptRate;
    private int reputation;
    private long userId;
    private UserType userType;
    private String link;
    private String displayName;
}
