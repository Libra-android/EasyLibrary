package com.easy.lib.utils

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileNotFoundException

/**
 * 上传多图的对象拼接
 * Created by wang on 2016/3/28.
 */
object EasyUpload {

    fun getRequestBody(filePath: List<String?>): RequestBody {
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        filePath.forEach {
            val file = File(it)
            if(file.exists()){
                builder.addFormDataPart("files", file.name, RequestBody.create(MediaType.parse("image/png"), file))
            }
        }
        return builder.build()
    }

    fun getRequestBody(filePath: String?): RequestBody {
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        val file = File(filePath)
        if (file.exists()) {
            builder.addFormDataPart("files", file.name, RequestBody.create(MediaType.parse("image/png"), file))
        } else {
            try {
                throw FileNotFoundException()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
        return builder.build()
    }

}