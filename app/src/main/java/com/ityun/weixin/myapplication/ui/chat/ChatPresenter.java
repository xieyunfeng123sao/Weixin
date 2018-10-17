package com.ityun.weixin.myapplication.ui.chat;

import com.ityun.weixin.myapplication.im.IMMessage;
import com.ityun.weixin.myapplication.im.IMModel;

/**
 * Created by 77367 on 2018/10/17.
 */

public class ChatPresenter implements ChatContract.Presenter{

    private ChatContract.View view;

    public ChatPresenter(ChatContract.View view) {
        this.view = view;

    }
    @Override
    public void start() {

    }

    @Override
    public void sendMessage(IMMessage imMessage) {
        if(imMessage.getChatType()==0)
        {
            IMModel.getInstance().sendTextMessage(imMessage);
        }
        if(imMessage.getChatType()==1)
        {
            IMModel.getInstance().sendImageMessage(imMessage);
        }
        if(imMessage.getChatType()==2)
        {
            IMModel.getInstance().sendVideoMessage(imMessage);
        }
        if(imMessage.getChatType()==3)
        {
            IMModel.getInstance().sendLocationMessage(imMessage);
        }
        if(imMessage.getChatType()==4)
        {
            IMModel.getInstance().sendVoiceMessage(imMessage);
        }
        if(imMessage.getChatType()==5)
        {
            IMModel.getInstance().sendFileMessage(imMessage);
        }
    }
}
