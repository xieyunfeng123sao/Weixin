package com.ityun.weixin.myapplication.bean;

import com.ityun.weixin.myapplication.conn.TableName;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2018/3/12 0012.
 */

public class FriendInfo extends BmobObject {

    //用户
    private String  userNum;
    //好友num
    private String  friendNum;
    //备注
    private String remarks;

    public FriendInfo()
    {
        this.setTableName(TableName.friendTable);
    }


    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getFriendNum() {
        return friendNum;
    }

    public void setFriendNum(String friendNum) {
        this.friendNum = friendNum;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
