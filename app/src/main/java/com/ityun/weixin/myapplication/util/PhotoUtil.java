package com.ityun.weixin.myapplication.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.BuildConfig;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.ityun.weixin.myapplication.conn.FileConstance;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2018/1/24 0024.
 */

public class PhotoUtil {

    private static PhotoUtil photoUtil;

    private String name;

    private String path;

    public static PhotoUtil getInstance() {
        if (photoUtil == null)
            photoUtil = new PhotoUtil();
        return photoUtil;
    }


    /**
     * 获取拍照图片
     */
    public void getImageFromPhone(Activity activity, int request) {
        // 拍照我们用Action为MediaStore.ACTION_IMAGE_CAPTURE，
        // 有些人使用其他的Action但我发现在有些机子中会出问题，所以优先选择这个
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 获取文件
        name = "userImage";
        File file1 = new File(FileConstance.base);
        if (!file1.exists()) {
            file1.mkdirs();
        }
        path = FileConstance.base + "/" + name + ".jpg";
        File file = createFileIfNeed(path);
        // 拍照后原图回存入此路径下
        //targetSdkVersion 24以上 不在支持file:// 而得改成content://
        Log.e("insert",activity.getPackageName());
        Uri photoURI = FileProvider.getUriForFile(activity,
                activity.getPackageName() + ".provider",
                file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(intent, request);
    }

    // 在sd卡中创建一保存图片（原图和缩略图共用的）文件夹
    private File createFileIfNeed(String file_Name) {
        File fileJA = new File(FileConstance.base);
        if (!fileJA.exists()) {
            fileJA.mkdirs();
        }
        File fileNew = new File(file_Name);
        if (!fileNew.exists()) {
            try {
                fileNew.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileNew;
    }

    public String getPath() {
        return path;
    }

    public void deleteFile() {
        if (path != null) {
            File file = new File(path);
            file.delete();
        }
    }
}
