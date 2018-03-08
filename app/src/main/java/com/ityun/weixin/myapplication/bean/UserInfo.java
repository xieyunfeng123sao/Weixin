package com.ityun.weixin.myapplication.bean;

import com.ityun.weixin.myapplication.conn.TableName;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by 慢嗨 on 2018/1/17 0017.
 */

/**
 * 用户信息
 */
public class UserInfo extends BmobObject {


    /**
     * 登录名
     */
    private String loginName;
    /**
     * 登录密码
     */
    private String password;


    /**
     * 用户昵称
     */
    private String userName;
    /**
     * 用户头像
     */
    private String userPic;

    /**
     * 创建时间
     */
    private BmobDate createTime;

    /**
     * 更新时间
     */
    private BmobDate updataTime;




    public UserInfo()
    {
        this.setTableName(TableName.userTable);
    }



    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public BmobDate getCreateTime() {
        return createTime;
    }

    public void setCreateTime(BmobDate createTime) {
        this.createTime = createTime;
    }

    public BmobDate getUpdataTime() {
        return updataTime;
    }

    public void setUpdataTime(BmobDate updataTime) {
        this.updataTime = updataTime;
    }
}
