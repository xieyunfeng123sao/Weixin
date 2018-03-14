package com.ityun.weixin.myapplication.bean;

import com.ityun.weixin.myapplication.conn.TableName;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2018/3/12 0012.
 */

public class FriendInfo extends BmobObject {

   private UserInfo user;

   private UserInfo friend;
    public FriendInfo()
    {
        this.setTableName(TableName.friendTable);
    }
    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public UserInfo getFriend() {
        return friend;
    }

    public void setFriend(UserInfo friend) {
        this.friend = friend;
    }
}
