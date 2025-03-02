package com.finance.finance.services;

import com.finance.finance.entities.Account;
import com.finance.finance.repositories.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account createAccount(String name, UUID userId, double initialBalance) {
        if (accountRepository.findByNameAndUserId(name, userId).isPresent()) {
            throw new RuntimeException("Счёт с таким именем уже существует");
        }

        Account account = Account.builder()
                .name(name)
                .userId(userId)
                .balance(initialBalance)
                .build();

        return accountRepository.save(account);
    }

    public List<Account> getAccounts(UUID userId) {
        return accountRepository.findByUserId(userId);
    }

    public void deleteAccount(UUID accountId, UUID userId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Счёт не найден"));

        if (!account.getUserId().equals(userId)) {
            throw new RuntimeException("Вы не можете удалить этот счёт");
        }

        accountRepository.delete(account);
    }

    @Transactional
    public Account updateBalance(UUID accountId, double amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Счёт не найден"));

        account.setBalance(account.getBalance() + amount);
        return accountRepository.save(account);
    }
}
