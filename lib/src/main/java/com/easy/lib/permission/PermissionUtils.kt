package com.easy.lib.permission

import android.Manifest.permission
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import java.util.*

/**
 * @author too young
 * @date 2017/12/18 14:56
 */
object PermissionUtils {

    const val WRITE_EXTERNAL_STORAGE = permission.WRITE_EXTERNAL_STORAGE
    const val READ_EXTERNAL_STORAGE = permission.READ_EXTERNAL_STORAGE
    const val ACCESS_COARSE_LOCATION = permission.ACCESS_COARSE_LOCATION
    const val ACCESS_FINE_LOCATION = permission.ACCESS_FINE_LOCATION
    const val CALL_PHONE = permission.CALL_PHONE

    private val PERMISSION_CODE = 2000

    private var listener: (Boolean) -> Unit = {

    }

    fun check(context: Activity, vararg permissions: String, listener: (Boolean) -> Unit) {
        //this.listener = listener;
        Log("android 系统版本号 " + Build.VERSION.SDK_INT)
        /** 判断是否为 Android 6.0以上 ,  */
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || permissions.isEmpty()) {
            Log("不需要权限")
            listener.invoke(true)
            return
        }
        /** 初始化 */
        val pkgManager = context.packageManager
        val packageName = context.packageName
        val boos: MutableList<String> = ArrayList()
        for (permission in permissions) {
            val boo = pkgManager.checkPermission(
                permission,
                packageName
            ) == PackageManager.PERMISSION_GRANTED
            if (!boo) {
                boos.add(permission)
                Log(permission)
            }
        }
        if (boos.size > 0) {
            val arrays: Array<String> = boos.toTypedArray()
            Log("开始申请权限")
            Log(toString(arrays))
            ActivityCompat.requestPermissions(context, arrays, PERMISSION_CODE)
        } else {
            Log("需要的权限全部都有")
            listener.invoke(true)
        }
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log("权限申请成功")
                    listener.invoke(true)
                } else {
                    listener.invoke(false)
                }
                return
            }
            else -> {
            }
        }
    }

    private fun Log(message: Any) {
        android.util.Log.d("PermissionUtils", message.toString())
    }

    private fun toString(strings: Array<String>): String {
        val stringBuilder = StringBuilder()
        for (s in strings) {
            stringBuilder.append(",").append(s)
        }
        return stringBuilder.toString()
    }

}