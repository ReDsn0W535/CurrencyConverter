package com.example.currencyconverter

import android.app.Application
import com.example.currencyconverter.di.components.AppComponent
import com.example.currencyconverter.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

class CurrencyConverterApp() : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    lateinit var appComponent: AppComponent

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .context(this)
            .app(this)
            .build().also {
                it.inject(this)
            }
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }
}