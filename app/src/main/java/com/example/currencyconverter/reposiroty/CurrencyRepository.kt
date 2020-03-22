package com.example.currencyconverter.reposiroty

import com.example.currencyconverter.data.api.ExchangeRatesApi
import com.example.currencyconverter.data.model.Currency
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import javax.inject.Inject


class CurrencyRepository @Inject constructor(retrofit: Retrofit, private val gson: Gson) {
    private val networkClient = retrofit.create(ExchangeRatesApi::class.java)
    private val scope = CoroutineScope(Dispatchers.IO)
    private val currenciesList = scope.async {
        gson.fromJson(networkClient.getLatestExchangeRates(null), JsonObject::class.java)
            .getAsJsonObject("rates")
            .keySet()
            .toTypedArray()
    }

    suspend fun getRatesByCurrency(currecy: String) =
        gson.fromJson(networkClient.getLatestExchangeRates(currecy), Currency::class.java)

    fun getCurrenciesTable() = flow<Currency> {
        currenciesList.await().forEach { currency ->
            emit(getRatesByCurrency(currency))
        }
    }.also {
        //cash it!!
        it
    }

    fun getList() = listOf(
        "CAD",
        "HKD",
        "ISK",
        "PHP",
        "DKK",
        "HUF",
        "CZK",
        "GBP",
        "RON",
        "SEK",
        "IDR",
        "INR"
    )
}