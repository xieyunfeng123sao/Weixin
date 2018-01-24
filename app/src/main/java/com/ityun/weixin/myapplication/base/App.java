package com.ityun.weixin.myapplication.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.ityun.weixin.myapplication.im.IMMessageHandler;
import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

/**
 * Created by Administrator on 2018/1/16.
 */

public class App extends Application {

    public static App app;


    public List<Activity> activityList = new ArrayList<>();

    public static App getInstance() {
        if (app == null)
            app = new App();
        return app;
    }


    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        init();
    }

    /**
     * bomb云的初始化
     */
    private void init() {
        //提供以下两种方式进行初始化操作：
        //第一：默认初始化
//        Bmob.initialize(this, "795c20bb0a69c473f2b8f299058981e4");
        // 注:自v3.5.2开始，数据sdk内部缝合了统计sdk，开发者无需额外集成，传渠道参数即可，不传默认没开启数据统计功能
        //Bmob.initialize(this, "Your Application ID","bmob");

        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        BmobConfig config = new BmobConfig.Builder(this)
                //设置appkey
                .setApplicationId("795c20bb0a69c473f2b8f299058981e4")
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(30)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(1024 * 1024)
                //文件的过期时间(单位为秒)：默认1800s
                .setFileExpiration(2500)
                .build();
        Bmob.initialize(config);

        //TODO 集成：1.8、初始化IM SDK，并注册消息接收器
        if (getApplicationInfo().packageName.equals(getMyProcessName())) {
            BmobIM.init(this);
            BmobIM.registerDefaultMessageHandler(new IMMessageHandler());
        }
    }

    /**
     * 获取当前运行的进程名
     *
     * @return
     */
    public static String getMyProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 添加activity
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (!activityList.contains(activity))
            activityList.add(activity);
    }

    /**
     * 删除activity
     * @param activity
     */
    public void destoryActivity(Activity activity) {
        if (activityList.contains(activity)) {
            activityList.remove(activity);
        }
    }

    /**
     * 删除除了当前activity外的所有activity
     * @param activity
     */
    public void  finishOrActivity(Activity activity)
    {
        for(Activity ac:activityList)
        {
            if(ac!=activity)
            {
                ac.finish();
            }
        }
    }

}
