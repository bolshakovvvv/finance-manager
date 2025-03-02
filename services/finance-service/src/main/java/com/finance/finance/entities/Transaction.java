package com.finance.finance.entities;

import com.finance.finance.models.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;  // Связь с аккаунтом

    @Column(nullable = false)
    private double amount;  // Сумма транзакции

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;  // Тип транзакции (INCOME / EXPENSE)

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;  // Категория расхода/дохода

    @Column(nullable = false)
    private LocalDateTime timestamp;  // Дата и время
}
