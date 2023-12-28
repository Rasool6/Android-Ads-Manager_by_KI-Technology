package com.example.adsmanager_androidkoltin.ui.frg

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.adsmanager_androidkoltin.R
import com.example.adsmanager_androidkoltin.ads.Constants
import com.example.adsmanager_androidkoltin.ads.Constants.visited
import com.example.adsmanager_androidkoltin.ads.collapsballBanner.AdmobCollapseBannerAds
import com.example.adsmanager_androidkoltin.ads.collapsballBanner.BannerCallBack
import com.example.adsmanager_androidkoltin.ads.collapsballBanner.CollapsibleType
import com.example.adsmanager_androidkoltin.ads.collapsballBanner.SingleLiveEvent
import com.example.adsmanager_androidkoltin.databinding.FragmentCollapsBannerAdsBinding
import com.example.adsmanager_androidkoltin.koin.DIComponent


class CollapsBannerAdsFragment : Fragment() {

    private val binding by lazy {
        FragmentCollapsBannerAdsBinding.inflate(layoutInflater)
    }
    private val admobCollapseBannerAds by lazy { AdmobCollapseBannerAds() }
    private val adsObserver = SingleLiveEvent<Boolean>()
    private var isCollapsibleOpen = false
    private val handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // setting value if it's the first time visiting
        DIComponent.collapseObserverViewModel.setBannerLoadingState("firstTime")

        DIComponent.collapseObserverViewModel.bannerLoadingState.observe(viewLifecycleOwner) { visited ->
            when (visited) {
                "firstTime" -> {
                    loadCollapseBanner()
                }
                "no" -> {
                    loadCollapseBanner()
                }
                "stopeLoading" -> {
                    Log.d("collapseAdTag", "onViewCreated: stop loading when go to next or back to screen")
                    handler.removeCallbacksAndMessages(null)
                }
                "yes" -> {
                    handler.postDelayed({
                        loadCollapseBanner()
                    }, 4000) // 4000 milliseconds = 4 seconds
                }
            }
        }


    }


    override fun onPause() {
        super.onPause()
        DIComponent.collapseObserverViewModel.setBannerLoadingState("stopeLoading")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Remove the observer when the view is destroyed
        DIComponent.collapseObserverViewModel.bannerLoadingState.removeObservers(viewLifecycleOwner)
        // Remove any pending delayed messages from the handler
        handler.removeCallbacksAndMessages(null)
    }

    private fun loadCollapseBanner() {

        admobCollapseBannerAds.loadCollapseBannerAds(
            requireActivity(),
            binding.adsBannerPlaceHolder,
            getString(R.string.admob_banner_collapse_id),
            1,// default enable vale i kept 1
            false,//PrefManager.instance.getIsAppPurchased(this),
            CollapsibleType.BOTTOM,
            DIComponent.internetManager.isInternetConnected,
            object : BannerCallBack {
                override fun onAdFailedToLoad(adError: String) {
                    Constants.visited = "no"
                    DIComponent.collapseObserverViewModel.setBannerLoadingState("no")
                }

                override fun onAdLoaded() {
                    //  Constants.visited="yes"
                }

                override fun onAdImpression() {}
                override fun onPreloaded() {}
                override fun onAdClicked() {}
                override fun onAdSwipeGestureClicked() {}
                override fun onAdClosed() {
                    isCollapsibleOpen = false
                    Constants.visited = "yes"
                    DIComponent.collapseObserverViewModel.setBannerLoadingState("yes")

//                    if (isBackPressed){
//                        adsObserver.value = true
//                    }
                }

                override fun onAdOpened() {
                    isCollapsibleOpen = true
                }
            }
        )
    }
}