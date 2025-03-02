package com.finance.finance.controllers.dto;

import com.finance.finance.models.TransactionType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.util.UUID;

@Data
public class TransactionRequest {

    @NotNull
    private UUID accountId;

    @NotNull
    private double amount;

    @NotNull
    private TransactionType type;

    private String categoryName;
}
