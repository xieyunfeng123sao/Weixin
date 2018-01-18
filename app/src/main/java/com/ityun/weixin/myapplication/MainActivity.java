package com.ityun.weixin.myapplication;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import com.ityun.weixin.myapplication.base.App;
import com.ityun.weixin.myapplication.base.BaseActivity;
import com.ityun.weixin.myapplication.ui.AddUserActivity;
import com.ityun.weixin.myapplication.util.ImageLoadUtil;
import com.ityun.weixin.myapplication.view.LoadDialog;

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
        ImageLoadUtil.getInstance(App.context).getResouce(R.mipmap.we_2,wel_img);
//        wel_img.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent=new Intent(MainActivity.this, HomeActivity.class);
//                startActivity(intent);
//
//            }
//        },1000);

    }

    @Override
    protected void onResume() {
        super.onResume();

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
