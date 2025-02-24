package com.finance.finance.repositories;

import com.finance.finance.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    List<Account> findByUserId(UUID userId);  // Получить все счета пользователя
    Optional<Account> findByNameAndUserId(String name, UUID userId);  // Найти счёт по имени
}
