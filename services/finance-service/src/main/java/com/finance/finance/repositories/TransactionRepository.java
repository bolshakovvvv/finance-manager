package com.finance.finance.repositories;

import com.finance.finance.entities.Transaction;
import com.finance.finance.models.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findByAccountId(UUID accountId);  // Все операции по аккаунту
    List<Transaction> findByAccountIdAndType(UUID accountId, TransactionType type);  // Доходы или расходы
}
