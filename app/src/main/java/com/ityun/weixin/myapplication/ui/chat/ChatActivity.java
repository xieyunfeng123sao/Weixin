package com.ityun.weixin.myapplication.ui.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 77367 on 2018/10/17.
 */

public class ChatActivity extends BaseActivity implements ChatContract.View {

    @BindView(R.id.sw_refresh)
    SwipeRefreshLayout sw_refresh;

    @BindView(R.id.rc_view)
    RecyclerView rc_view;

    @BindView(R.id.layout_record)
    RelativeLayout layout_record;

    @BindView(R.id.iv_record)
    ImageView iv_record;

    @BindView(R.id.tv_voice_tips)
    TextView tv_voice_tips;

    @BindView(R.id.btn_chat_voice)
    Button btn_chat_voice;

    @BindView(R.id.btn_chat_keyboard)
    Button btn_chat_keyboard;

    @BindView(R.id.edit_msg)
    EditText edit_msg;

    @BindView(R.id.btn_speak)
    Button btn_speak;

    @BindView(R.id.btn_chat_add)
    Button btn_chat_add;

    @BindView(R.id.btn_chat_send)
    Button btn_chat_send;

    @BindView(R.id.include_chat_add)
    LinearLayout include_chat_add;

    @BindView(R.id.tv_picture)
    TextView tv_picture;

    @BindView(R.id.tv_camera)
    TextView tv_camera;

    @BindView(R.id.tv_location)
    TextView tv_location;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
    }

}
