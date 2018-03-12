package com.ityun.weixin.myapplication.im;

import com.ityun.weixin.myapplication.bean.NewFriend;
import com.ityun.weixin.myapplication.cache.NewFriendCache;

import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.BmobIMMessageHandler;

/**
 * Created by Administrator on 2018/1/17 0017.
 */

public class IMMessageHandler extends BmobIMMessageHandler {
    @Override
    public void onMessageReceive(final MessageEvent event) {
        if(event.getMessage().getMsgType().equals("add"))
        {
            addFriend(event);
        }
    }


    @Override
    public void onOfflineReceive(final OfflineMessageEvent event) {
        //离线消息，每次connect的时候会查询离线消息，如果有，此方法会被调用
    }

    /**
     * 如果是添加好友的消息就将信息保存到本地
     * @param event
     */
    private  void addFriend(MessageEvent  event)
    {
        NewFriend newFriend=new NewFriend();
        newFriend.setAvatar(event.getFromUserInfo().getAvatar());
        newFriend.setId(event.getMessage().getId());
        newFriend.setMsg(event.getMessage().getContent());
        newFriend.setName(event.getFromUserInfo().getName());
        newFriend.setStatus(0);
        newFriend.setUid(event.getFromUserInfo().getUserId());
        newFriend.setTime(event.getMessage().getCreateTime());
        NewFriendCache.getInstance().saveNewFriend(newFriend);
    }

}
