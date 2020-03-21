package com.example.currencyconverter

import com.example.currencyconverter.di.components.AppComponent
import com.example.currencyconverter.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class CurrencyConverterApp : DaggerApplication() {

    private val appComponent: AndroidInjector<CurrencyConverterApp> by lazy {
        DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = appComponent

}