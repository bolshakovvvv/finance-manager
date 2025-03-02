package com.finance.finance.controllers.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
public class CreateAccountRequest {
    @NotBlank
    private String name;

    @NotNull
    private double initialBalance;
}
