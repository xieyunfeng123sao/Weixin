package com.ityun.weixin.myapplication.event;

/**
 * Created by Administrator on 2018/4/28 0028.
 */

public class IMLoginEvent {

    private String loginResult;

    public IMLoginEvent() {
    }

    public IMLoginEvent(String loginResult) {
        this.loginResult = loginResult;
    }

    public String getLoginResult() {
        return loginResult;
    }

    public void setLoginResult(String loginResult) {
        this.loginResult = loginResult;
    }
}
