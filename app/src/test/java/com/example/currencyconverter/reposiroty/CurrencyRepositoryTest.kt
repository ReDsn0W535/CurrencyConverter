package com.example.currencyconverter.reposiroty

import com.example.currencyconverter.data.api.ExchangeRatesApi
import com.example.currencyconverter.data.database.CurrencyDao
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import javax.inject.Inject

@RunWith(MockitoJUnitRunner::class)
class CurrencyRepositoryTest {

    lateinit var repository: CurrencyRepository
    @Inject
    lateinit var gson: Gson
    @Inject
    lateinit var currencyDao: CurrencyDao

    @Before
    fun setUp() = runBlocking {
        val retrofit = Mockito.mock(Retrofit::class.java)
        val api = Mockito.mock(ExchangeRatesApi::class.java)
        Mockito.`when`(retrofit.create(ExchangeRatesApi::class.java)).thenReturn(api)
        Mockito.`when`(api.getLatestExchangeRates("USD"))
            .thenReturn(JsonParser().parse(stringUsd).asJsonObject)
        Mockito.`when`(api.getLatestExchangeRates(null))
            .thenReturn(JsonParser().parse(stringUsd).asJsonObject)
        Mockito.`when`(api.getLatestExchangeRates("RUB"))
            .thenReturn(JsonParser().parse(stringRub).asJsonObject)

        repository = CurrencyRepository(retrofit = retrofit, gson = gson, currencyDao = currencyDao)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getList() {
    }

    @Test
    fun getTable() {
    }

    @Test
    fun convert() {
    }


    companion object {
        val stringUsd = "{\n" +
                "    \"rates\": {\n" +
                "        \"CAD\": 1.4352082756,\n" +
                "        \"HKD\": 7.7529324836,\n" +
                "        \"ISK\": 140.2974046366,\n" +
                "        \"PHP\": 51.0298328253,\n" +
                "        \"DKK\": 6.897755611,\n" +
                "        \"HUF\": 327.8562852129,\n" +
                "        \"CZK\": 25.3495889905,\n" +
                "        \"GBP\": 0.8451371571,\n" +
                "        \"RON\": 4.4745543549,\n" +
                "        \"SEK\": 10.1263507897,\n" +
                "        \"IDR\": 16114.9995381916,\n" +
                "        \"INR\": 76.3821926665,\n" +
                "        \"BRL\": 5.0740740741,\n" +
                "        \"RUB\": 78.3240971645,\n" +
                "        \"HRK\": 7.0271543364,\n" +
                "        \"JPY\": 111.4528493581,\n" +
                "        \"THB\": 32.78008682,\n" +
                "        \"CHF\": 0.9792186201,\n" +
                "        \"EUR\": 0.9236168837,\n" +
                "        \"MYR\": 4.3865336658,\n" +
                "        \"BGN\": 1.8064099012,\n" +
                "        \"TRY\": 6.4350235522,\n" +
                "        \"CNY\": 7.0974415812,\n" +
                "        \"NOK\": 10.8751269973,\n" +
                "        \"NZD\": 1.7067516394,\n" +
                "        \"ZAR\": 17.4081463009,\n" +
                "        \"USD\": 1.0,\n" +
                "        \"MXN\": 24.5408700471,\n" +
                "        \"SGD\": 1.4455527847,\n" +
                "        \"AUD\": 1.6618638589,\n" +
                "        \"ILS\": 3.6400665004,\n" +
                "        \"KRW\": 1232.8622887226,\n" +
                "        \"PLN\": 4.2317354761\n" +
                "    },\n" +
                "    \"base\": \"USD\",\n" +
                "    \"date\": \"2020-03-25\"\n" +
                "}"

        val stringRub = "{\n" +
                "    \"rates\": {\n" +
                "        \"CAD\": 0.0183239683,\n" +
                "        \"HKD\": 0.0989852774,\n" +
                "        \"ISK\": 1.7912419002,\n" +
                "        \"PHP\": 0.6515214943,\n" +
                "        \"DKK\": 0.0880668384,\n" +
                "        \"HUF\": 4.1858929382,\n" +
                "        \"CZK\": 0.3236499354,\n" +
                "        \"GBP\": 0.0107902572,\n" +
                "        \"RON\": 0.0571287064,\n" +
                "        \"SEK\": 0.1292878074,\n" +
                "        \"IDR\": 205.7476577655,\n" +
                "        \"INR\": 0.9752068065,\n" +
                "        \"BRL\": 0.0647830522,\n" +
                "        \"RUB\": 1.0,\n" +
                "        \"HRK\": 0.0897189319,\n" +
                "        \"JPY\": 1.4229701126,\n" +
                "        \"THB\": 0.4185185404,\n" +
                "        \"CHF\": 0.0125021373,\n" +
                "        \"EUR\": 0.0117922442,\n" +
                "        \"MYR\": 0.0560049056,\n" +
                "        \"BGN\": 0.0230632713,\n" +
                "        \"TRY\": 0.0821589241,\n" +
                "        \"CNY\": 0.0906163216,\n" +
                "        \"NOK\": 0.1388477798,\n" +
                "        \"NZD\": 0.0217908881,\n" +
                "        \"ZAR\": 0.222257861,\n" +
                "        \"USD\": 0.0127674628,\n" +
                "        \"MXN\": 0.3133246464,\n" +
                "        \"SGD\": 0.0184560415,\n" +
                "        \"AUD\": 0.0212177851,\n" +
                "        \"ILS\": 0.0464744138,\n" +
                "        \"KRW\": 15.7405234577,\n" +
                "        \"PLN\": 0.0540285254\n" +
                "    },\n" +
                "    \"base\": \"RUB\",\n" +
                "    \"date\": \"2020-03-25\"\n" +
                "}"
    }
}