package com.example.adsmanager_androidkoltin.ui.frg

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.adsmanager_androidkoltin.R
import com.example.adsmanager_androidkoltin.databinding.FragmentFirstBinding
import com.example.adsmanager_androidkoltin.extentions.findNavControllerOrThrow
import com.example.adsmanager_androidkoltin.extentions.findNavHostFragment
import com.example.adsmanager_androidkoltin.koin.DIComponent


class FirstFragment : Fragment() {
     private var bindingFirst:FragmentFirstBinding?=null
    private val binding get() = bindingFirst!!
    private var navController :NavController?=null

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
        bindingFirst  =FragmentFirstBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initWork()

        binding.btn.setOnClickListener {
            DIComponent.interstitialViewModel.goToNextFragment(navController,R.id.secondFragment,requireContext())
        }
    }


    private fun initWork() {
        // Find the NavHostFragment using the extension function
        val navHostFragment = requireActivity().findNavHostFragment(R.id.fragmentContainerView)
        // Assign the NavController using the extension function, or throw an exception if not found
        navController = requireActivity().findNavControllerOrThrow(R.id.fragmentContainerView)
     }


}