package com.ityun.weixin.myapplication.event;

/**
 * Created by Administrator on 2018/4/28 0028.
 */

public class IMNewFriendEvent {
    //notice 好友邀请的通知消息   hasAdd 已经添加好友
    private String result;

    public IMNewFriendEvent() {

    }


    public IMNewFriendEvent(String result) {
        this.result = result;
    }


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
