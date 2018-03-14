package com.ityun.weixin.myapplication.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2018/1/17 0017.
 */

public class BaseActivity extends AppCompatActivity {


    protected ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getInstance().addActivity(this);
        actionBar=getSupportActionBar();
        initActionBar();
    }

    protected  void  initActionBar()
    {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getInstance().destoryActivity(this);
    }


    protected   void Toast(final @StringRes int resId)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this,resId,Toast.LENGTH_SHORT).show();
            }
        });

    }
  
}
