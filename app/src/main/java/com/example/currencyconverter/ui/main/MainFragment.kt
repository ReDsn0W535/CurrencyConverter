package com.example.currencyconverter.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.currencyconverter.BR
import com.example.currencyconverter.R
import com.example.currencyconverter.ViewModelProviderFactory
import com.example.currencyconverter.databinding.MainFragmentBinding
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import javax.inject.Inject

//TODO("добавить BaseFragment")
open class MainFragment : DaggerFragment() {
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

}