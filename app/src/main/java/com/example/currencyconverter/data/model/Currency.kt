package com.example.currencyconverter.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Currency(
    @PrimaryKey val base: String,
    val rates: Map<String, Double>
)
