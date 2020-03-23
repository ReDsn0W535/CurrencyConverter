package com.example.currencyconverter.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.currencyconverter.data.database.converters.RatesConverter


@Entity
data class Currency(
    @PrimaryKey val base: String,
    val rates: Map<String, Double>?
)
