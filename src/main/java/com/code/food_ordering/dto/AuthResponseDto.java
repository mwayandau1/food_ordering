package com.code.food_ordering.dto;

import com.code.food_ordering.entity.ROLE;
import lombok.Data;

@Data
public class AuthResponseDto {
    private String jwt;

    private String message;

    private ROLE role;
}
