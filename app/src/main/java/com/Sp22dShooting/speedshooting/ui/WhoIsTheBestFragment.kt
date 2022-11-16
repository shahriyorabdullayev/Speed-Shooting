package com.Sp22dShooting.speedshooting.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.Sp22dShooting.speedshooting.R
import com.Sp22dShooting.speedshooting.databinding.FragmentWhoIsTheBestBinding
import com.Sp22dShooting.speedshooting.utils.*
import com.Sp22dShooting.speedshooting.utils.Constants.KEY_SCORE_LEFT
import com.Sp22dShooting.speedshooting.utils.Constants.KEY_SCORE_RIGHT
import com.Sp22dShooting.speedshooting.utils.Constants.KEY_VIBRATION


class WhoIsTheBestFragment : Fragment(R.layout.fragment_who_is_the_best) {

    private val binding by viewBinding { FragmentWhoIsTheBestBinding.bind(it) }

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

            val scoreLeft = getString(KEY_SCORE_LEFT)
            val scoreRight = getString(KEY_SCORE_RIGHT)

            if (scoreLeft != "" && scoreRight == "") {
                binding.ivBestPlayer.setImageResource(R.drawable.gamer_orange)
            }

            if (scoreLeft == "" && scoreRight != "") {
                binding.ivBestPlayer.setImageResource(R.drawable.gamer_blue)
            }

            if (scoreLeft != "" && scoreRight != "") {
                if (scoreLeft.toInt() < scoreRight.toInt()) {
                    binding.ivBestPlayer.setImageResource(R.drawable.gamer_blue)
                } else {
                    binding.ivBestPlayer.setImageResource(R.drawable.gamer_orange)
                }
            }

        }
    }


}