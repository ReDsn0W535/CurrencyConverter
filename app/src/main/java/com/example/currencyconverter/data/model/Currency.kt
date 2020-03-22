package com.example.currencyconverter.data.model

data class Currency(
    val base: String,
    val rates: Map<String, Double>
)
