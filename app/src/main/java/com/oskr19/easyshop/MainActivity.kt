package com.oskr19.easyshop

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.oskr19.easyshop.screens.common.HomeFragmentDirections
import com.oskr19.easyshop.screens.search.data.local.provider.CustomSuggestionProvider
import com.oskr19.easyshop.screens.search.presentation.SearchFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * Created by oscar.vergara on 23/07/2021
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.nav_host_fragment)
        handleSearchIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleSearchIntent(intent)
    }

    private fun handleSearchIntent(intent: Intent) {
        if (listOf(Intent.ACTION_SEARCH, Intent.ACTION_VIEW).contains(intent.action)) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                if(Intent.ACTION_SEARCH == intent.action) {
                    SearchRecentSuggestions(
                        this,
                        CustomSuggestionProvider.AUTHORITY,
                        CustomSuggestionProvider.MODE
                    )
                        .saveRecentQuery(query, null)
                }

                val action = SearchFragmentDirections.searchToResult(query,"","")
                navController.navigate(action)
            }
        }
    }
}