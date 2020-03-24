package com.example.currencyconverter.data.database

import androidx.room.*
import com.example.currencyconverter.data.model.Currency

@Dao
interface CurrencyDao {
    @Query("SELECT * FROM currency")
    fun getAll(): List<Currency>

    @Query("SELECT * FROM currency WHERE base = :base")
    fun getById(base: String): Currency?

    @Insert
    fun insert(currency: Currency)

    @Update
    fun update(currency: Currency)

    @Delete
    fun delete(currency: Currency)
}
