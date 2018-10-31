package com.ityun.weixin.myapplication.ui.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.base.BaseActivity;
import com.ityun.weixin.myapplication.bean.User;
import com.ityun.weixin.myapplication.event.IMRefreshEvent;
import com.ityun.weixin.myapplication.im.IMMessage;
import com.ityun.weixin.myapplication.im.IMModel;
import com.ityun.weixin.myapplication.ui.chat.adapter.ChatMessageAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 77367 on 2018/10/17.
 */

public class ChatActivity extends BaseActivity implements ChatContract.View, TextWatcher {

    /**
     * 刷新
     */
    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout sw_refresh;

    /**
     * 消息列表
     */
    @BindView(R.id.rc_view)
    RecyclerView rc_view;

    /**
     * 音量布局
     */
    @BindView(R.id.layout_record)
    RelativeLayout layout_record;

    /**
     * 音量图标
     */
    @BindView(R.id.iv_record)
    ImageView iv_record;

    /**
     * 音量文字
     */
    @BindView(R.id.tv_voice_tips)
    TextView tv_voice_tips;

    /**
     * 语音button
     */
    @BindView(R.id.btn_chat_voice)
    Button btn_chat_voice;

    /**
     * 键盘button
     */
    @BindView(R.id.btn_chat_keyboard)
    Button btn_chat_keyboard;


    //文字编辑
    @BindView(R.id.edit_msg)
    EditText edit_msg;

    /**
     * 语音按键
     */
    @BindView(R.id.btn_speak)
    Button btn_speak;

    /**
     * 其他功能的显示
     */
    @BindView(R.id.btn_chat_add)
    Button btn_chat_add;

    /**
     * 文字的发送
     */
    @BindView(R.id.btn_chat_send)
    Button btn_chat_send;

    /**
     * 其他功能include布局
     */
    @BindView(R.id.include_chat_add)
    LinearLayout include_chat_add;

    /**
     * 图片
     */
    @BindView(R.id.tv_picture)
    TextView tv_picture;

    /**
     * 视频
     */
    @BindView(R.id.tv_camera)
    TextView tv_camera;

    /**
     * 位置
     */
    @BindView(R.id.tv_location)
    TextView tv_location;

    ChatContract.Presenter presenter;

    private User friend;

    private List<EMMessage> messageList = new ArrayList<>();

    private ChatMessageAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        init();
    }

    private void init() {
        presenter = new ChatPresenter(this);
        edit_msg.addTextChangedListener(this);
        friend = (User) getBundle().getSerializable("friend");
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        if (friend != null) {
            mActionBar.setTitle(friend.getNickname());
            List<EMMessage> emMessages = IMModel.getInstance().getEMMessage(friend.getUsername());
            if (emMessages != null) {
                messageList.addAll(emMessages);
            }
        }
        adapter = new ChatMessageAdapter(this);
        adapter.setData(messageList);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rc_view.setLayoutManager(manager);
        rc_view.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @OnClick({R.id.btn_chat_send, R.id.btn_chat_add, R.id.btn_chat_voice, R.id.btn_chat_keyboard})
    public void viewOnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_chat_send:
                //发送文本消息
                sendTXT();
                //发送完成后清空状态
                hideSoftInput(edit_msg);
                edit_msg.setText("");
                break;
            case R.id.btn_chat_add:
                //其他功能的添加
                if (include_chat_add.getVisibility() == View.VISIBLE) {
                    include_chat_add.setVisibility(View.GONE);
                } else {
                    include_chat_add.setVisibility(View.VISIBLE);
                }
                hideSoftInput(edit_msg);
                break;
            case R.id.btn_chat_voice:
                //切换到语音发送
                include_chat_add.setVisibility(View.GONE);
                hideSoftInput(edit_msg);
                edit_msg.setText("");
                edit_msg.setVisibility(View.GONE);
                btn_speak.setVisibility(View.VISIBLE);
                btn_chat_voice.setVisibility(View.GONE);
                btn_chat_keyboard.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_chat_keyboard:
                //切换到文字的发送
                include_chat_add.setVisibility(View.GONE);
                edit_msg.setText("");
                hideSoftInput(edit_msg);
                edit_msg.setVisibility(View.VISIBLE);
                btn_speak.setVisibility(View.GONE);
                btn_chat_voice.setVisibility(View.VISIBLE);
                btn_chat_keyboard.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 发送文字
     */
    private void sendTXT() {
        if (friend != null) {
            IMMessage imMessage = new IMMessage();
            imMessage.setChatType(0);
            imMessage.setFriendId(friend.getUsername());
            imMessage.setContent(edit_msg.getText().toString());
            EMMessage emMessage = presenter.sendMessage(imMessage);
            messageList.add(emMessage);
            adapter.notifyDataSetChanged();
        }
    }


    /**
     * 刷新message
     *
     * @param event
     */
    @Subscribe
    public void messageRefru(IMRefreshEvent event) {
        int position = 0;
        for (EMMessage emMessage : messageList) {
            if (emMessage.getMsgId().equals(event.emMessage.getMsgId())) {
                break;
            }
            position++;
        }
        messageList.set(position, event.emMessage);
        adapter.notifyItemChanged(position);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (TextUtils.isEmpty(charSequence.toString())) {
            btn_chat_send.setVisibility(View.GONE);
            btn_chat_add.setVisibility(View.VISIBLE);
        } else {
            btn_chat_send.setVisibility(View.VISIBLE);
            btn_chat_add.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
