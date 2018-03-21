package com.ityun.weixin.myapplication.im;

import android.text.TextUtils;

import com.ityun.weixin.myapplication.bean.User;
import com.ityun.weixin.myapplication.model.UserModel;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMExtraMessage;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.ConnectStatusChangeListener;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by Administrator on 2018/2/27 0027.
 */

public class IMModel {

    public static IMModel instance;

    private String userId;

    public BmobIMUserInfo info;

    public static IMModel getInstance() {
        if (instance == null)
            instance = new IMModel();
        return instance;
    }

    public  void  updataUser(User user)
    {
        info=new BmobIMUserInfo(user.getObjectId(), user.getUsername(), user.getAvatar());
    }

    public void login(final String userId) {
        if (!TextUtils.isEmpty(userId)) {
            this.userId = userId;
            BmobIM.connect(userId, new ConnectListener() {
                @Override
                public void done(String uid, BmobException e) {
                    if (e == null) {
                        //连接成功
                        BmobIM.getInstance().updateUserInfo(info);
                        connectStatusChangeListener();
                    } else {
                        //连接失败
                    }
                }
            });
        }
    }

    /**
     * 发送添加好友的请求
     */
    public void sendAddFriendMessage(User userInfo, String sendMsg, final OnMessageListener onMessageListener) {
        User user= UserModel.getInstance().getUser();
        //TODO 会话：4.1、创建一个暂态会话入口，发送好友请求
        BmobIMConversation conversationEntrance = BmobIM.getInstance().startPrivateConversation(new BmobIMUserInfo(userInfo.getObjectId(), userInfo.getUsername(), userInfo.getAvatar()), true, null);
        //TODO 消息：5.1、根据会话入口获取消息管理，发送好友请求
        BmobIMConversation messageManager = BmobIMConversation.obtain(BmobIMClient.getInstance(), conversationEntrance);
        BmobIMExtraMessage msg = new BmobIMExtraMessage();
        if (sendMsg == null || sendMsg.length() == 0) {
            msg.setContent("我是" + user.getNickname());//给对方的一个留言信息
        } else {
            msg.setContent(sendMsg);
        }
        //TODO 这里只是举个例子，其实可以不需要传发送者的信息过去
        Map<String, Object> map = new HashMap<>();
        map.put("name", info.getName());//发送者账号
        map.put("avatar", info.getAvatar());//发送者的头像
        map.put("nickname",user.getNickname());//发送者昵称
        map.put("uid", info.getUserId());//发送者的uid
        msg.setMsgType("add");
        msg.setIsTransient(true);
        msg.setExtraMap(map);
        messageManager.sendMessage(msg, new MessageSendListener() {
            @Override
            public void done(BmobIMMessage msg, BmobException e) {
                if (e == null) {//发送成功
                    onMessageListener.onSucess(msg);
                } else {//发送失败
                    onMessageListener.onFail(e);
                }
            }
        });
    }


    public void sendAgreeFriendMessage(User userInfo, final OnMessageListener onMessageListener) {
        //TODO 会话：4.1、创建一个暂态会话入口，发送好友请求
        BmobIMConversation conversationEntrance = BmobIM.getInstance().startPrivateConversation(new BmobIMUserInfo(userInfo.getObjectId(), userInfo.getUsername(), userInfo.getAvatar()), true, null);
        //TODO 消息：5.1、根据会话入口获取消息管理，发送好友请求
        BmobIMConversation messageManager = BmobIMConversation.obtain(BmobIMClient.getInstance(), conversationEntrance);
        BmobIMExtraMessage msg = new BmobIMExtraMessage();
        msg.setContent("我通过了你的朋友验证请求，现在我们可以开始聊天了");//给对方的一个留言信息
        //TODO 这里只是举个例子，其实可以不需要传发送者的信息过去
        Map<String, Object> map = new HashMap<>();
        map.put("name", info.getName());//发送者姓名
        map.put("avatar", info.getAvatar());//发送者的头像
        map.put("uid", info.getUserId());//发送者的uid
        msg.setMsgType("agree");
        msg.setIsTransient(false);
        msg.setExtraMap(map);
        messageManager.sendMessage(msg, new MessageSendListener() {
            @Override
            public void done(BmobIMMessage msg, BmobException e) {
                if (e == null) {//发送成功
                    onMessageListener.onSucess(msg);
                } else {//发送失败
                    onMessageListener.onFail(e);
                }
            }
        });
    }


    private void connectStatusChangeListener() {
        //TODO 连接：3.3、监听连接状态，可通过BmobIM.getInstance().getCurrentStatus()来获取当前的长连接状态
        BmobIM.getInstance().setOnConnectStatusChangeListener(new ConnectStatusChangeListener() {
            @Override
            public void onChange(ConnectionStatus status) {
                Logger.e(status.getMsg());
                if (status.getMsg().equals(ConnectionStatus.CONNECTING)) {

                } else {

                }
            }
        });
    }


    public interface OnMessageListener {
        void onSucess(BmobIMMessage message);

        void onFail(BmobException e);
    }

    //TODO 会话：4.2、查询全部会话
    public  List<BmobIMConversation>  getAllConversation()
    {
       return  BmobIM.getInstance().loadAllConversation();
    }

    //TODO 会话：4.2、查询全部会话
    public  void   updataConversation(BmobIMConversation bmobIMConversation)
    {
        BmobIM.getInstance().updateConversation(bmobIMConversation);
    }
}
