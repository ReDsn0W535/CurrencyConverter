package com.example.currencyconverter.repository

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.currencyconverter.data.api.ExchangeRatesApi
import com.example.currencyconverter.data.database.CurrencyDao
import com.example.currencyconverter.data.database.CurrencyDatabase
import com.example.currencyconverter.data.model.Currency
import com.example.currencyconverter.reposiroty.CurrencyRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CurrencyRepositoryTest {

    var repository: CurrencyRepository? = null

    @Before
    fun setUp() = runBlocking {
        val gson = GsonBuilder()
                .setLenient()
                .setPrettyPrinting()
                .create()
        val api = Mockito.mock(ExchangeRatesApi::class.java)
        val db = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                CurrencyDatabase::class.java
        )
                .build()
        val dao = Mockito.mock(CurrencyDao::class.java)/*db.currencyDao()*/
        Mockito.`when`(dao.getAll()).thenReturn(arrayListOf(
                gson.fromJson(stringUsd, Currency::class.java),
                gson.fromJson(stringRub, Currency::class.java)
        ))
        Mockito.`when`(api.getLatestExchangeRates("USD"))
                .thenReturn(gson.fromJson(stringUsd, JsonObject::class.java))
        Mockito.`when`(api.getLatestExchangeRates(null))
                .thenReturn(gson.fromJson(stringUsd, JsonObject::class.java))
        Mockito.`when`(api.getLatestExchangeRates("RUB"))
                .thenReturn(gson.fromJson(stringRub, JsonObject::class.java))
        repository = CurrencyRepository(networkClient = api, gson = gson, currencyDao = dao)
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
        repository?.convert("USD", "RUB")
        assertEquals(1.0 ,repository?.convert("RUB", "RUB"))
        assertEquals(1.0 ,repository?.convert("USD", "USD"))
    }


    companion object {
        const val stringUsd = "{\n" +
                "    \"rates\": {\n" +
                "        \"CAD\": 1.4161682074,\n" +
                "        \"HKD\": 7.7541236179,\n" +
                "        \"ISK\": 139.8404930216,\n" +
                "        \"PHP\": 50.9298531811,\n" +
                "        \"DKK\": 6.7669929309,\n" +
                "        \"HUF\": 325.1042233098,\n" +
                "        \"CZK\": 24.7553017945,\n" +
                "        \"GBP\": 0.805691499,\n" +
                "        \"RON\": 4.3753851731,\n" +
                "        \"SEK\": 10.0031720138,\n" +
                "        \"IDR\": 16393.0034439007,\n" +
                "        \"INR\": 75.6751857894,\n" +
                "        \"BRL\": 5.1215334421,\n" +
                "        \"RUB\": 79.8790103317,\n" +
                "        \"HRK\": 6.911818017,\n" +
                "        \"JPY\": 108.1566068515,\n" +
                "        \"THB\": 32.6998368679,\n" +
                "        \"CHF\": 0.9580387892,\n" +
                "        \"EUR\": 0.9062896502,\n" +
                "        \"MYR\": 4.3326083016,\n" +
                "        \"BGN\": 1.7725212978,\n" +
                "        \"TRY\": 6.5683342396,\n" +
                "        \"CNY\": 7.0996012326,\n" +
                "        \"NOK\": 10.5934384629,\n" +
                "        \"NZD\": 1.6680261011,\n" +
                "        \"ZAR\": 17.8798259924,\n" +
                "        \"USD\": 1.0,\n" +
                "        \"MXN\": 23.7864781584,\n" +
                "        \"SGD\": 1.4257748777,\n" +
                "        \"AUD\": 1.6332245786,\n" +
                "        \"ILS\": 3.5838317926,\n" +
                "        \"KRW\": 1224.8685880007,\n" +
                "        \"PLN\": 4.124433569\n" +
                "    },\n" +
                "    \"base\": \"USD\",\n" +
                "    \"date\": \"2020-03-30\"\n" +
                "}"

        const val stringRub = "{\n" +
                "    \"rates\": {\n" +
                "        \"CAD\": 0.0177289153,\n" +
                "        \"HKD\": 0.0970733561,\n" +
                "        \"ISK\": 1.7506538006,\n" +
                "        \"PHP\": 0.6375874334,\n" +
                "        \"DKK\": 0.0847155329,\n" +
                "        \"HUF\": 4.0699580773,\n" +
                "        \"CZK\": 0.3099099712,\n" +
                "        \"GBP\": 0.0100863981,\n" +
                "        \"RON\": 0.054775155,\n" +
                "        \"SEK\": 0.1252290429,\n" +
                "        \"IDR\": 205.2229162057,\n" +
                "        \"INR\": 0.9473726011,\n" +
                "        \"BRL\": 0.0641161354,\n" +
                "        \"RUB\": 1.0,\n" +
                "        \"HRK\": 0.0865285885,\n" +
                "        \"JPY\": 1.3540053439,\n" +
                "        \"THB\": 0.4093670757,\n" +
                "        \"CHF\": 0.0119936237,\n" +
                "        \"EUR\": 0.0113457797,\n" +
                "        \"MYR\": 0.0542396342,\n" +
                "        \"BGN\": 0.0221900758,\n" +
                "        \"TRY\": 0.082228538,\n" +
                "        \"CNY\": 0.0888794341,\n" +
                "        \"NOK\": 0.1326185492,\n" +
                "        \"NZD\": 0.0208819075,\n" +
                "        \"ZAR\": 0.2238363485,\n" +
                "        \"USD\": 0.0125189333,\n" +
                "        \"MXN\": 0.2977813328,\n" +
                "        \"SGD\": 0.0178491806,\n" +
                "        \"AUD\": 0.0204462295,\n" +
                "        \"ILS\": 0.0448657511,\n" +
                "        \"KRW\": 15.3340481175,\n" +
                "        \"PLN\": 0.0516335086\n" +
                "    },\n" +
                "    \"base\": \"RUB\",\n" +
                "    \"date\": \"2020-03-30\"\n" +
                "}"
    }
}