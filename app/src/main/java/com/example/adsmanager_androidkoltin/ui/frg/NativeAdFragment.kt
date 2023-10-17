package com.example.adsmanager_androidkoltin.ui.frg

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.FrameLayout
import androidx.navigation.NavController
import com.example.adsmanager_androidkoltin.R
import com.example.adsmanager_androidkoltin.ads.Constants
import com.example.adsmanager_androidkoltin.ads.Constants.isAdsPurchased
import com.example.adsmanager_androidkoltin.ads.interfaces.NativeAdCallBack
import com.example.adsmanager_androidkoltin.ads.utils.enums.NativeType
import com.example.adsmanager_androidkoltin.databinding.FragmentNativeAdBinding
import com.example.adsmanager_androidkoltin.extentions.findNavControllerOrThrow
import com.example.adsmanager_androidkoltin.extentions.findNavHostFragment
import com.example.adsmanager_androidkoltin.koin.DIComponent


class NativeAdFragment : Fragment() {

    private var bindingSecond: FragmentNativeAdBinding? = null
    private val binding get() = bindingSecond!!

    private var navController: NavController? = null
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
        bindingSecond = FragmentNativeAdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWork()
       loadAds(NativeType.FIX,binding.nativeBannerAdFrame)
        binding.btnChange.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // This method will be called when an item in the Spinner is selected.
                // The "position" parameter indicates the index of the selected item.
                // You can perform actions based on the selected item here.
                val selectedItem = binding.btnChange.getItemAtPosition(position).toString()
                Log.d("select", "onItemSelected: $selectedItem")
                when (selectedItem) {

                    "SMALL" -> {
                        loadAds(NativeType.SMALL, binding.nativeAdFrame)
                    }
                    "LARGE" -> {
                        loadAds(NativeType.LARGE, binding.nativeAdFrame)
                    }
                    "LARGE_ADJUSTED" -> {
                        loadAds(NativeType.LARGE_ADJUSTED, binding.nativeAdFrame)
                    }
                    "FIX" -> {
                        loadAds(NativeType.FIX, binding.nativeAdFrame)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // This method is called when no item is selected.
                // You can add additional handling for this case if needed.
            }
        }
        binding.btn.setOnClickListener {
            DIComponent.interstitialViewModel.backToPreviousFragment(
                navController,
                R.id.secondFragment,
                requireContext()
            )
        }
    }

    private fun initWork() {
        // Find the NavHostFragment using the extension function
        val navHostFragment = requireActivity().findNavHostFragment(R.id.fragmentContainerView)
        // Assign the NavController using the extension function, or throw an exception if not found
        navController = requireActivity().findNavControllerOrThrow(R.id.fragmentContainerView)
    }
    fun loadAds(nativeAdType: NativeType, nativeBannerAdFrame: FrameLayout) {

        Log.d("selectedType", "loadAds: $nativeAdType")
        DIComponent.admobNativeAds.loadNativeAds(
            requireActivity(),
            nativeBannerAdFrame,
            getString(R.string.admob_native_home_ids),
            1,
             isAdsPurchased,
            DIComponent.internetManager.isInternetConnected,
            nativeAdType,
            object : NativeAdCallBack {
                override fun onAdFailedToLoad(adError: String) {}
                override fun onAdLoaded() {}
                override fun onAdImpression() {}
                override fun onPreloaded() {}
                override fun onAdClicked() {}
                override fun onAdClosed() {}
                override fun onAdOpened() {}
                override fun onAdSwipeGestureClicked() {}
            }
        )
    }

}