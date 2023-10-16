package com.example.adsmanager_androidkoltin.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adsmanager_androidkoltin.databinding.ActivityFirstBinding
import com.example.adsmanager_androidkoltin.koin.DIComponent

class FirstActivity : AppCompatActivity() {
    private var bindingFirst: ActivityFirstBinding?=null
    private val binding get() = bindingFirst!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingFirst=ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            DIComponent.interstitialViewModel.goToNextActivity(SecondActivity::class.java,this)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        DIComponent.interstitialViewModel.backToPreviousActivity(this);
    }

}