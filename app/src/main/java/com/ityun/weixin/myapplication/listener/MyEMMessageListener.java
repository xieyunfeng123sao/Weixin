package com.ityun.weixin.myapplication.listener;

import android.util.Log;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMMessage;
import com.ityun.weixin.myapplication.event.IMRefreshEvent;
import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Administrator on 2018/4/28 0028.
 */

public class MyEMMessageListener implements EMMessageListener {

    @Override
    public void onMessageReceived(List<EMMessage> messages) {
        //收到消息
        EventBus.getDefault().post(new IMRefreshEvent(messages.get(messages.size() - 1)));
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> messages) {
        //收到透传消息
        Log.e("MyEMMessageListener", messages.size() + "==============");
    }

    @Override
    public void onMessageRead(List<EMMessage> messages) {
        //收到已读回执
        Log.e("MyEMMessageListener", messages.size() + "==============");
    }

    @Override
    public void onMessageDelivered(List<EMMessage> messages) {
        //收到已送达回执
        Log.e("MyEMMessageListener", messages.size() + "==============");
    }

    @Override
    public void onMessageChanged(EMMessage message, Object change) {
        //消息状态变动
        Log.e("MyEMMessageListener", message + "==============");
    }


}
