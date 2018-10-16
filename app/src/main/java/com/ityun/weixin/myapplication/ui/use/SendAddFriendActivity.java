package com.ityun.weixin.myapplication.ui.use;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.base.BaseActivity;
import com.ityun.weixin.myapplication.bean.NewFriend;
import com.ityun.weixin.myapplication.bean.User;
import com.ityun.weixin.myapplication.dao.NewFriendUtil;
import com.ityun.weixin.myapplication.im.IMModel;
import com.ityun.weixin.myapplication.listener.IMFriendCallBack;
import com.ityun.weixin.myapplication.model.UserModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/12 0012.
 */

public class SendAddFriendActivity extends BaseActivity {

    @BindView(R.id.send_add_message)
    public EditText send_add_message;

    private User userInfo;

    private ImageView send_add_finish;

    private TextView send_add_msg;

    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_add_friend);
        ButterKnife.bind(this);
        user = UserModel.getInstance().getUser();
        userInfo = (User) getIntent().getSerializableExtra("userinfo");
        send_add_message.setText("我是" + user.getNickname());
        send_add_message.setSelection(user.getNickname().length() + 2);
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar_send);
        send_add_finish = actionBar.getCustomView().findViewById(R.id.send_add_finish);
        send_add_msg = actionBar.getCustomView().findViewById(R.id.send_add_msg);
        send_add_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        send_add_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAddFriend();
            }
        });
    }


    //发送添加好友
    private void sendAddFriend() {
        IMModel.getInstance().addFriend(userInfo.getUsername(), send_add_message.getText().toString(), new IMFriendCallBack() {
            @Override
            public void sendSucess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast(R.string.send_sucess);
                        finish();
                    }
                });
            }

            @Override
            public void sendFail() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast(R.string.send_fail);
                    }
                });
            }
        });
    }


}
