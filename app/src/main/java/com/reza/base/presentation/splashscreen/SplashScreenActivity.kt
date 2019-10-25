package com.reza.base.presentation.splashscreen

import android.os.Bundle
import com.reza.base.R
import com.reza.base.core.BaseActivity
import com.reza.base.core.ViewDataBindingOwner
import com.reza.base.databinding.ActivitySplashScreenBinding
import com.reza.base.presentation.mainpage.MainPageActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : BaseActivity(),
    SplashScreenView,
    ViewDataBindingOwner<ActivitySplashScreenBinding> {

    override fun getViewLayoutResId(): Int {
        return R.layout.activity_splash_screen
    }

    private lateinit var viewModel: SplashScreenViewModel
    override lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = SplashScreenViewModel()
        viewModel = binding.vm!!

        GlobalScope.launch(Dispatchers.Main) {
            delay(2100)
            MainPageActivity.startThisActivity(this@SplashScreenActivity)
            finish()
        }
    }
}