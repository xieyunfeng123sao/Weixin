package com.ityun.weixin.myapplication.ui.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.base.BaseActivity;

/**
 * Created by Administrator on 2018/3/22 0022.
 */

public class ChatActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
    }
}
