package com.easy.lib.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import java.net.NetworkInterface
import java.net.SocketException

/**
 * Created by Administrator on 2015/7/29.
 */
class NetworkUtils(pContext: Context?) {
    private var mContext: Context? = null
    val connTypeName: String
        get() {
            val connectivityManager =
                mContext!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            @SuppressLint("MissingPermission") val networkInfo =
                connectivityManager.activeNetworkInfo
            return if (networkInfo == null) {
                NET_TYPE_NO_NETWORK
            } else {
                networkInfo.typeName
            }
        }

    companion object {
        const val NET_TYPE_WIFI = "WIFI"
        const val NET_TYPE_MOBILE = "MOBILE"
        const val NET_TYPE_NO_NETWORK = "no_network"
        const val IP_DEFAULT = "0.0.0.0"
        fun isConnectInternet(pContext: Context): Boolean {
            val conManager =
                pContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            @SuppressLint("MissingPermission") val networkInfo =
                conManager.activeNetworkInfo
            return networkInfo?.isAvailable ?: false
        }

        fun isConnectWifi(pContext: Context): Boolean {
            val mConnectivity =
                pContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            @SuppressLint("MissingPermission") val info =
                mConnectivity.activeNetworkInfo
            //判断网络连接类型，只有在3G或wifi里进行一些数据更新。
            var netType = -1
            if (info != null) {
                netType = info.type
            }
            return if (netType == ConnectivityManager.TYPE_WIFI) {
                info!!.isConnected
            } else {
                false
            }
        }

        fun getNetTypeName(pNetType: Int): String {
            return when (pNetType) {
                0 -> "unknown"
                1 -> "GPRS"
                2 -> "EDGE"
                3 -> "UMTS"
                4 -> "CDMA: Either IS95A or IS95B"
                5 -> "EVDO revision 0"
                6 -> "EVDO revision A"
                7 -> "1xRTT"
                8 -> "HSDPA"
                9 -> "HSUPA"
                10 -> "HSPA"
                11 -> "iDen"
                12 -> "EVDO revision B"
                13 -> "LTE"
                14 -> "eHRPD"
                15 -> "HSPA+"
                else -> "unknown"
            }
        }

        val iPAddress: String
            get() = try {
                val networkInterfaceEnumeration =
                    NetworkInterface.getNetworkInterfaces()
                while (networkInterfaceEnumeration.hasMoreElements()) {
                    val networkInterface =
                        networkInterfaceEnumeration.nextElement()
                    val inetAddressEnumeration =
                        networkInterface.inetAddresses
                    while (inetAddressEnumeration.hasMoreElements()) {
                        val inetAddress = inetAddressEnumeration.nextElement()
                        if (!inetAddress.isLoopbackAddress) {
                             inetAddress.hostAddress
                        }
                    }
                }
                IP_DEFAULT
            } catch (e: SocketException) {
                IP_DEFAULT
            }

        /**
         * @return 0: 无网络， 1:WIFI， 2:其他（流量）
         */
        @SuppressLint("MissingPermission")
        fun getNetType(context: Context): Int {
            if (!isConnectWifi(context)) {
                return 0
            }
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var type = 0
            if (connectivityManager != null) {
                type = connectivityManager.activeNetworkInfo.type
            }
            return if (type == ConnectivityManager.TYPE_WIFI) {
                1
            } else {
                2
            }
        }
    }

    init {
        mContext = pContext
    }
}