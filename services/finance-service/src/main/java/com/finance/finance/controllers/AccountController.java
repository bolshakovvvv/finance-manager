package com.finance.finance.controllers;

import com.finance.finance.controllers.dto.BalanceUpdateRequest;
import com.finance.finance.controllers.dto.CreateAccountRequest;
import com.finance.finance.entities.Account;
import com.finance.finance.services.AccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<List<Account>> getAccounts(Principal principal) {
        UUID userId = UUID.fromString(principal.getName());
        return ResponseEntity.ok(accountService.getAccounts(userId));
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody @Valid CreateAccountRequest request, Principal principal) {
        UUID userId = UUID.fromString(principal.getName());
        Account account = accountService.createAccount(request.getName(), userId, request.getInitialBalance());
        return ResponseEntity.ok(account);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable UUID id, Principal principal) {
        UUID userId = UUID.fromString(principal.getName());
        accountService.deleteAccount(id, userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/balance")
    public ResponseEntity<Account> updateBalance(@PathVariable UUID id, @RequestBody BalanceUpdateRequest request) {
        Account updatedAccount = accountService.updateBalance(id, request.getAmount());
        return ResponseEntity.ok(updatedAccount);
    }
}
