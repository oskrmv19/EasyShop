package com.oskr19.easyshop

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.oskr19.easyshop.core.data.preferences.EasyShopPrefs
import com.oskr19.easyshop.core.domain.Constants
import com.oskr19.easyshop.databinding.ActivityMainBinding
import com.oskr19.easyshop.screens.search.data.local.provider.CustomSuggestionProvider
import com.oskr19.easyshop.screens.search.presentation.SearchFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/** Created by oscar.vergara on 23/07/2021 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    internal lateinit var pref: EasyShopPrefs

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val callback: OnBackPressedCallback = object : OnBackPressedCallback(
        true
    ) {
        override fun handleOnBackPressed() {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                isEnabled = false
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        onBackPressedDispatcher.addCallback(this, callback)

        setUpDrawer()

        handleSearchIntent(intent)

        //Set default Colombia as siteID
        pref.saveSiteID(Constants.DEFAULT_SITE_ID)
    }

    private fun setUpDrawer() {
        navController = findNavController(R.id.nav_host_fragment)
        binding.navView.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout)
    }

    private fun handleSearchIntent(intent: Intent) {
        if (listOf(Intent.ACTION_SEARCH, Intent.ACTION_VIEW).contains(intent.action)) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                if (Intent.ACTION_SEARCH == intent.action) {
                    SearchRecentSuggestions(
                        this,
                        CustomSuggestionProvider.AUTHORITY,
                        CustomSuggestionProvider.MODE
                    )
                        .saveRecentQuery(query, null)
                }

                val action = SearchFragmentDirections.searchToResult(query, "", "")
                navController.navigate(action)
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleSearchIntent(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, binding.drawerLayout)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        navController.navigateUp()
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }
}
