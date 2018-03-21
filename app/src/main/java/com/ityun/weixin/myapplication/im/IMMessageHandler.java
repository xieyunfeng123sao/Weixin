package com.ityun.weixin.myapplication.im;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.ityun.weixin.myapplication.MainActivity;
import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.base.App;
import com.ityun.weixin.myapplication.bean.NewFriend;
import com.ityun.weixin.myapplication.bean.User;
import com.ityun.weixin.myapplication.db.NewFriendManager;
import com.ityun.weixin.myapplication.listener.BmobTableListener;
import com.ityun.weixin.myapplication.model.UserModel;
import com.ityun.weixin.myapplication.listener.UpdateCacheListener;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMMessageType;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.BmobIMMessageHandler;
import cn.bmob.newim.notification.BmobNotificationManager;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static com.ityun.weixin.myapplication.base.App.context;

/**
 * Created by Administrator on 2018/1/17 0017.
 */

public class IMMessageHandler extends BmobIMMessageHandler {
    @Override
    public void onMessageReceive(final MessageEvent event) {
        executeMessage(event);
    }


    @Override
    public void onOfflineReceive(final OfflineMessageEvent event) {
        //离线消息，每次connect的时候会查询离线消息，如果有，此方法会被调用
        //每次调用connect方法时会查询一次离线消息，如果有，此方法会被调用
        Map<String, List<MessageEvent>> map = event.getEventMap();
        Logger.i("有" + map.size() + "个用户发来离线消息");
        //挨个检测下离线消息所属的用户的信息是否需要更新
        for (Map.Entry<String, List<MessageEvent>> entry : map.entrySet()) {
            List<MessageEvent> list = entry.getValue();
            int size = list.size();
            Logger.i("用户" + entry.getKey() + "发来" + size + "条消息");
            for (int i = 0; i < size; i++) {
                //处理每条消息
                executeMessage(list.get(i));
            }
        }
    }

    private void executeMessage(final MessageEvent event) {
        UserModel.getInstance().updateUserInfo(event, new UpdateCacheListener() {
            @Override
            public void done() {
                BmobIMMessage msg = event.getMessage();
                if (BmobIMMessageType.getMessageTypeValue(msg.getMsgType()) == 0) {
                    //自定义消息类型：0
                    processCustomMessage(msg, event.getFromUserInfo());
                } else {
                    //SDK内部内部支持的消息类型
                    processSDKMessage(msg, event);
                }
            }
        });
    }

    /**
     * 处理SDK支持的消息
     *
     * @param msg
     * @param event
     */
    private void processSDKMessage(BmobIMMessage msg, MessageEvent event) {
        if (BmobNotificationManager.getInstance(context).isShowNotification()) {
            //如果需要显示通知栏，SDK提供以下两种显示方式：
            Intent pendingIntent = new Intent(context, MainActivity.class);
            pendingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

            //TODO 消息接收：8.5、多个用户的多条消息合并成一条通知：有XX个联系人发来了XX条消息
            //BmobNotificationManager.getInstance(context).showNotification(event, pendingIntent);
            //TODO 消息接收：8.6、自定义通知消息：始终只有一条通知，新消息覆盖旧消息
            BmobIMUserInfo info = event.getFromUserInfo();
            //这里可以是应用图标，也可以将聊天头像转成bitmap
            Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
            BmobNotificationManager.getInstance(context).showNotification(largeIcon,
                    info.getName(), msg.getContent(), "您有一条新消息", pendingIntent);
        } else {
            //直接发送消息事件
            EventBus.getDefault().post(event);
        }
    }


    /**
     * 处理自定义消息类型
     *
     * @param msg
     */
    //TODO 好友管理：9.9、接收并处理好友相关的请求
    private void processCustomMessage(BmobIMMessage msg, BmobIMUserInfo info) {
        //消息类型
        String type = msg.getMsgType();
        //发送页面刷新的广播
//        EventBus.getDefault().post(new RefreshEvent());
        //处理消息
        if (type.equals("add")) {//接收到的添加好友的请求
            NewFriend friend = convert(msg);
            //本地好友请求表做下校验，本地没有的才允许显示通知栏--有可能离线消息会有些重复
            long id = NewFriendManager.getInstance(App.context).insertOrUpdateNewFriend(friend);
            if (id > 0) {
                showAddNotify(friend);
            }
        } else if (type.equals("agree")) {//接收到的对方同意添加自己为好友,此时需要做的事情：1、添加对方为好友，2、显示通知
            String extra = msg.getExtra();
            if (!TextUtils.isEmpty(extra)) {
                JSONObject json = null;
                try {
                    json = new JSONObject(extra);
                    String uid = json.getString("uid");
//                    String m = json.getString("msg");
                    addFriend(uid);//添加消息的发送方为好友
                    //这里应该也需要做下校验--来检测下是否已经同意过该好友请求，我这里省略了
                    showAgreeNotify(info, msg.getContent());
                    IMModel.getInstance().updataConversation(msg.getBmobIMConversation());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Logger.i("AgreeAddFriendMessage的extra为空");
            }
        } else {
            Toast.makeText(context, "接收到的自定义消息：" + msg.getMsgType() + "," + msg.getContent() + "," + msg.getExtra(), Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 显示对方添加自己为好友的通知
     *
     * @param friend
     */
    private void showAddNotify(NewFriend friend) {
        Intent pendingIntent = new Intent(context, MainActivity.class);
        pendingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        //这里可以是应用图标，也可以将聊天头像转成bitmap
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon);
        BmobNotificationManager.getInstance(context).showNotification(largeIcon,
                friend.getName(), friend.getMsg(), friend.getName() + "请求添加你为朋友", pendingIntent);
    }


    /**
     * 显示对方同意添加自己为好友的通知
     *
     * @param info
     */
    private void showAgreeNotify(BmobIMUserInfo info, String msg) {
        Intent pendingIntent = new Intent(context, MainActivity.class);
        pendingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon);
        BmobNotificationManager.getInstance(context).showNotification(largeIcon, info.getName(), msg, msg, pendingIntent);
    }


    /**
     * 将BmobIMMessage转成NewFriend
     *
     * @param msg 消息
     * @return
     */
    public static NewFriend convert(BmobIMMessage msg) {
        NewFriend add = new NewFriend();
        String content = msg.getContent();
        add.setMsg(content);
        add.setTime(msg.getCreateTime());
        add.setStatus(0);
        try {
            String extra = msg.getExtra();
            if (!TextUtils.isEmpty(extra)) {
                JSONObject json = new JSONObject(extra);
                String name = json.getString("name");
                add.setName(name);
                String avatar = json.getString("avatar");
                add.setAvatar(avatar);
                String nickname = json.getString("nickname");
                add.setNickname(nickname);
                add.setUid(json.getString("uid"));
            } else {
                Logger.i("AddFriendMessage的extra为空");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return add;
    }

    /**
     * TODO 好友管理：9.11、收到同意添加好友后添加好友
     *
     * @param uid
     */
    private void addFriend(final String uid) {
        UserModel.getInstance().queryById(uid, new BmobTableListener<User>() {
            @Override
            public void onSucess(User object) {
                UserModel.getInstance()
                        .addNewFriend((User) object, new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    Logger.e("success");
                                } else {
                                    Logger.e(e.getMessage());
                                }
                            }
                        });
            }

            @Override
            public void onFail(BmobException e) {

            }
        });


    }

}
