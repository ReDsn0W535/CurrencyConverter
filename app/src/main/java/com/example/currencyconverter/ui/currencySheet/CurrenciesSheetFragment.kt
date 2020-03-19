package com.example.currencyconverter.ui.currencySheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.currencyconverter.R
import com.example.currencyconverter.ViewModelProviderFactory
import com.example.currencyconverter.ui.main.MainFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class CurrenciesSheetFragment : BottomSheetDialogFragment() {
    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    internal lateinit var factory: ViewModelProviderFactory
    private val viewModel: CurrenciesSheetViewModel by activityViewModels() { factory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.currencies_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}