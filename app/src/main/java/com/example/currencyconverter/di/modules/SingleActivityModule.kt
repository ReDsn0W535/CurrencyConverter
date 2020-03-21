package com.example.currencyconverter.di.modules

import com.example.currencyconverter.MainActivity
import com.example.currencyconverter.di.scope.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SingleActivityModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [FragmentsModule::class])
    abstract fun contributeIntroActivity(): MainActivity
}
