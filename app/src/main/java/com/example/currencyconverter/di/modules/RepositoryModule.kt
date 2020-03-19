package com.example.currencyconverter.di.modules

import com.example.currencyconverter.reposiroty.CurrencyRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideCurrencyRepository(retrofit: Retrofit) = CurrencyRepository(retrofit)
}