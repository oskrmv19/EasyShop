package com.oskr19.easyshop.screens.search.data.local.provider

import android.content.SearchRecentSuggestionsProvider

class CustomSuggestionProvider : SearchRecentSuggestionsProvider() {
    init {
        setupSuggestions(AUTHORITY, MODE)
    }

    companion object {
        const val AUTHORITY = "com.oskr19.easyshop.CustomSuggestionProvider"
        const val MODE: Int = DATABASE_MODE_QUERIES
    }
}
