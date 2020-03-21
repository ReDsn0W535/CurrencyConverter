package com.example.currencyconverter.di.modules

import com.example.currencyconverter.di.scope.FragmentScope
import com.example.currencyconverter.ui.main.MainFragment
import dagger.android.ContributesAndroidInjector
import dagger.Module

@Module
abstract class FragmentsModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment
}
