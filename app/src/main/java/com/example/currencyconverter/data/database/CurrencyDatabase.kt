package com.example.currencyconverter.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.currencyconverter.data.database.converters.RatesConverter
import com.example.currencyconverter.data.model.Currency

@Database(entities = [Currency::class], version = 1)
@TypeConverters(RatesConverter::class)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}
