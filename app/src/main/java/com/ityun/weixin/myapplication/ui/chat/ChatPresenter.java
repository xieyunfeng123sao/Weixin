package com.ityun.weixin.myapplication.ui.chat;

import com.hyphenate.chat.EMMessage;
import com.ityun.weixin.myapplication.im.IMMessage;
import com.ityun.weixin.myapplication.im.IMModel;

/**
 * Created by 77367 on 2018/10/17.
 */

public class ChatPresenter implements ChatContract.Presenter {

    private ChatContract.View view;

    public ChatPresenter(ChatContract.View view) {
        this.view = view;

    }

    @Override
    public void start() {

    }

    @Override
    public EMMessage sendMessage(IMMessage imMessage) {
        EMMessage emMessage = null;
        if (imMessage.getMessageType() == 0) {
            emMessage = IMModel.getInstance().sendTextMessage(imMessage);
        }
        if (imMessage.getMessageType() == 1) {
            emMessage = IMModel.getInstance().sendImageMessage(imMessage);
        }
        if (imMessage.getMessageType() == 2) {
            emMessage = IMModel.getInstance().sendVideoMessage(imMessage);
        }
        if (imMessage.getMessageType() == 3) {
            emMessage = IMModel.getInstance().sendLocationMessage(imMessage);
        }
        if (imMessage.getMessageType() == 4) {
            emMessage = IMModel.getInstance().sendVoiceMessage(imMessage);
        }
        if (imMessage.getMessageType() == 5) {
            emMessage = IMModel.getInstance().sendFileMessage(imMessage);
        }
        return emMessage;
    }
}
