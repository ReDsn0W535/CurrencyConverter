package com.example.currencyconverter.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currencyconverter.data.model.Currency
import com.example.currencyconverter.reposiroty.CurrencyRepository
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    val from = MutableLiveData<String>()
    val fromAmount = MutableLiveData<String>("1")
    val to = MutableLiveData<String>()
    val toAmount = MutableLiveData<String>()

    val currenciesList by lazy { currencyRepository.getList() }

    fun convert(){
        if (!(from.value.isNullOrEmpty() or to.value.isNullOrEmpty())) {
            toAmount.value = (currencyRepository.convert(from.value!!, to.value!!) * fromAmount.value?.toDouble()!!).toString()
        }
    }
}