package com.ityun.weixin.myapplication.ui.login;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.base.BaseActivity;
import com.ityun.weixin.myapplication.bean.User;
import com.ityun.weixin.myapplication.ui.HomeActivity;
import com.ityun.weixin.myapplication.ui.adduser.AddUserActivity;
import com.ityun.weixin.myapplication.util.CacheUtils;
import com.ityun.weixin.myapplication.util.DecideUtil;
import com.ityun.weixin.myapplication.view.LoadDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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


    @BindView(R.id.login_user_password_line)
    public View login_user_password_line;

    @BindView(R.id.login_user_password_look)
    public ImageView login_user_password_look;

    @BindView(R.id.login_user_button)
    public Button login_user_button;

    private LoginContract.Presenter presenter;

    private boolean isShowPsd;

    Dialog dialog;

    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        presenter = new LoginPresenter(this);
        initListener();
    }


    public void initListener() {
        login_user_num.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                booleanBackGroud(login_user_num_line, hasFocus);
            }
        });

        login_user_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                booleanBackGroud(login_user_password_line, hasFocus);
            }
        });
        login_user_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString())) {
                    login_user_num_delete.setVisibility(View.GONE);
                } else {
                    login_user_num_delete.setVisibility(View.VISIBLE);
                }
                booleanCanAdd();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        login_user_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                booleanCanAdd();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 判断是否可以点击
     */
    private void booleanCanAdd() {
        String phoneNum = login_user_num.getText().toString();
        String password = login_user_password.getText().toString();

        if (TextUtils.isEmpty(phoneNum)) {
            login_user_button.setEnabled(false);
            return;
        }
        if (TextUtils.isEmpty(password)) {
            login_user_button.setEnabled(false);
            return;
        }
        login_user_button.setEnabled(true);
    }

    /**
     * 下划线的颜色
     *
     * @param view
     * @param hasFocus
     */
    private void booleanBackGroud(View view, boolean hasFocus) {
        if (hasFocus) {
            view.setBackgroundResource(R.color.main_color);
        } else {
            view.setBackgroundResource(R.color.txt_color);
        }
    }


    @OnClick({R.id.add_goback, R.id.login_user_num_delete,R.id.login_user_password_look,R.id.login_user_button})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.add_goback:
                //后退
                finish();
                break;
            case R.id.login_user_num_delete:
                //消除用户账号
                login_user_num.setText("");
                break;
            case R.id.login_user_password_look:
                //密码是否可见
                if (isShowPsd) {
                    login_user_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    login_user_password_look.setImageResource(R.mipmap.eye_yes);
                } else {
                    login_user_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    login_user_password_look.setImageResource(R.mipmap.eye_no);
                }
                if (!TextUtils.isEmpty(login_user_password.getText().toString()))
                    login_user_password.setSelection(login_user_password.getText().toString().length());
                isShowPsd = !isShowPsd;
                break;
            case R.id.login_user_button:
                //登录
                if (!DecideUtil.isMobile(login_user_num.getText().toString())) {
                    Toast(R.string.error_num);
                    return;
                }
                dialog = new LoadDialog(this).setText(R.string.adding_login).build();
                dialog.show();
                user = new User();

                user.setLoginName(login_user_num.getText().toString());
                user.setPassword(login_user_password.getText().toString());
                presenter.login(user);
                break;
            default:
                break;
        }
    }


    @Override
    public void loginSucess(final User callBackuser) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CacheUtils.getInstance(LoginActivity.this).saveUser(callBackuser);
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
    }

    @Override
    public void loginFail() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast(R.string.error_login);
                dialog.dismiss();
            }
        });
    }

    @Override
    public void loginError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast(R.string.error);
                dialog.dismiss();
            }
        });
    }
}
