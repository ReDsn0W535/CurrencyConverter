package com.example.currencyconverter.data.api

import com.example.currencyconverter.data.model.Currency
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRatesApi {

    @GET("/latest")
    suspend fun getLatestExchangeRates(@Query("base") baseCurrency : String?) : String
}