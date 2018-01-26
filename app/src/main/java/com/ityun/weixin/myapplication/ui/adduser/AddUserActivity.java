package com.ityun.weixin.myapplication.ui.adduser;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.base.BaseActivity;
import com.ityun.weixin.myapplication.bean.User;
import com.ityun.weixin.myapplication.ui.HomeActivity;
import com.ityun.weixin.myapplication.ui.album.AlbumActivity;
import com.ityun.weixin.myapplication.util.CacheUtils;
import com.ityun.weixin.myapplication.util.DecideUtil;
import com.ityun.weixin.myapplication.view.LoadDialog;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by Administrator on 2018/1/17.
 */

public class AddUserActivity extends BaseActivity implements AddUserContract.View {

    //返回
    @BindView(R.id.add_goback)
    ImageView add_goback;
    //昵称
    @BindView(R.id.add_input_nickname)
    EditText add_input_nickname;

    //昵称下面的线条
    @BindView(R.id.nickname_line)
    View nickname_line;
    //手机号
    @BindView(R.id.add_input_phonenum)
    EditText add_input_phonenum;
    //清空手机号
    @BindView(R.id.clear_input_num)
    ImageView clear_input_num;
    //手机号下面的线条
    @BindView(R.id.phonenum_line)
    View phonenum_line;
    //密码
    @BindView(R.id.add_input_password)
    EditText add_input_password;

    @BindView(R.id.eye_password)
    ImageView eye_password;

    @BindView(R.id.password_line)
    View password_line;
    //注册
    @BindView(R.id.add_user_button)
    Button add_user_button;

    @BindView(R.id.add_user_img_rl)
    RelativeLayout add_user_img_rl;

    @BindView(R.id.add_user_img)
    ImageView add_user_img;

    //密码是否可见
    private boolean isLook;

    Dialog dialog;

    private AddUserPresenter presenter;

    public int GET_USER_IMG = 10;

    private String path = "";
    User user;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        ButterKnife.bind(this);
        initListener();
        presenter = new AddUserPresenter(this);
    }

    @OnClick(R.id.add_goback)
    public void goBack() {
        finish();
    }

    /**
     * 清除手机号
     */
    @OnClick(R.id.clear_input_num)
    public void clearNum() {
        add_input_phonenum.setText("");
    }

    /**
     * 密码是否可见
     */
    @OnClick(R.id.eye_password)
    public void lookPassword() {
        // 是否可以查看密码
        if (isLook) {
            add_input_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            eye_password.setImageResource(R.mipmap.eye_yes);
        } else {
            add_input_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            eye_password.setImageResource(R.mipmap.eye_no);
        }
        //光标移动到最后一位
        if (!TextUtils.isEmpty(add_input_password.getText().toString()))
            add_input_password.setSelection(add_input_password.getText().toString().length());
        isLook = !isLook;
    }

    @OnClick(R.id.add_user_button)
    public void addUser() {
        if (!DecideUtil.isMobile(add_input_phonenum.getText().toString())) {
            Toast(R.string.error_num);
            return;
        }
        dialog = new LoadDialog(this).setText(R.string.adding_user).build();
        dialog.show();
        user = new User();
        user.setUserName(add_input_nickname.getText().toString());
        user.setLoginName(add_input_phonenum.getText().toString());
        user.setPassword(add_input_password.getText().toString());
        presenter.selectUser(user.getLoginName());
    }

    @OnClick({R.id.add_user_img_rl, R.id.add_user_img})
    public void addUserHeadPortrait(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.add_user_img_rl:
                intent = new Intent(AddUserActivity.this, AlbumActivity.class);
                startActivityForResult(intent, GET_USER_IMG);
                break;
            case R.id.add_user_img:
                intent = new Intent(AddUserActivity.this, AlbumActivity.class);
                startActivityForResult(intent, GET_USER_IMG);
                break;
            default:
                break;
        }
    }


    /**
     * 判断Edittext是否有焦点
     * 判断文字的长度
     */
    private void initListener() {
        add_input_nickname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                booleanBackGroud(nickname_line, hasFocus);
            }
        });
        add_input_phonenum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                booleanBackGroud(phonenum_line, hasFocus);
            }
        });
        add_input_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                booleanBackGroud(password_line, hasFocus);
            }
        });

        add_input_nickname.addTextChangedListener(new TextWatcher() {
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


        add_input_phonenum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString())) {
                    clear_input_num.setVisibility(View.GONE);
                } else {
                    clear_input_num.setVisibility(View.VISIBLE);
                }
                booleanCanAdd();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        add_input_password.addTextChangedListener(new TextWatcher() {
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
        String nickName = add_input_nickname.getText().toString();
        String phoneNum = add_input_phonenum.getText().toString();
        String password = add_input_password.getText().toString();
        if (TextUtils.isEmpty(nickName)) {
            add_user_button.setEnabled(false);
            return;
        }
        if (TextUtils.isEmpty(phoneNum)) {
            add_user_button.setEnabled(false);
            return;
        }
        if (TextUtils.isEmpty(password)) {
            add_user_button.setEnabled(false);
            return;
        }
        add_user_button.setEnabled(true);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_USER_IMG) {
            if (resultCode == RESULT_OK) {
                path = data.getStringExtra("image");
                Glide.with(AddUserActivity.this).load(new File(path)).into(add_user_img);
                add_user_img.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void selectSucess(final Object object) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONArray jsonArray = (JSONArray) object;
                if (jsonArray.length() == 0) {
                    //这个账号没有注册过
                    if (path == null || !new File(path).exists()) {
                        presenter.addUser(user);
                    } else {
                        presenter.addImage(path);
                    }
                } else {
                    //这个账号已经存在了
                    dialog.dismiss();
                    Toast(R.string.error_has_user);
                }
            }
        });

    }

    @Override
    public void selectFail() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast(R.string.error_add_user);
                dialog.dismiss();
            }
        });

    }

    @Override
    public void uploadSucess(final String url) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                user.setUserPic(url);
                presenter.addUser(user);
            }
        });
    }

    @Override
    public void uploadFail(final BmobException e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                user.setUserPic("");
                presenter.addUser(user);
            }
        });
    }

    @Override
    public void addSucess(final Object object) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                user.setId(object.toString());
                CacheUtils.getInstance(AddUserActivity.this).saveUser(user);
                Intent intent = new Intent(AddUserActivity.this, HomeActivity.class);
                intent.putExtra("userInfo",user);
                startActivity(intent);
                dialog.dismiss();
            }
        });
    }

    @Override
    public void addError(BmobException e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast(R.string.error_add_user);
                dialog.dismiss();
            }
        });

    }
}
