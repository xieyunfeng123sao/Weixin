package com.ityun.weixin.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteQuery;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ityun.weixin.myapplication.base.App;
import com.ityun.weixin.myapplication.base.BaseActivity;
import com.ityun.weixin.myapplication.ui.adduser.AddUserActivity;
import com.ityun.weixin.myapplication.util.ImageLoadUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.wel_img)
    ImageView wel_img;

    //登录按钮
    @BindView(R.id.wel_login_button)
    Button wel_login_button;
    //注册按钮
    @BindView(R.id.wel_add_button)
    Button wel_add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        ImageLoadUtil.getInstance().getResouce(R.mipmap.we_2,wel_img);
        try {
            FileInputStream stream=new FileInputStream(new File(""));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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

    private void  isNeedLogin()
    {

    }


    @OnClick(R.id.wel_add_button)
    public  void  addUserOnclick()
    {
        Intent intent=new Intent(this, AddUserActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.wel_login_button)
    public   void   loginOnclick()
    {

    }


}
