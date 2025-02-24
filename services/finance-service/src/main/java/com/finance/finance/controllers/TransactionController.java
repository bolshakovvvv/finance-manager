package com.finance.finance.controllers;

import com.finance.finance.controllers.dto.TransactionRequest;
import com.finance.finance.entities.Transaction;
import com.finance.finance.models.TransactionType;
import com.finance.finance.services.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody @Valid TransactionRequest request) {
        Transaction transaction = transactionService.createTransaction(
                request.getAccountId(),
                request.getAmount(),
                request.getType(),
                request.getCategoryId()
        );
        return ResponseEntity.ok(transaction);
    }


    @GetMapping
    public ResponseEntity<List<Transaction>> getTransactions(
            @RequestParam UUID accountId,
            @RequestParam(required = false) TransactionType type
    ) {
        List<Transaction> transactions = (type == null)
                ? transactionService.getTransactions(accountId)
                : transactionService.getTransactionsByType(accountId, type);

        return ResponseEntity.ok(transactions);
    }
}
