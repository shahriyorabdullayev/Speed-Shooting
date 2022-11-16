package com.Sp22dShooting.speedshooting.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.Sp22dShooting.speedshooting.R
import com.Sp22dShooting.speedshooting.databinding.FragmentMenuBinding
import com.Sp22dShooting.speedshooting.utils.*
import com.Sp22dShooting.speedshooting.utils.Constants.EN
import com.Sp22dShooting.speedshooting.utils.Constants.KEY
import com.Sp22dShooting.speedshooting.utils.Constants.KEY_BACK_BUTTON
import com.Sp22dShooting.speedshooting.utils.Constants.KEY_LANGUAGE
import com.Sp22dShooting.speedshooting.utils.Constants.KEY_SCORE_LEFT
import com.Sp22dShooting.speedshooting.utils.Constants.KEY_SCORE_RIGHT
import com.Sp22dShooting.speedshooting.utils.Constants.KEY_SOUND
import com.Sp22dShooting.speedshooting.utils.Constants.KEY_VIBRATION


class MenuFragment : Fragment(R.layout.fragment_menu) {


    private val binding by viewBinding { FragmentMenuBinding.bind(it) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (getString(KEY_LANGUAGE) == "") {
            saveString(KEY_LANGUAGE, EN)
        }

//        if (getString(KEY) == "") {
//            saveBoolean(KEY_SOUND, false)
//            saveBoolean(KEY_VIBRATION, false)
//            saveString(KEY, "key")
//        }

        saveBoolean(KEY_BACK_BUTTON, true)

        clickListeners()

    }

    private fun clickListeners() {
        binding.apply {

            tvStartGame.setOnClickListener {
                navigateFragment(R.id.action_menuFragment_to_gameFragment,
                    getBoolean(KEY_VIBRATION))
            }

            tvAccount.setOnClickListener {
                navigateFragment(R.id.action_menuFragment_to_accountFragment,
                    getBoolean(KEY_VIBRATION))
            }

            tvRules.setOnClickListener {
                navigateFragment(R.id.action_menuFragment_to_rulesFragment,
                    getBoolean(KEY_VIBRATION))
            }

            tvSettings.setOnClickListener {
                navigateFragment(R.id.action_menuFragment_to_settingsFragment,
                    getBoolean(KEY_VIBRATION))
            }

            tvWhoIsTheBest.setOnClickListener {
                navigateFragment(R.id.action_menuFragment_to_whoIsTheBestFragment,
                    getBoolean(KEY_VIBRATION))
            }


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        saveBoolean(KEY_BACK_BUTTON, true)
    }

}