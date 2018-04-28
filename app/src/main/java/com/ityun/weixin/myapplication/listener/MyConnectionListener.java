package com.ityun.weixin.myapplication.listener;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;

/**
 * Created by Administrator on 2018/4/28 0028.
 */

public class MyConnectionListener implements EMConnectionListener {
    @Override
    public void onConnected() {
    }

    @Override
    public void onDisconnected(final int error) {
        if (error == EMError.USER_REMOVED) {
            // 显示帐号已经被移除
        } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
            // 显示帐号在其他设备登录
        } else {

        }
    }
}