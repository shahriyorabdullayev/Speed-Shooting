package com.Sp22dShooting.speedshooting.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.Sp22dShooting.speedshooting.R
import com.Sp22dShooting.speedshooting.databinding.FragmentSettingsBinding
import com.Sp22dShooting.speedshooting.service.SoundService
import com.Sp22dShooting.speedshooting.utils.*
import com.Sp22dShooting.speedshooting.utils.Constants.EN
import com.Sp22dShooting.speedshooting.utils.Constants.KEY
import com.Sp22dShooting.speedshooting.utils.Constants.KEY_LANGUAGE
import com.Sp22dShooting.speedshooting.utils.Constants.KEY_SOUND
import com.Sp22dShooting.speedshooting.utils.Constants.KEY_VIBRATION
import com.Sp22dShooting.speedshooting.utils.Constants.RU
import dev.b3nedikt.app_locale.AppLocale
import dev.b3nedikt.reword.Reword
import java.util.*


class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val binding by viewBinding { FragmentSettingsBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saveBoolean(Constants.KEY_BACK_BUTTON, true)

        setupSound(getBoolean(KEY_SOUND))
        setupVibrationIcon(getBoolean(KEY_VIBRATION))
        setupLanguageIcon(getString(KEY_LANGUAGE))

        onClickListeners()
    }

    private fun onClickListeners() {
        binding.apply {
            btnBackSettings.setOnClickListener {
                if (getBoolean(KEY_VIBRATION)) {
                    vibrator()
                    requireActivity().onBackPressed()
                } else {
                    requireActivity().onBackPressed()
                }


            }

            btnSound.setOnClickListener {
                if (getBoolean(KEY_VIBRATION)) {
                    vibrator()
                    if (getBoolean(KEY_SOUND)) {
                        saveBoolean(KEY_SOUND, false)
                        requireActivity().stopService(Intent(requireContext(), SoundService::class.java))
                        setupSound(false)
                    } else {
                        saveBoolean(KEY_SOUND, true)
                        requireActivity().startService(Intent(requireContext(), SoundService::class.java))
                        setupSound(true)
                    }
                } else {
                    if (getBoolean(KEY_SOUND)) {
                        saveBoolean(KEY_SOUND, false)
                        requireActivity().stopService(Intent(requireContext(), SoundService::class.java))
                        setupSound(false)
                    } else {
                        saveBoolean(KEY_SOUND, true)
                        requireActivity().startService(Intent(requireContext(), SoundService::class.java))
                        setupSound(true)
                    }
                }
            }

            btnVibration.setOnClickListener {
                if (getBoolean(KEY_VIBRATION)) {
                    vibrator()
                    saveBoolean(KEY_VIBRATION, false)
                    setupVibrationIcon(false)
                } else if (!getBoolean(KEY_VIBRATION)) {
                    saveBoolean(KEY_VIBRATION, true)
                    setupVibrationIcon(true)
                }
            }


            btnLang.setOnClickListener {
                Log.d("@@@", "onClickListeners: click")
                if (getBoolean(KEY_VIBRATION)) {
                    Log.d("@@@", "onClickListeners: click1")
                    vibrator()
                    if (getString(KEY_LANGUAGE) == EN) {
                        saveString(KEY_LANGUAGE, RU)
                        setupLanguageIcon(RU)
                        AppLocale.desiredLocale = Locale(RU)
                        Reword.reword(binding.root)
                    }else if (getString(KEY_LANGUAGE) == RU) {
                        saveString(KEY_LANGUAGE, EN)
                        setupLanguageIcon(EN)
                        AppLocale.desiredLocale = Locale(EN)
                        Reword.reword(binding.root)
                    }
                } else {
                    Log.d("@@@", "onClickListeners: click2")
                    if (getString(KEY_LANGUAGE) == EN) {
                        saveString(KEY_LANGUAGE, RU)
                        setupLanguageIcon(RU)
                        AppLocale.desiredLocale = Locale(RU)
                        Reword.reword(binding.root)
                    }else if (getString(KEY_LANGUAGE) == RU) {
                        Log.d("@@@", "onClickListeners: click3")
                        saveString(KEY_LANGUAGE, EN)
                        setupLanguageIcon(EN)
                        AppLocale.desiredLocale = Locale(EN)
                        Reword.reword(binding.root)
                    }
                }


            }
        }

    }

    private fun setupLanguageIcon(language: String) {
        if (language == EN) {
            binding.btnLang.setImageResource(R.drawable.ic_lang_en)
        }else if (language == RU) {
            binding.btnLang.setImageResource(R.drawable.ic_lang_ru)
        }
    }

    private fun setupSound(isOn: Boolean) {
        if (isOn) {
            binding.btnSound.setImageResource(R.drawable.ic_sound_on)
        } else {
            binding.btnSound.setImageResource(R.drawable.ic_sound_off)
        }
    }

    private fun setupVibrationIcon(isOn: Boolean) {
        if (isOn) {
            binding.btnVibration.setImageResource(R.drawable.ic_vibration_on)
        } else {
            binding.btnVibration.setImageResource(R.drawable.ic_vibration_off)
        }
    }

}