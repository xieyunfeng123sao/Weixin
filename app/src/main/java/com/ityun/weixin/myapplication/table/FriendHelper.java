package com.ityun.weixin.myapplication.table;

import com.ityun.weixin.myapplication.bean.FriendInfo;
import com.ityun.weixin.myapplication.listener.BmobTableListener;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2018/3/12 0012.
 */

public class FriendHelper {

    private static FriendHelper friendHelper;

    public static FriendHelper getInstance() {
        if (friendHelper == null) {
            friendHelper = new FriendHelper();
        }
        return friendHelper;
    }

    public void addFriend(FriendInfo friendInfo, final BmobTableListener listener) {
        friendInfo.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    listener.onSucess(s);
                } else {
                    listener.onFail(e);
                }
            }
        });
    }

}
