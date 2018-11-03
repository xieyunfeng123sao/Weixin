package com.ityun.weixin.myapplication.listener;

import android.util.Log;

import com.hyphenate.EMContactListener;
import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.bean.NewFriend;
import com.ityun.weixin.myapplication.bean.User;
import com.ityun.weixin.myapplication.dao.NewFriendUtil;
import com.ityun.weixin.myapplication.db.Config;
import com.ityun.weixin.myapplication.event.IMNewFriendEvent;
import com.ityun.weixin.myapplication.im.IMModel;
import com.ityun.weixin.myapplication.model.UserModel;
import com.ityun.weixin.myapplication.util.SpUtil;

import org.greenrobot.eventbus.EventBus;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2018/4/28 0028.
 */

public class MyEMContactListener implements EMContactListener {
    @Override
    public void onContactAdded(String username) {
        // 增加联系人时回调此方法
    }

    @Override
    public void onContactDeleted(String username) {
        //被删除时回调此方法
    }

    @Override
    public void onContactInvited(String username, String reason) {
        //收到好友邀请
        getContactInvited(username, reason);
    }

    @Override
    public void onFriendRequestAccepted(String username) {
        // 好友请求被同意
        receiveAgree(username);
    }

    @Override
    public void onFriendRequestDeclined(String username) {
        //好友请求被拒绝
    }

    private void getContactInvited(String username, final String reason) {
        UserModel.getInstance().queryByNum(username, new BmobTableListener<User>() {
            @Override
            public void onSucess(User object) {
                NewFriend newFriend = new NewFriend(0l, object.getObjectId(), reason, object.getUsername(), object.getAvatar(), 0, 0l, object.getNickname());
                NewFriendUtil.getInstance().insert(newFriend);
                EventBus.getDefault().post(new IMNewFriendEvent("notice"));
            }

            @Override
            public void onFail(BmobException e) {

            }
        });
    }

    private void receiveAgree(String username) {
        UserModel.getInstance().queryByNum(username, new BmobTableListener<User>() {
            @Override
            public void onSucess(final User object) {

                        UserModel.getInstance().addNewFriend(object, new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if(e==null)
                                {
                                    EventBus.getDefault().post(new IMNewFriendEvent("hasAdd"));
                                }
                            }
                        });
                    }
            @Override
            public void onFail(BmobException e) {

            }
        });
//


    }
}
