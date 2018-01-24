package com.ityun.weixin.myapplication.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Administrator on 2018/1/22 0022.
 */

public class PermissionUtil {

    public static int readFile(Activity context, int permiss) {
        //第二个参数是需要申请的权限
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //权限还没有授予，需要在这里写申请权限的代码
            ActivityCompat.requestPermissions(context,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, permiss);
            return 0;
        } else {
            //权限已经被授予，在这里直接写要执行的相应方法即可
            return 1;
        }
    }

    public static int openCamare(Activity context, int permiss) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CAMERA},
                    permiss);
            return 0;
        }
        else
        {
            return 1;
        }
    }

}
