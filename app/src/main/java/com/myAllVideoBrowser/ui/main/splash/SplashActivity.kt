package com.myAllVideoBrowser.ui.main.splash

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.myAllVideoBrowser.R
import com.myAllVideoBrowser.databinding.ActivitySplashBinding
import com.myAllVideoBrowser.ui.main.base.BaseActivity
import com.myAllVideoBrowser.ui.main.home.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

//@OpenForTesting
@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var splashViewModel: SplashViewModel

    private lateinit var dataBinding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        splashViewModel = ViewModelProvider(this, viewModelFactory)[SplashViewModel::class.java]

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        dataBinding.viewModel = splashViewModel

        lifecycleScope.launch {
            delay(3000)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }
}