package com.easy.lib.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.easy.lib.permission.PermissionUtils

/**
 * @author too young
 * @date  2020/4/1 15:57
 */
abstract class LibActivity : AppCompatActivity() {


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun back(view: View) {
        finish()
    }

    fun Int.dp() = this.toFloat().dp()

    fun Double.dp() = this.toFloat().dp()

    fun Float.dp() = (this * resources.displayMetrics.density + 0.5f).toInt()

}