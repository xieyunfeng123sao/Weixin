package com.ityun.weixin.myapplication;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.ityun.weixin.myapplication.base.App;
import com.ityun.weixin.myapplication.bean.User;
import com.ityun.weixin.myapplication.listener.BmobTableListener;
import com.ityun.weixin.myapplication.table.UserHelper;
import com.ityun.weixin.myapplication.util.ImageLoadUtil;
import com.squareup.leakcanary.RefWatcher;

import org.json.JSONArray;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.exception.BmobException;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.wel_img)
    ImageView wel_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        ImageLoadUtil.getInstance(App.context).getResouce(R.mipmap.we_1,wel_img);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = App.getRefWatcher(this);
        refWatcher.watch(this);
    }
}
