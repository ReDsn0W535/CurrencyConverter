package com.example.currencyconverter.reposiroty

import com.example.currencyconverter.data.api.ExchangeRatesApi
import com.example.currencyconverter.data.database.CurrencyDao
import com.example.currencyconverter.data.model.Currency
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import retrofit2.Retrofit
import javax.inject.Inject


class CurrencyRepository @Inject constructor(
    retrofit: Retrofit,
    private val gson: Gson,
    private val currencyDao: CurrencyDao
) {
    private val networkClient = retrofit.create(ExchangeRatesApi::class.java)
    private val scope = CoroutineScope(Dispatchers.IO)
    private val currenciesList = scope.async {
        gson.fromJson(networkClient.getLatestExchangeRates(null), JsonObject::class.java)
            .getAsJsonObject("rates")
            .keySet()
            .toTypedArray()
    }
    private val currenciesTable = scope.async {
        val list = arrayListOf<Currency>()
        currenciesList.await().forEach { currency ->
            list.add(getRatesByCurrency(currency))
        }
        list
    }

    private suspend fun getRatesByCurrency(currency: String) =
        gson.fromJson(networkClient.getLatestExchangeRates(currency), Currency::class.java)


    fun getList(): Array<String> {
        return try {
            currenciesList.getCompleted()
        } catch (e: Exception) {
            currenciesList.cancel()
            val curNames = arrayListOf<String>()
            currencyDao.getAll().forEach {
                curNames.add(it.base)
            }
            curNames.toTypedArray()
        }
    }

    fun getTable(): ArrayList<Currency> {
        return try {
            currenciesTable.getCompleted()
        } catch (e: Exception) {
            currenciesTable.cancel()
            arrayListOf<Currency>().also {
                it.addAll(currencyDao.getAll())
            }
        }

    }
/*    fun getCurrenciesTable(): ArrayList<Currency> {
        return try {
            currenciesTable.getCompleted()
        } catch (e: Exception) {
            currenciesTable.cancel()
            currencyDao.getAll() as ArrayList<Currency>
        }
    }*/

    fun convert(from: String, to: String): Double {
        getTable().forEach {
            if (it.base == from)
                return it.rates?.get(to) ?: error("not found")
        }
        error("not found")
    }
}

