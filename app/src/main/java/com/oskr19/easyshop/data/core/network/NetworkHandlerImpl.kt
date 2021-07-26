package com.oskr19.easyshop.data.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.oskr19.easyshop.domain.core.network.INetworkHandler

/**
 * Created by oscar.vergara on 24/07/2021
 */
class NetworkHandlerImpl(private val context: Context) : INetworkHandler {
    override fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    ConnectivityManager.TYPE_BLUETOOTH -> true
                    else -> false
                }
            }
        }
        return false
    }
}