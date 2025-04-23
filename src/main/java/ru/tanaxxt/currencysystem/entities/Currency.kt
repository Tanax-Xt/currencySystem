package ru.tanaxxt.currencysystem.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String baseCurrency;

    @Column(nullable = false)
    private String priceChangeRange;

    @Column()
    private String description;

    @Column()
    private boolean isDeleted;
}
