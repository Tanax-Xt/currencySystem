package ru.tanaxxt.currencysystem.entities

import jakarta.persistence.*
import java.util.UUID

@Entity
data class Currency(
    @Column(nullable = false)
    var name: String = "",

    @Column(nullable = false, unique = true)
    var baseCurrency: String = "",

    @Column(nullable = false)
    var priceChangeRange: String = "",

    @Column
    var description: String? = null,
) {
    @Id
    var id: UUID = UUID.randomUUID()

    @Column
    var isDeleted: Boolean = false
}
