package com.example.currencyconverter.ui.main

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverter.BR
import com.example.currencyconverter.R
import com.example.currencyconverter.ViewModelProviderFactory
import com.example.currencyconverter.databinding.MainFragmentBinding
import com.example.currencyconverter.ui.base.BaseFragment
import com.example.currencyconverter.ui.recycler.CurrencyRecyclerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


open class MainFragment : BaseFragment<MainFragmentBinding>() {
    companion object {
        const val TAG = "MainFragment"
        fun newInstance() = MainFragment()
    }

    @Inject
    internal lateinit var factory: ViewModelProviderFactory
    private val mViewModel: MainViewModel by activityViewModels() { factory }
    override fun getLayoutId() = R.layout.main_fragment
    override fun getViewModel() = mViewModel
    override fun getBindingVariable() = BR.viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.apply {
            currencyFrom.setOnClickListener(Listener())
            currencyTo.setOnClickListener(Listener())
            convertButton.setOnClickListener {
                this@MainFragment.mViewModel.convert()
            }
        }
    }

    private inner class Listener : View.OnClickListener{
        override fun onClick(v: View?) {
            val dialog = BottomSheetDialog(this@MainFragment.requireContext())
            val dialogView: View = layoutInflater.inflate(R.layout.bottom_sheet, null)
            dialogView.findViewById<RecyclerView>(R.id.currencies).apply {
                adapter = CurrencyRecyclerAdapter(this@MainFragment.mViewModel.currenciesList) { value ->
                    (v as TextInputEditText).setText(value, TextView.BufferType.EDITABLE)
                    dialog.dismiss()
                }
                layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            }
            dialog.setContentView(dialogView)
            dialog.show()
        }

    }
}