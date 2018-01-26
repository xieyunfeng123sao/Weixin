package com.ityun.weixin.myapplication.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.base.App;
import com.ityun.weixin.myapplication.base.BaseActivity;

/**
 * Created by Administrator on 2018/1/17 0017.
 */

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void onStart() {
        super.onStart();
        App.getInstance().finishOrActivity(this);
    }
}
