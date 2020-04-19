package com.easy.lib.permission

import android.Manifest

/**
 * STORAGE 外部存储
 * CALENDAR 日历
 * CALL_LOG 通话记录
 * CAMERA 相机
 * CONTACTS 联系人和个人资料
 * CALL_PHONE 打电话
 * BODY_SENSORS 感应器
 * SMS 短信
 * MICROPHONE 麦克风
 * LOCATION 定位
 */
enum class Permission(val permission: String) {
    /* READ_EXTERNAL_STORAGE*/
    READ_EXTERNAL_STORAGE(Manifest.permission.READ_EXTERNAL_STORAGE),
    WRITE_EXTERNAL_STORAGE(Manifest.permission.WRITE_EXTERNAL_STORAGE),
    READ_CALENDAR(Manifest.permission.READ_CALENDAR),
    WRITE_CALENDAR(Manifest.permission.WRITE_CALENDAR),
    READ_CALL_LOG(Manifest.permission.READ_CALL_LOG),
    WRITE_CALL_LOG(Manifest.permission.WRITE_CALL_LOG),
    CAMERA(Manifest.permission.CAMERA),
    READ_CONTACTS(Manifest.permission.READ_CONTACTS),
    WRITE_CONTACTS(Manifest.permission.WRITE_CONTACTS),
    CALL_PHONE(Manifest.permission.CALL_PHONE),
    BODY_SENSORS(Manifest.permission.BODY_SENSORS),
    READ_SMS(Manifest.permission.READ_SMS),
    SEND_SMS(Manifest.permission.SEND_SMS),
    MICROPHONE(Manifest.permission_group.MICROPHONE),
    LOCATION(Manifest.permission_group.LOCATION),
}



