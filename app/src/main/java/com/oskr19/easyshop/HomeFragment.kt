package com.oskr19.easyshop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.oskr19.easyshop.core.data.preferences.EasyShopPrefs
import com.oskr19.easyshop.databinding.FragmentHomeBinding
import com.oskr19.easyshop.screens.search.presentation.SearchViewModel
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
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_home,container, false)
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