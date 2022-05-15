package ir.ariyana.loginsystem.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class CheckConnection(private val context: Context) {

    val isConnected: Boolean
    get() {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {

            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val currentNetwork = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

            return when {

                currentNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                currentNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                currentNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true

                else -> false
            }
        }

        else {

            return when(connectivityManager.activeNetworkInfo?.type) {

                ConnectivityManager.TYPE_WIFI -> true

                ConnectivityManager.TYPE_ETHERNET -> true

                ConnectivityManager.TYPE_MOBILE -> true

                else -> false
            }
        }
    }
}