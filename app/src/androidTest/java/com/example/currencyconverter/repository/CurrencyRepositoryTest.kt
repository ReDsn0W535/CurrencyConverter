package com.example.currencyconverter.repository

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.currencyconverter.data.api.ExchangeRatesApi
import com.example.currencyconverter.data.database.CurrencyDatabase
import com.example.currencyconverter.data.model.Currency
import com.example.currencyconverter.reposiroty.CurrencyRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit

@RunWith(MockitoJUnitRunner::class)
class CurrencyRepositoryTest {

    var repository: CurrencyRepository? = null

    @Before
    fun setUp() = runBlocking {
        val retrofit = Mockito.mock(Retrofit::class.java)
        val api = Mockito.mock(ExchangeRatesApi::class.java)
        val db = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                CurrencyDatabase::class.java
        )
                .build()
        val dao = db.currencyDao()


        Mockito.`when`(retrofit.create(ExchangeRatesApi::class.java)).thenReturn(api)
        Mockito.`when`(api.getLatestExchangeRates("USD"))
            .thenReturn(JsonParser().parse(stringUsd).asJsonObject)
        Mockito.`when`(api.getLatestExchangeRates(null))
            .thenReturn(JsonParser().parse(stringUsd).asJsonObject)
        Mockito.`when`(api.getLatestExchangeRates("RUB"))
            .thenReturn(JsonParser().parse(stringRub).asJsonObject)
        repository = CurrencyRepository(retrofit = retrofit, gson = GsonBuilder()
                .setLenient()
                .setPrettyPrinting()
                .create(), currencyDao = dao)
    }

    @After
    fun tearDown() {
        repository = null
    }

    @Test
    fun getList() {
        val list = repository?.getList()!!
        assert(list.contains("USB") && list.contains("RUB"))
    }

    @Test
    fun getTable() {
        val table = repository?.getTable()!!
        assert(table.contains(Gson().fromJson(stringRub, Currency::class.java)) && table.contains(Gson().fromJson(stringUsd, Currency::class.java)))
    }

    @Test
    fun convert() {
        assertEquals(78.3240971645 ,repository?.convert("USB", "RUB"))
        assertEquals(1.0 ,repository?.convert("RUB", "RUB"))
        assertEquals(0.0127674628 ,repository?.convert("RUB", "USD"))
    }


    companion object {
        const val stringUsd = "{\n" +
                "    \"rates\": {\n" +
                "        \"RUB\": 78.3240971645,\n" +
                "        \"USD\": 1.0,\n" +
                "    },\n" +
                "    \"base\": \"USD\",\n" +
                "    \"date\": \"2020-03-25\"\n" +
                "}"

        const val stringRub = "{\n" +
                "    \"rates\": {\n" +
                "        \"RUB\": 1.0,\n" +
                "        \"USD\": 0.0127674628,\n" +
                "    },\n" +
                "    \"base\": \"RUB\",\n" +
                "    \"date\": \"2020-03-25\"\n" +
                "}"
    }
}