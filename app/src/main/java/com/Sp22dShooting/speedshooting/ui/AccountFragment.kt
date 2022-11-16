package com.Sp22dShooting.speedshooting.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.Sp22dShooting.speedshooting.R
import com.Sp22dShooting.speedshooting.databinding.FragmentAccountBinding
import com.Sp22dShooting.speedshooting.utils.*
import com.Sp22dShooting.speedshooting.utils.Constants.KEY_SCORE_LEFT
import com.Sp22dShooting.speedshooting.utils.Constants.KEY_SCORE_RIGHT
import com.Sp22dShooting.speedshooting.utils.Constants.KEY_VIBRATION

class AccountFragment : Fragment(R.layout.fragment_account) {

    private val binding by viewBinding { FragmentAccountBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saveBoolean(Constants.KEY_BACK_BUTTON, true)
        binding.apply {
            btnBack.setOnClickListener {
                if (getBoolean(KEY_VIBRATION)) {
                    vibrator()
                    requireActivity().onBackPressed()
                } else {
                    requireActivity().onBackPressed()
                }

            }

            if (getString(KEY_SCORE_LEFT) != "" && getString(KEY_SCORE_RIGHT) != "") {
                tvScoreLeftAccount.text = getString(KEY_SCORE_LEFT)
                tvScoreRightAccount.text = getString(KEY_SCORE_RIGHT)
            }

        }



    }

}