package com.example.currencyconverter

import android.os.Bundle
import com.example.currencyconverter.ui.main.MainFragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.apply {
                findFragmentByTag(MainFragment.TAG)
                    ?: beginTransaction()
                        .add(R.id.container, MainFragment.newInstance(), MainFragment.TAG)
                        .commit()

            }
        }
    }
}