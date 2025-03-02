package com.finance.finance.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;  // Название счета (например, "Карта Сбер", "Наличные")

    @Column(nullable = false)
    private double balance;  // Текущий баланс

    @Column(nullable = false)
    private UUID userId;  // ID владельца (из auth-service)

}
