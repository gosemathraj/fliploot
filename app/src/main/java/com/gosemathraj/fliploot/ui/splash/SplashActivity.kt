package com.gosemathraj.fliploot.ui.splash

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import com.gosemathraj.fliploot.R
import com.gosemathraj.fliploot.databinding.ActivitySplashBinding
import com.gosemathraj.fliploot.ui.base.BaseActivity
import com.gosemathraj.fliploot.ui.home.HomeActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_splash

    override val currentFragment: Fragment?
        get() = null

    override val fragmentContainerId: Int
        get() = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        object : CountDownTimer(3000, 1000) {
            override fun onFinish() {
                finish()
                openActivity(HomeActivity::class.java)
            }

            override fun onTick(millisUntilFinished: Long) {}
        }.start()
    }
}