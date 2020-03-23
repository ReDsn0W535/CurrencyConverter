package com.example.currencyconverter.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RatesConverter {
    @TypeConverter
    fun fromString(value: String?): Map<String, Double> {
        val mapType = object :
            TypeToken<Map<String?, Double?>?>() {}.type
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    fun fromStringMap(map: Map<String?, Double?>?): String {
        val gson = Gson()
        return gson.toJson(map)
    }
}