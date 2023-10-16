package com.example.adsmanager_androidkoltin.extentions

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class InternetManager(private val connectivityManager: ConnectivityManager) {

    val isInternetConnected: Boolean
        get() {
            try {
                val network = connectivityManager.activeNetwork ?: return false
                val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
                return when {
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } catch (ex: Exception) {
              //  ex.recordException("Internet Manager")
                return false
            }
        }
}