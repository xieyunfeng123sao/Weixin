package com.ityun.weixin.myapplication.event;

/**
 * Created by Administrator on 2018/4/28 0028.
 */

public class IMNewFriendEvent {

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
