package com.example.adsmanager_androidkoltin.ui.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.adsmanager_androidkoltin.databinding.ActivityMainBinding
import com.example.adsmanager_androidkoltin.koin.DIComponent

class MainActivity : AppCompatActivity() {

    private var bindingMain: ActivityMainBinding? = null
    private val binding get() = bindingMain!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        DIComponent.interstitialViewModel.preLoadInterstitialAd(isLoadedCallBack = {
           when (it) {
                false -> {
                    Log.d("varMsg", "Ad is not loaded")
                }
                true-> {
                     Log.d("varMsg", "Ad is loaded")
                }
            }

        },this)

        Handler(Looper.getMainLooper()).postDelayed({
            binding.progressCircular.visibility = View.INVISIBLE
            binding.btNext.visibility = View.VISIBLE
            binding.btNext.setOnClickListener {

                DIComponent.interstitialViewModel.goToNextActivity(FirstActivity::class.java,this)

            }
          }, 9*1000)

    }

}