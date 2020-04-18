package com.easy.lib.permission

import android.Manifest


enum class Permission(val permission: String) {
    /* READ_EXTERNAL_STORAGE*/
    READ_EXTERNAL_STORAGE(Manifest.permission.READ_EXTERNAL_STORAGE),
    STORAGE(Manifest.permission_group.STORAGE)
}