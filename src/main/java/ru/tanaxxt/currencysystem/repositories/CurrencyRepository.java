package ru.tanaxxt.currencysystem.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.tanaxxt.currencysystem.entities.Currency;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, UUID> {
    List<Currency> findByIsDeletedFalse();
    Optional<Currency> findByNameAndIsDeletedFalse(String name);
    Optional<Currency> findByIdAndIsDeletedFalse(UUID id);
}