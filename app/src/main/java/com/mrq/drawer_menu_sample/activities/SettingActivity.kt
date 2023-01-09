package com.mrq.drawer_menu_sample.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mrq.drawer_menu_sample.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {}
}