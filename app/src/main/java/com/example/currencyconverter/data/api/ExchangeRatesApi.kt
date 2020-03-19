package com.example.currencyconverter.data.api

import retrofit2.http.GET

interface ExchangeRatesApi {

    @GET("/latest")
    suspend fun getLatestExchangeRates()
}