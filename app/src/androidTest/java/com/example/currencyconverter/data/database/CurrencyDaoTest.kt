package com.example.currencyconverter.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.currencyconverter.R
import com.example.currencyconverter.data.model.Currency
import com.google.gson.Gson
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.IllegalStateException


class CurrencyDaoTest {
    private lateinit var db: CurrencyDatabase
    private lateinit var dao: CurrencyDao

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                CurrencyDatabase::class.java
        )
                .build()
        dao = db.currencyDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insert() {
        val currency = Gson().fromJson<Currency>(
                ApplicationProvider.getApplicationContext<Context>().getString(R.string.test_usd),
                Currency::class.java
        )
        dao.insert(currency)
        assertEquals(1, dao.getAll().size)
        assertEquals(currency, dao.getById(currency.base))
    }

    @Test
    fun delete() {
        val currencyUsd = Gson().fromJson<Currency>(
                ApplicationProvider.getApplicationContext<Context>().getString(R.string.test_usd),
                Currency::class.java
        )
        val currencyRub = Gson().fromJson<Currency>(
                ApplicationProvider.getApplicationContext<Context>().getString(R.string.test_rub),
                Currency::class.java
        )
        dao.insert(currencyUsd)
        dao.insert(currencyRub)
        assertEquals(2, dao.getAll().size)
        dao.delete(currencyRub)
        assertEquals(1, dao.getAll().size)
        assertEquals(currencyUsd, dao.getById(currencyUsd.base))

    }

    @Test
    fun update() {
    }

    @Test
    fun getAll() {
        val currencyUsd = Gson().fromJson<Currency>(
                ApplicationProvider.getApplicationContext<Context>().getString(R.string.test_usd),
                Currency::class.java
        )
        val currencyRub = Gson().fromJson<Currency>(
                ApplicationProvider.getApplicationContext<Context>().getString(R.string.test_rub),
                Currency::class.java
        )
        dao.insert(currencyUsd)
        dao.insert(currencyRub)
        val list = dao.getAll()
        assertEquals(2, list.size)
        list.forEach {
            if (currencyRub.base != it.base && currencyUsd.base != it.base)
                throw IllegalStateException()
        }
    }

    @Test
    fun getById() {
        val currencyUsd = Gson().fromJson<Currency>(
                ApplicationProvider.getApplicationContext<Context>().getString(R.string.test_usd),
                Currency::class.java
        )
        val currencyRub = Gson().fromJson<Currency>(
                ApplicationProvider.getApplicationContext<Context>().getString(R.string.test_rub),
                Currency::class.java
        )
        dao.insert(currencyUsd)
        dao.insert(currencyRub)
        assertEquals(currencyRub, dao.getById(currencyRub.base))
        assertEquals(currencyUsd, dao.getById(currencyUsd.base))
    }
}