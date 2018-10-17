package com.ityun.weixin.myapplication.im;

import android.util.Log;
import android.widget.RemoteViews;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.NetUtils;
import com.ityun.weixin.myapplication.event.IMLoginEvent;
import com.ityun.weixin.myapplication.event.IMRefreshEvent;
import com.ityun.weixin.myapplication.listener.IMFriendCallBack;
import com.ityun.weixin.myapplication.listener.MyConnectionListener;
import com.ityun.weixin.myapplication.listener.MyEMContactListener;
import com.ityun.weixin.myapplication.listener.MyEMMessageListener;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/26 0026.
 */

public class IMModel implements EMCallBack {

    public static int LOGIN_STATE = 0;

    private static IMModel ourInstance = new IMModel();


    private MyEMMessageListener myEMMessageListener;

    public static IMModel getInstance() {
        return ourInstance;
    }

    public void login(String name, String password) {

        EMClient.getInstance().login(name, password, new EMCallBack() {
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                LOGIN_STATE = 1;
                EventBus.getDefault().post(new IMLoginEvent("sucess"));
            }

            @Override
            public void onError(int code, String error) {
                LOGIN_STATE = 0;
                EventBus.getDefault().post(new IMLoginEvent("error"));
            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });
    }


    public void addConnectionListener() {
        EMClient.getInstance().addConnectionListener(new MyConnectionListener());
    }


    public EMMessage sendTextMessage(IMMessage imMessagee) {
        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        EMMessage message = EMMessage.createTxtSendMessage(imMessagee.getContent(),imMessagee.getFriendId());
        //如果是群聊，设置chattype，默认是单聊
        if (imMessagee.getChatType() == 1) {
            message.setChatType(EMMessage.ChatType.GroupChat);
        }
        message.setMessageStatusCallback(this);
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);
        return message;
    }

    public EMMessage sendVoiceMessage(IMMessage imMessage) {
        //filePath为语音文件路径，length为录音时间(秒)
        EMMessage message = EMMessage.createVoiceSendMessage(imMessage.getPath(), imMessage.getLength(), imMessage.getFriendId());
        //如果是群聊，设置chattype，默认是单聊
        if (imMessage.getChatType() == 1)
            message.setChatType(EMMessage.ChatType.GroupChat);
        message.setMessageStatusCallback(this);
        EMClient.getInstance().chatManager().sendMessage(message);
        return message;
    }

    public EMMessage sendVideoMessage(IMMessage imMessage) {
        //videoPath为视频本地路径，thumbPath为视频预览图路径，videoLength为视频时间长度
        EMMessage message = EMMessage.createVideoSendMessage(imMessage.getPath(), imMessage.getThumbPath(), imMessage.getLength(), imMessage.getFriendId());
        //如果是群聊，设置chattype，默认是单聊
        if (imMessage.getChatType() == 1)
            message.setChatType(EMMessage.ChatType.GroupChat);
        message.setMessageStatusCallback(this);
        EMClient.getInstance().chatManager().sendMessage(message);
        return  message;
    }

    public EMMessage sendImageMessage(IMMessage imMessage) {
        //imagePath为图片本地路径，false为不发送原图（默认超过100k的图片会压缩后发给对方），需要发送原图传true
        EMMessage message = EMMessage.createImageSendMessage(imMessage.getPath(), false, imMessage.getFriendId());
        //如果是群聊，设置chattype，默认是单聊
        if (imMessage.getChatType() == 1)
            message.setChatType(EMMessage.ChatType.GroupChat);
        message.setMessageStatusCallback(this);
        EMClient.getInstance().chatManager().sendMessage(message);
        return  message;
    }

    public EMMessage sendLocationMessage(IMMessage imMessage) {
        //latitude为纬度，longitude为经度，locationAddress为具体位置内容
        EMMessage message = EMMessage.createLocationSendMessage(imMessage.getLatitude(), imMessage.getLongitude(), imMessage.getLocationAddress(), imMessage.getFriendId());
        //如果是群聊，设置chattype，默认是单聊
        if (imMessage.getChatType() == 1)
            message.setChatType(EMMessage.ChatType.GroupChat);
        message.setMessageStatusCallback(this);
        EMClient.getInstance().chatManager().sendMessage(message);
        return  message;
    }

    public EMMessage sendFileMessage(IMMessage imMessage) {
        EMMessage message = EMMessage.createFileSendMessage(imMessage.getPath(), imMessage.getFriendId());
        // 如果是群聊，设置chattype，默认是单聊
        if (imMessage.getChatType() == 1)
            message.setChatType(EMMessage.ChatType.GroupChat);
        message.setMessageStatusCallback(this);
        EMClient.getInstance().chatManager().sendMessage(message);
        return message;
    }

    public void sendActionMessage() {
        EMMessage cmdMsg = EMMessage.createSendMessage(EMMessage.Type.CMD);
        //支持单聊和群聊，默认单聊，如果是群聊添加下面这行
//        cmdMsg.setChatType(EMMessage.ChatType.GroupChat);
        String action = "action1";//action可以自定义
        EMCmdMessageBody cmdBody = new EMCmdMessageBody(action);
        String toUsername = "test1";//发送给某个人
        cmdMsg.setTo(toUsername);
        cmdMsg.addBody(cmdBody);
        cmdMsg.setMessageStatusCallback(this);
        EMClient.getInstance().chatManager().sendMessage(cmdMsg);
    }


    public void sendMyslfMessage() {
        EMMessage message = EMMessage.createTxtSendMessage("", "");
        // 增加自己特定的属性
        message.setAttribute("attribute1", "value");
        message.setAttribute("attribute2", true);
        EMClient.getInstance().chatManager().sendMessage(message);
        //接收消息的时候获取到扩展属性
        //获取自定义的属性，第2个参数为没有此定义的属性时返回的默认值
        message.getStringAttribute("attribute1", null);
        message.getBooleanAttribute("attribute2", false);
        message.setMessageStatusCallback(this);
    }


    public void addMessageListener(MyEMMessageListener myEMMessageListener) {
        this.myEMMessageListener = myEMMessageListener;
        EMClient.getInstance().chatManager().addMessageListener(myEMMessageListener);

    }

    public void removeMessageListener() {
        if (myEMMessageListener != null)
            EMClient.getInstance().chatManager().removeMessageListener(myEMMessageListener);
    }


    public List<EMMessage> getEMMessage(String username, String startMsgId, int pagesize) {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(username);
        //获取此会话的所有消息
        //List<EMMessage> messages = conversation.getAllMessages();
        //SDK初始化加载的聊天记录为20条，到顶时需要去DB里获取更多
        //获取startMsgId之前的pagesize条消息，此方法获取的messages SDK会自动存入到此会话中，APP中无需再次把获取到的messages添加到会话中
        List<EMMessage> messages = conversation.loadMoreMsgFromDB(startMsgId, pagesize);
        return messages;
    }


    public int unReadMessage(String username) {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(username);
        return conversation.getUnreadMsgCount();
    }


    public void changeUnReadMessage(String username) {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(username);
        //指定会话消息未读数清零
        conversation.markAllMessagesAsRead();
        //把一条消息置为已读
//        conversation.markMessageAsRead(messageId);
        //所有未读消息数清零
//        EMClient.getInstance().chatManager().markAllConversationsAsRead();
    }


    public int allMessageCount(String username) {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(username);
        //获取此会话在本地的所有的消息数量
        return conversation.getAllMsgCount();
        //        //如果只是获取当前在内存的消息数量，调用
        //        conversation.getAllMessages().size();
    }


    public Map<String, EMConversation> allConversation() {
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        return conversations;
    }


    public void deleteMessage(String username) {
        //删除和某个user会话，如果需要保留聊天记录，传false
        EMClient.getInstance().chatManager().deleteConversation(username, true);
        //删除当前会话的某条聊天记录
        //EMConversation conversation = EMClient.getInstance().chatManager().getConversation(username);
        //conversation.removeMessage(deleteMsg.msgId);
    }

    public void importMessage(List<EMMessage> msgs) {
        EMClient.getInstance().chatManager().importMessages(msgs);
    }

    public List<String> getAllFrind() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
//                    Logger.e(usernames.get(0));
//                } catch (HyphenateException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

        return null;
    }

    /**
     * 添加好友
     * @param toAddUsername
     * @param content
     * @param imFriendCallBack
     */
    public void addFriend(final String toAddUsername,final String content,final IMFriendCallBack imFriendCallBack)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //参数为要添加的好友的username和添加理由
                try {
                    EMClient.getInstance().contactManager().addContact(toAddUsername, content);
                    imFriendCallBack.sendSucess();
                } catch (HyphenateException e) {
                    imFriendCallBack.sendFail();
                }
            }
        }).start();

    }


    public void deleteFriend(String username)
    {
        try {
            EMClient.getInstance().contactManager().deleteContact(username);
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }

    public  void  agreeFriend(final String username,final IMFriendCallBack imFriendCallBack)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //参数为要添加的好友的username和添加理由
                try {
                    EMClient.getInstance().contactManager().acceptInvitation(username);
                    imFriendCallBack.sendSucess();
                } catch (HyphenateException e) {
                    imFriendCallBack.sendFail();
                }
            }
        }).start();

    }



    public  void setContactListener()
    {
        EMClient.getInstance().contactManager().setContactListener(new MyEMContactListener());
    }


    @Override
    public void onSuccess() {
        EventBus.getDefault().post(new IMRefreshEvent());
    }

    @Override
    public void onError(int code, String error) {
        EventBus.getDefault().post(new IMRefreshEvent());
    }

    @Override
    public void onProgress(int progress, String status) {
        EventBus.getDefault().post(new IMRefreshEvent());
    }
}
