package com.example.currencyconverter.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.reposiroty.CurrencyRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    val from = MutableLiveData<String>()
    val fromAmount = MutableLiveData<String>().also { it.value = "1" }
    val to = MutableLiveData<String>()
    val toAmount = MutableLiveData<String>()

    fun init() {
        viewModelScope.launch {
            currencyRepository.getCurrenciesTable().collect {
                Timber.i(it.toString())
            }
        }
    }
    fun currenciesList() = currencyRepository.getList()

    fun convert(){

    }
}