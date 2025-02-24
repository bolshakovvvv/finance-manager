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
import org.springframework.beans.factory.annotation.Autowired;
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
    public Transaction createTransaction(UUID accountId, BigDecimal amount, TransactionType type, UUID categoryId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Счёт не найден"));

        // Если указан categoryId, ищем категорию
        Category category = null;
        if (categoryId != null) {
            category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new EntityNotFoundException("Категория не найдена"));
        }

        // Корректируем баланс счёта
        if (type == TransactionType.EXPENSE) {
            amount = amount.negate();  // Расходы уменьшают баланс
        }

        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);

        // Создаём и сохраняем транзакцию
        Transaction transaction = Transaction.builder()
                .account(account)
                .amount(amount.abs())  // Сохраняем только положительное значение
                .type(type)
                .category(category)
                .timestamp(LocalDateTime.now())
                .build();

        return transactionRepository.save(transaction);
    }

    /**
     * Получить все транзакции счёта
     */
    public List<Transaction> getTransactions(UUID accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    /**
     * Получить все транзакции по типу (доходы/расходы)
     */
    public List<Transaction> getTransactionsByType(UUID accountId, TransactionType type) {
        return transactionRepository.findByAccountIdAndType(accountId, type);
    }
}
