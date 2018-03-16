package com.ityun.weixin.myapplication.ui.use;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.base.BaseActivity;
import com.ityun.weixin.myapplication.bean.User;
import com.ityun.weixin.myapplication.util.ImageLoadUtil;
import com.ityun.weixin.myapplication.view.LoadDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/3/12 0012.
 */

public class UserDetailActivity extends BaseActivity {

    private User userInfo;


    @BindView(R.id.add_friend)
    public Button add_friend;

    @BindView(R.id.search_user_name)
    public TextView search_user_name;

    @BindView(R.id.video_friend)
    public Button video_friend;

    @BindView(R.id.search_user_img)
    public ImageView search_user_img;

    @BindView(R.id.search_user_num)
    public TextView search_user_num;

    private Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);
        userInfo = (User) getBundle().getSerializable("user");

        search_user_num.setText("微信号:" + userInfo.getMobilePhoneNumber());
        search_user_name.setText(userInfo.getNickname());
        ImageLoadUtil.getInstance().loadUrl(userInfo.getAvatar(), search_user_img);
        dialog = new LoadDialog(this).setText(R.string.adding).build();
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.detail_info);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detai_user_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.detail_menu) {
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @OnClick(R.id.add_friend)
    public void addFriendOnClick() {
        dialog.show();
        add_friend.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(UserDetailActivity.this, SendAddFriendActivity.class);
                intent.putExtra("userinfo", userInfo);
                startActivity(intent);
                dialog.dismiss();
            }
        }, 300);
    }

    @OnClick(R.id.video_friend)
    public void videoOnClick() {

    }
}
