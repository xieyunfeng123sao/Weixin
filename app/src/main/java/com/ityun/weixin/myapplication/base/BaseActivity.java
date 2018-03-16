package com.ityun.weixin.myapplication.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ityun.weixin.myapplication.model.ErrorHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Administrator on 2018/1/17 0017.
 */

public class BaseActivity extends AppCompatActivity implements ErrorHelper {


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
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }


    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
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

    public void startActivity(Class<? extends Activity> target, Bundle bundle,boolean finish)
    {
        Intent intent = new Intent();
        intent.setClass(this, target);
        if (bundle != null)
            intent.putExtra(getPackageName(), bundle);
        startActivity(intent);
        if (finish)
            finish();
    }
    public Bundle getBundle() {
        if (getIntent() != null && getIntent().hasExtra(getPackageName()))
            return getIntent().getBundleExtra(getPackageName());
        else
            return null;
    }

    @Subscribe
    public void onEvent(Boolean empty){

    }
  
}
