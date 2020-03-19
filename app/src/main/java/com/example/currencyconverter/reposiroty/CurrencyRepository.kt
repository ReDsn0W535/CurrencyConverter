package com.example.currencyconverter.reposiroty

import com.example.currencyconverter.data.api.ExchangeRatesApi
import com.example.currencyconverter.data.model.Currency
import com.example.currencyconverter.data.model.Rates
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Retrofit
import javax.inject.Inject

class CurrencyRepository @Inject constructor(retrofit: Retrofit, private val gson: Gson) {
    private val networkClient = retrofit.create(ExchangeRatesApi::class.java)

    suspend fun getCurrencies() = gson.fromJson(networkClient.getLatestExchangeRates(null), JsonObject::class.java)
            .getAsJsonObject("rates")
            .keySet()

    suspend fun getRatesByCurrency(currecy : String) = gson.fromJson(networkClient.getLatestExchangeRates(currecy), Currency::class.java)
}

