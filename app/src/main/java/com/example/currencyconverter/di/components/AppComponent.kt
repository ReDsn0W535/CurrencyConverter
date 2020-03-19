package com.example.currencyconverter.di.components

import android.app.Application
import android.content.Context
import com.example.currencyconverter.di.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        @BindsInstance
        fun app(app: Application): Builder
        fun build(): AppComponent
    }
    fun inject(app : Application)
}