package com.easy.lib;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * 上传多图的对象拼接
 * Created by wang on 2016/3/28.
 */
public class UploadUtils {

    public static RequestBody getRequestBody(List<String> filePath) {

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                /*.addFormDataPart("title", title)*/;   //Here you can add the fix number of data.
        for (int i = 0; i < filePath.size(); i++) {
            File file = new File(filePath.get(i));
            if (file.exists()) {
                builder.addFormDataPart("files", file.getName(), RequestBody.create(MediaType.parse("image/png"), file));
            }
        }
        return builder.build();
    }

    public static RequestBody getRequestBody(String filePath) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        File file = new File(filePath);
        if (file.exists()) {
            builder.addFormDataPart("files", file.getName(), RequestBody.create(MediaType.parse("image/png"), file));
        } else {
            try {
                throw new FileNotFoundException();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return builder.build();
    }




}
