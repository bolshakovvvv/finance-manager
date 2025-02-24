package com.finance.finance.controllers.dto;

import com.finance.finance.models.TransactionType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class TransactionRequest {

    @NotNull
    private UUID accountId;  // ID счёта

    @NotNull
    private BigDecimal amount;  // Сумма транзакции

    @NotNull
    private TransactionType type;  // Тип (INCOME / EXPENSE)

    private UUID categoryId;  // (Необязательно) ID категории

}
