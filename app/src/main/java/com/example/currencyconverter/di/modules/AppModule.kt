package com.example.currencyconverter.di.modules

import android.content.Context
import androidx.room.Room
import com.example.currencyconverter.CurrencyConverterApp
import com.example.currencyconverter.data.database.CurrencyDatabase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule(private val app: CurrencyConverterApp) {

    @Singleton
    @Provides
    fun provideDatabase(context: Context) = Room
            .databaseBuilder(context, CurrencyDatabase::class.java, "database").allowMainThreadQueries().build()

    @Singleton
    @Provides
    fun provideDao(currencyDatabase: CurrencyDatabase) = currencyDatabase.currencyDao()

    @Singleton
    @Provides
    fun getOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        var okHttpClient = OkHttpClient()
        okHttpClient = okHttpClient.newBuilder()
                .addInterceptor(interceptor)
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build()

        return okHttpClient
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder().client(client)
            .baseUrl("https://api.exchangeratesapi.io")
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .setPrettyPrinting()
            .create()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }
    @Provides
    @Singleton
    fun bindContext() : Context = app
}