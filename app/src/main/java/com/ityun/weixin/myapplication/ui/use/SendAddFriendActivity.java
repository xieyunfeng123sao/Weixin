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
import com.ityun.weixin.myapplication.bean.UserInfo;
import com.ityun.weixin.myapplication.im.IMModel;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by Administrator on 2018/3/12 0012.
 */

public class SendAddFriendActivity extends BaseActivity {

    @BindView(R.id.send_add_message)
    public EditText send_add_message;

    private UserInfo userInfo;

    private ImageView send_add_finish;

    private TextView send_add_msg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_add_friend);
        ButterKnife.bind(this);
        userInfo = (UserInfo) getIntent().getSerializableExtra("userinfo");
        send_add_message.setText("我是" + userInfo.getUserName());
        send_add_message.setSelection(userInfo.getUserName().length() + 2);
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
                IMModel.getInstance().sendAddFriendMessage(userInfo, send_add_message.getText().toString(), new IMModel.OnMessageListener() {
                    @Override
                    public void onSucess(BmobIMMessage message) {
                        Toast(R.string.send_sucess);
                        finish();
                    }
                    @Override
                    public void onFail(BmobException e) {
                        Toast(R.string.send_fail);
                        finish();
                    }
                });
            }
        });
    }


}
