package com.example.currencyconverter.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverter.BR
import com.example.currencyconverter.R
import com.example.currencyconverter.ViewModelProviderFactory
import com.example.currencyconverter.databinding.MainFragmentBinding
import com.example.currencyconverter.ui.recycler.CurrencyRecyclerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


//TODO("добавить BaseFragment")
open class MainFragment : Fragment() {
    companion object {
        const val TAG = "MainFragment"
        fun newInstance() = MainFragment()
    }

    @Inject
    internal lateinit var factory: ViewModelProviderFactory
    private lateinit var dataBinding: MainFragmentBinding
    private var mRootView: View? = null
    private val viewModel: MainViewModel by activityViewModels() { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        viewModel.init()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        dataBinding.let { mRootView = it.root }
        return mRootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dataBinding.apply {
            setVariable(BR.viewModel, viewModel)
            lifecycleOwner = this@MainFragment
            executePendingBindings()
        }
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.apply {
            currencyFrom.setOnClickListener {
                val dialog = BottomSheetDialog(this@MainFragment.requireContext())
                val dialogView: View = layoutInflater.inflate(R.layout.bottom_sheet, null)
                dialogView.findViewById<RecyclerView>(R.id.currencies).apply {
                    adapter = CurrencyRecyclerAdapter(this@MainFragment.viewModel.currenciesList()) { value ->
                        (it as TextInputEditText).setText(value, TextView.BufferType.EDITABLE)
                        dialog.dismiss()
                    }
                    layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                }
                dialog.setContentView(dialogView)
                dialog.show()
            }
            currencyTo.setOnClickListener {
                val dialog = BottomSheetDialog(this@MainFragment.requireContext())
                val dialogView: View = layoutInflater.inflate(R.layout.bottom_sheet, null)
                dialogView.findViewById<RecyclerView>(R.id.currencies).apply {
                    adapter = CurrencyRecyclerAdapter(this@MainFragment.viewModel.currenciesList()) { value ->
                        (it as TextInputEditText).setText(value, TextView.BufferType.EDITABLE)
                        dialog.dismiss()
                    }
                    layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                }
                dialog.setContentView(dialogView)
                dialog.show()
            }
        }
    }
}