package com.example.currencyconverter.di.components

import android.app.Application
import android.content.Context
import com.example.currencyconverter.CurrencyConverterApp
import com.example.currencyconverter.di.modules.AppModule
import com.example.currencyconverter.di.modules.SingleActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    AndroidInjectionModule::class,
    SingleActivityModule::class
])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app : CurrencyConverterApp)

}