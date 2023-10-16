package com.example.adsmanager_androidkoltin.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.adsmanager_androidkoltin.R
import com.example.adsmanager_androidkoltin.databinding.ActivitySecondBinding
import com.example.adsmanager_androidkoltin.extentions.findNavControllerOrThrow
import com.example.adsmanager_androidkoltin.extentions.findNavHostFragment
import com.example.adsmanager_androidkoltin.koin.DIComponent

class SecondActivity : AppCompatActivity() {
    private var bindSeActivity:ActivitySecondBinding?=null
    private val binding get() = bindSeActivity!!
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindSeActivity=ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Find the NavHostFragment using the extension function
        val navHostFragment = findNavHostFragment(R.id.fragmentContainerView)
        // Assign the NavController using the extension function, or throw an exception if not found
        navController = findNavControllerOrThrow(R.id.fragmentContainerView)

        // Now you can use navController as needed

    }

    override fun onBackPressed() {
        super.onBackPressed()
        DIComponent.interstitialViewModel.backToPreviousActivity(this);
    }

}