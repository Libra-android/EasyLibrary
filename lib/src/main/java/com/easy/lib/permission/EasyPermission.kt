package com.easy.lib.permission

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object EasyPermission {

    private const val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0x001

    private var hasPermission: (Boolean) -> Unit = {

    }


    /**
     *
     * @param thisActivity
     * @param hasPermission 申请权限回调方法 true代表已获取权限
     * @param shouldShowRequestPermissionRationale 当用户之前已经拒绝过这些权限时，拦截请求权限，向用户说明该权限为何申请不传该参数是默认为 null，表示不拦截请求权限，直接申请权限
     *
     */
    fun requestPermission(
        thisActivity: Activity,
        permissions: Array<Permission>,
        hasPermission: (Boolean) -> Unit,
        shouldShowRequestPermissionRationale: (() -> Unit)?
    ) {
        this.hasPermission = hasPermission
        //没有获取权限
        if (!checkPermission(thisActivity, permissions)) {

            val shouldShowRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(
                thisActivity,
                Manifest.permission.READ_CONTACTS
            )

            if (shouldShowRequestPermissionRationale != null && shouldShowRequestPermission) {
                shouldShowRequestPermissionRationale.invoke()
            } else {
                requestPermissions(thisActivity)
            }
        } else {
            //已授予权限
            hasPermission.invoke(true)
        }

    }


    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {

        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_CONTACTS -> {
                //如果取消请求，则结果数组(grantResults)为空。
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //授予了权限
                    hasPermission.invoke(true)
                } else {
                    //权限被拒绝
                    hasPermission.invoke(false)
                }
            }
            else -> {

            }
        }
    }

    /**
     * 判断是否获取权限
     * 返回true 代表已拥有权限
     */
    fun checkPermission(thisActivity: Activity, permissions: Array<Permission>) =
        ContextCompat.checkSelfPermission(
            thisActivity,
            Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED


    /**
     * 不进行权限判断，直接申请权限
     */
    fun requestPermissions(thisActivity: Activity) {

        ActivityCompat.requestPermissions(
            thisActivity,
            arrayOf(Manifest.permission.READ_CONTACTS),
            MY_PERMISSIONS_REQUEST_READ_CONTACTS
        )

    }


    /**
     * 将权限集合转成系统 API 需要的权限集合
     */
    private fun Array<Permission>.toPermissions(): Array<String> {
        val permissions = arrayOf<String>()
        this.forEachIndexed { index, permission ->
            permissions[index] = permission.permission
        }
        return permissions
    }

}