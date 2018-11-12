package com.ityun.weixin.myapplication.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phontolibrary.ZxingQrCodeUtil;
import com.google.gson.Gson;
import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.bean.User;
import com.ityun.weixin.myapplication.util.ImageLoadUtil;
import com.ityun.weixin.myapplication.util.SpUtil;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/2/12 0012.
 */

public class MeFragment extends Fragment {


    @BindView(R.id.user_img)
    public ImageView user_img;

    @BindView(R.id.user_nikename)
    public TextView user_nikename;

    @BindView(R.id.user_num)
    public TextView user_num;

    @BindView(R.id.user_num_code)
    public ImageView user_num_code;

    private User user;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }


    private void initData() {
        user = SpUtil.getUser();
        ImageLoadUtil.getInstance().loadUrl(user.getAvatar(), user_img);
        user_nikename.setText(user.getNickname());
        user_num.setText("微信号：" + user.getUsername());

        Gson gson = new Gson();
        String json = gson.toJson(user);
        Log.e("insert", json+"===============MeFragment=================");
        Bitmap bitmap = ZxingQrCodeUtil.createQRcodeImage(user.getUsername(), 100, 100, getResources().getColor(R.color.light_txt_color));
        if (bitmap != null) {
            ImageLoadUtil.getInstance().loadBitmap(bitmap, user_num_code);
        }
    }

    @Subscribe
    public void meEvent() {

    }
}
