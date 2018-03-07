package com.ityun.weixin.myapplication.ui.friend;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.base.BaseActivity;
import com.ityun.weixin.myapplication.bean.UserInfo;
import com.ityun.weixin.myapplication.ui.HomeActivity;
import com.ityun.weixin.myapplication.util.CacheUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/5 0005.
 */

public class AddFriendActivity extends BaseActivity {


    @BindView(R.id.intent_input_num)
    public EditText intent_input_num;

    @BindView(R.id.my_weixin_num_text)
    public  TextView my_weixin_num_text;

    private UserInfo userInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        ButterKnife.bind(this);
        userInfo= CacheUtils.getInstance(this).getUser();
        intent_input_num.setCursorVisible(false);
        String  showText=getResources().getString(R.string.my_weixinnum)+userInfo.getLoginName();
        my_weixin_num_text.setText(showText);
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.addfriend);
    }

    @OnClick(R.id.intent_input_num)
    public  void  intentOnClick()
    {
        Intent intent=new Intent(this, SearchUserActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
