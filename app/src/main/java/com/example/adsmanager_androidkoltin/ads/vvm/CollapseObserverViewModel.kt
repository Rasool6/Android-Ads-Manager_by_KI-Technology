package com.example.adsmanager_androidkoltin.ads.vvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CollapseObserverViewModel : ViewModel() {

    private val _bannerLoadingState = MutableLiveData<String>().apply {
        value = "firstTime" // Set default value here
    }

    val bannerLoadingState: LiveData<String>
        get() = _bannerLoadingState

    fun setBannerLoadingState(isLoading: String) {
        _bannerLoadingState.value = isLoading
    }
}