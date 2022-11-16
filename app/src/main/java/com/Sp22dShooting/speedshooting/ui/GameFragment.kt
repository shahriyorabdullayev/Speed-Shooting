package com.Sp22dShooting.speedshooting.ui

import android.animation.Animator
import android.animation.ValueAnimator
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.Sp22dShooting.speedshooting.R
import com.Sp22dShooting.speedshooting.databinding.FragmentGameBinding
import com.Sp22dShooting.speedshooting.utils.*
import com.Sp22dShooting.speedshooting.utils.Constants.KEY_BACK_BUTTON
import com.Sp22dShooting.speedshooting.utils.Constants.KEY_SCORE_LEFT
import com.Sp22dShooting.speedshooting.utils.Constants.KEY_SCORE_RIGHT
import com.Sp22dShooting.speedshooting.utils.Constants.KEY_VIBRATION
import org.w3c.dom.Text


class GameFragment : Fragment(R.layout.fragment_game) {


    private val binding by viewBinding { FragmentGameBinding.bind(it) }
    private var scoreLeft: Int = 0
    private var scoreRight: Int = 0

    private var width: Int = 0
    private var height: Int = 0

    private var countDownTimer: CountDownTimer? = null

    private var startCount = 3

    private lateinit var mediaPlayer: MediaPlayer

    private lateinit var bulletShootLeft: ValueAnimator
    private lateinit var bulletShootRight: ValueAnimator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        width = displayMetrics.widthPixels
        height = displayMetrics.heightPixels

        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.shoot)

        setTimer()

    }

    private fun setTimer() {
        saveBoolean(KEY_BACK_BUTTON, false)
        binding.tvGetReady.text = startCount.toString()
        binding.tvShootRight.isEnabled = false
        binding.tvShootLeft.isEnabled = false
        binding.llTint.visibility = View.VISIBLE

        countDownTimer = object : CountDownTimer(1800, 600) {
            override fun onTick(p0: Long) {
                binding.tvGetReady.text = (startCount--).toString()
            }

            override fun onFinish() {
                startGame()
            }
        }
        countDownTimer?.start()
    }

    private fun startGame() {
        saveBoolean(KEY_BACK_BUTTON, true)
        binding.tvScoreLeft.text = scoreLeft.toString()
        binding.tvScoreRight.text = scoreRight.toString()

        saveString(KEY_SCORE_LEFT, scoreLeft.toString())
        saveString(KEY_SCORE_RIGHT, scoreRight.toString())

        binding.tvGetReady.text = resources.getString(R.string.shooting)
        binding.tvShootRight.isEnabled = true
        binding.tvShootLeft.isEnabled = true
        binding.llTint.visibility = View.INVISIBLE
        binding.gamerRight.setImageResource(R.drawable.gamer_blue)
        binding.gamerLeft.setImageResource(R.drawable.gamer_orange)

        binding.tvShootLeft.setOnClickListener {
            if (getBoolean(KEY_VIBRATION)) {
                vibrator()
                saveBoolean(KEY_BACK_BUTTON, false)
                binding.tvShootRight.isEnabled = false
                binding.tvShootLeft.isEnabled = false
                shootLeftBullet()
            } else {
                saveBoolean(KEY_BACK_BUTTON, false)
                binding.tvShootRight.isEnabled = false
                binding.tvShootLeft.isEnabled = false
                shootLeftBullet()
            }

        }

        binding.tvShootRight.setOnClickListener {
            if (getBoolean(KEY_VIBRATION)) {
                vibrator()
                saveBoolean(KEY_BACK_BUTTON, false)
                binding.tvShootLeft.isEnabled = false
                binding.tvShootRight.isEnabled = false
                shootRightBullet()
            } else {
                saveBoolean(KEY_BACK_BUTTON, false)
                binding.tvShootLeft.isEnabled = false
                binding.tvShootRight.isEnabled = false
                shootRightBullet()
            }

        }
    }

    private fun shootLeftBullet() {
        mediaPlayer.start()
        binding.bulletLeft.visibility = View.VISIBLE
        bulletShootLeft = ValueAnimator.ofFloat(0f, 1000f)

        bulletShootLeft.addUpdateListener {
            val value = it.animatedValue as Float
            binding.bulletLeft.translationX = value
        }

        bulletShootLeft.interpolator = LinearInterpolator()
        bulletShootLeft.repeatCount = 0
        bulletShootLeft.duration = 1200

        bulletShootLeft.start()

        bulletShootLeft.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                startCount = 3
                saveBoolean(KEY_BACK_BUTTON, true)
                binding.bulletLeft.visibility = View.INVISIBLE
                binding.gamerRight.setImageResource(R.drawable.gamer_right_over)
                scoreLeft++

                if (scoreLeft == 5 || scoreRight == 5) {
                    if (scoreLeft > scoreRight) {
                        showResultDialog(true)
                    } else {
                        showResultDialog(false)
                    }
                }

                setTimer()
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationRepeat(p0: Animator?) {
            }

        })
    }

    private fun shootRightBullet() {
        mediaPlayer.start()
        binding.bulletRight.visibility = View.VISIBLE

        bulletShootRight = ValueAnimator.ofFloat(0f, -1000f)


        bulletShootRight.addUpdateListener {
            val value = it.animatedValue as Float
            binding.bulletRight.translationX = value
        }

        bulletShootRight.interpolator = LinearInterpolator()
        bulletShootRight.repeatCount = 0
        bulletShootRight.duration = 1200

        bulletShootRight.start()

        bulletShootRight.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {}

            override fun onAnimationEnd(p0: Animator?) {
                startCount = 3
                saveBoolean(KEY_BACK_BUTTON, true)
                binding.bulletRight.visibility = View.INVISIBLE
                binding.gamerLeft.setImageResource(R.drawable.gamer_left_over)
                scoreRight++

                if (scoreLeft == 5 || scoreRight == 5) {
                    if (scoreLeft > scoreRight) {
                        showResultDialog(true)
                    } else {
                        showResultDialog(false)
                    }
                }

                setTimer()
            }

            override fun onAnimationCancel(p0: Animator?) {}

            override fun onAnimationRepeat(p0: Animator?) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
        saveBoolean(KEY_BACK_BUTTON, true)
    }

    private fun showResultDialog(result: Boolean) {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.result_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)

        if (result) {
            dialog.findViewById<ImageView>(R.id.iv_winner).setImageResource(R.drawable.gamer_orange)
        } else {
            dialog.findViewById<ImageView>(R.id.iv_winner).setImageResource(R.drawable.gamer_blue)
        }

        dialog.findViewById<TextView>(R.id.btn_menu).setOnClickListener {
            dialog.dismiss()
            findNavController().navigate(R.id.action_gameFragment_to_menuFragment)
        }


        dialog.show()
    }


}