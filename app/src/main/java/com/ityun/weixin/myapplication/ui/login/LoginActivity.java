package com.ityun.weixin.myapplication.ui.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.base.BaseActivity;
import com.ityun.weixin.myapplication.bean.User;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/17.
 */

public class LoginActivity extends BaseActivity implements LoginContract.View {


    @BindView(R.id.add_goback)
    public ImageView add_goback;

    @BindView(R.id.login_user_num)
    public EditText login_user_num;

    @BindView(R.id.login_user_num_delete)
    public ImageView login_user_num_delete;

    @BindView(R.id.login_user_num_line)
    public View login_user_num_line;

    @BindView(R.id.login_user_password)
    public EditText login_user_password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void loginSucess(User user) {

    }

    @Override
    public void loginFail() {

    }

    @Override
    public void loginError() {

    }
}
