package com.ityun.weixin.myapplication.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2018/3/16 0016.
 */

public class Friend extends BmobObject {

    private User user;
    private User friendUser;
    private transient String pinyin;
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFriendUser() {
        return friendUser;
    }

    public void setFriendUser(User friendUser) {
        this.friendUser = friendUser;
    }

    public String getPinyin() {
        return pinyin;
    }
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}
