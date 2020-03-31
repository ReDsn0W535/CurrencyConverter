package com.example.currencyconverter.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.reposiroty.CurrencyRepository
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    val from = MutableLiveData<String>()
    val fromAmount = MutableLiveData("1")
    val to = MutableLiveData<String>()
    val toAmount = MutableLiveData<String>()

    fun currenciesList() = currencyRepository.getList()

    fun convert(){
        if (!(from.value.isNullOrEmpty() or to.value.isNullOrEmpty())) {
            toAmount.value = (currencyRepository.convert(from.value!!, to.value!!) * fromAmount.value?.toDouble()!!).toString()
        }
    }
}