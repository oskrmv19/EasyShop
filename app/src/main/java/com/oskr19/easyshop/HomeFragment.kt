package com.oskr19.easyshop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.oskr19.easyshop.core.data.preferences.EasyShopPrefs
import com.oskr19.easyshop.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    @Inject
    internal lateinit var pref: EasyShopPrefs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container,false)
        binding.searchToolbar.toolbarContainer.setOnClickListener {
            val action = HomeFragmentDirections.homeToSearch("")
            Navigation.findNavController(it).navigate(action)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref.saveSiteID("MCO")
    }
}