package com.sports2020.demo

import android.view.KeyEvent
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

    // 设置返回按钮的监听事件
    private var exitTime: Long = 0

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        // 监听返回键，点击两次退出程序
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                showMessageShort("再按一次退出程序")
                exitTime = System.currentTimeMillis()
            } else {
                this.finish()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}