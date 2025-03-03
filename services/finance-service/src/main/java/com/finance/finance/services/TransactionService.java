package com.finance.finance.services;

import com.finance.finance.entities.Account;
import com.finance.finance.entities.Category;
import com.finance.finance.entities.Transaction;
import com.finance.finance.models.TransactionType;
import com.finance.finance.repositories.AccountRepository;
import com.finance.finance.repositories.CategoryRepository;
import com.finance.finance.repositories.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Transaction createTransaction(UUID accountId, double amount, TransactionType type, String categoryName) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Счёт не найден"));

        Category category = categoryRepository.findByName(categoryName)
                .orElseGet(() -> createNewCategory(categoryName));

        // Корректируем баланс счёта
        double transactionAmount = Math.abs(amount);
        if (type == TransactionType.EXPENSE) {
            account.setBalance(account.getBalance() - transactionAmount);
        } else {
            account.setBalance(account.getBalance() + transactionAmount);
        }
        accountRepository.save(account);

        Transaction transaction = Transaction.builder()
                .account(account)
                .amount(transactionAmount)
                .type(type)
                .category(category)
                .timestamp(LocalDateTime.now())
                .build();

        return transactionRepository.save(transaction);
    }


    private Category createNewCategory(String name) {
        Category category = new Category();
        category.setName(name);
        return categoryRepository.save(category);
    }



    private Category getOrCreateDefaultCategory() {
        return categoryRepository.findByName("Без категории")
                .orElseGet(() -> {
                    Category defaultCategory = new Category();
                    defaultCategory.setName("Без категории");
                    return categoryRepository.save(defaultCategory);
                });
    }


    public List<Transaction> getTransactions(UUID accountId) {
        return transactionRepository.findByAccountId(accountId);
    }


    public List<Transaction> getTransactionsByType(UUID accountId, TransactionType type) {
        return transactionRepository.findByAccountIdAndType(accountId, type);
    }
}
