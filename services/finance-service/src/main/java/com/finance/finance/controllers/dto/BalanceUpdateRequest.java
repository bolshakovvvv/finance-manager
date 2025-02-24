package com.finance.finance.controllers.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BalanceUpdateRequest {

    @NotNull
    private BigDecimal amount;

}
