package com.sports2020.demo

import androidx.lifecycle.ViewModelProvider
import com.sports2020.demo.databinding.ActivityMainBinding
import com.sports2020.demo.viewmodel.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<SplashViewModel, ActivityMainBinding>() {

    @set:Inject
    var viewModelFactory: ViewModelFactory? = null

    override fun getContentView(): Int {
        return R.layout.activity_main
    }

    override fun initViewModel(): SplashViewModel? {
        return viewModelFactory?.let { ViewModelProvider(this, it).get(SplashViewModel::class.java) }
    }

    override fun initView() {

    }

    override fun initData() {

    }
}