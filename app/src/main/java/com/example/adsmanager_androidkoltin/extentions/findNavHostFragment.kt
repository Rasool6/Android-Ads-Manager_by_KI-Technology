package com.example.adsmanager_androidkoltin.extentions

import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController

fun FragmentActivity.findNavHostFragment(containerViewId: Int): NavHostFragment? {
    return supportFragmentManager.findFragmentById(containerViewId) as NavHostFragment?
}

fun FragmentActivity.findNavControllerOrThrow(containerViewId: Int): NavController {
    return findNavHostFragment(containerViewId)?.navController
        ?: throw IllegalStateException("NavHostFragment not found for containerViewId: $containerViewId")
}