package com.Sp22dShooting.speedshooting

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.ViewPumpAppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.Sp22dShooting.speedshooting.service.SoundService
import com.Sp22dShooting.speedshooting.utils.Constants.KEY_BACK_BUTTON
import com.Sp22dShooting.speedshooting.utils.Constants.KEY_SCORE_LEFT
import com.Sp22dShooting.speedshooting.utils.Constants.KEY_SCORE_RIGHT
import com.Sp22dShooting.speedshooting.utils.Constants.KEY_SOUND
import com.Sp22dShooting.speedshooting.utils.SharedPref
import dev.b3nedikt.app_locale.AppLocale

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (SharedPref(this).getBoolean(KEY_SOUND)) {
            startService(Intent(this, SoundService::class.java))
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, SoundService::class.java))
        SharedPref(this).saveString(KEY_SCORE_LEFT, "")
        SharedPref(this).saveString(KEY_SCORE_RIGHT, "")
    }

    private val appCompatDelegate: AppCompatDelegate by lazy {
        ViewPumpAppCompatDelegate(
            baseDelegate = super.getDelegate(),
            baseContext = this,
            wrapContext = AppLocale::wrap
        )
    }

    override fun getDelegate(): AppCompatDelegate {
        return appCompatDelegate
    }

    override fun onBackPressed() {
        if (SharedPref(this).getBoolean(KEY_BACK_BUTTON)) {
            super.onBackPressed()
        }

    }
}