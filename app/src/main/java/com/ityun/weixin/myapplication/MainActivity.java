package com.ityun.weixin.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.ityun.weixin.myapplication.base.BaseActivity;
import com.ityun.weixin.myapplication.bean.UserInfo;
import com.ityun.weixin.myapplication.ui.HomeActivity;
import com.ityun.weixin.myapplication.ui.adduser.AddUserActivity;
import com.ityun.weixin.myapplication.ui.login.LoginActivity;
import com.ityun.weixin.myapplication.ui.login.LoginContract;
import com.ityun.weixin.myapplication.ui.login.LoginPresenter;
import com.ityun.weixin.myapplication.util.CacheUtils;
import com.ityun.weixin.myapplication.util.ImageLoadUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements LoginContract.View {

    //测试能不能分享
    @BindView(R.id.wel_img)
    ImageView wel_img;

    //登录按钮
    @BindView(R.id.wel_login_button)
    Button wel_login_button;

    //注册按钮
    @BindView(R.id.wel_add_button)
    Button wel_add_button;

    private UserInfo user;

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        //加载图片
        ImageLoadUtil.getInstance().getResouce(R.mipmap.we_2, wel_img);
        //获取用户信息
        user = CacheUtils.getInstance(this).getCaCheUser();

        presenter=new LoginPresenter(this);
        if (user != null) {
            //如果有缓存的用户信息 就不显示登录和注册
            wel_login_button.setVisibility(View.GONE);
            wel_add_button.setVisibility(View.GONE);
            if(user.getPassword()!=null&&!user.getPassword().equals(""))
            {
                presenter.login(user);
                return;
            }
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
     }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void login(UserInfo user) {

    }


    /**
     * 跳转注册界面
     */
    @OnClick(R.id.wel_add_button)
    public void addUserOnclick() {
        Intent intent = new Intent(this, AddUserActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转登录界面
     */
    @OnClick(R.id.wel_login_button)
    public void loginOnclick() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void loginSucess(final UserInfo user) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CacheUtils.getInstance(MainActivity.this).saveUser(user);
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void loginFail() {

    }

    @Override
    public void loginError() {

    }
}
