package com.easy.lib.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.util.DisplayMetrics
import android.view.WindowManager

/**
 * @author too young
 * @date 2020/1/8 17:22
 */
object EasyDevice {

    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    /**
     * 获取屏幕相关信息
     *
     * @return
     */
    fun getDisplay(contact: Context): DisplayMetrics {
        val wm = contact.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= 17) {
            wm.defaultDisplay.getRealMetrics(dm)
        } else {
            wm.defaultDisplay.getMetrics(dm)
        }
        return dm
    }

    /**
     * 检测Sdcard是否存在
     *
     * @return
     */
    val isExitsSdcard = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED


    //获取版本号
    fun getVersionName(context: Context): String {
        return try {
            val pi =
                context.packageManager.getPackageInfo(context.packageName, 0)
            pi.versionName
        } catch (e: PackageManager.NameNotFoundException) { // TODO Auto-generated catch block
            e.printStackTrace()
            "未知版本"
        }
    }

    //获取版本号(内部识别号)
    fun getVersionCode(context: Context): Int {
        return try {
            val pi =
                context.packageManager.getPackageInfo(context.packageName, 0)
            pi.versionCode
        } catch (e: PackageManager.NameNotFoundException) { // TODO Auto-generated catch block
            e.printStackTrace()
            0
        }
    }

}