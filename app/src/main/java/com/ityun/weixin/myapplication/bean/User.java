package com.ityun.weixin.myapplication.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2018/3/16 0016.
 */

public class User extends BmobUser {

    private String avatar;

    private  String nickname;

    public User(){}

    public User(NewFriend friend){
        setObjectId(friend.getUid());
        setUsername(friend.getName());
        setAvatar(friend.getAvatar());
        setNickname(friend.getNickname());
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
