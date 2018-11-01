package com.ityun.weixin.myapplication.conn;

import android.os.Environment;

/**
 * Created by Administrator on 2018/10/24 0024.
 */

public interface Constant {

    public interface MessageType
    {
        public String[] str={"图片","视频","位置","语音","文件"};
    }

    public  String basePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Weixin";

}
