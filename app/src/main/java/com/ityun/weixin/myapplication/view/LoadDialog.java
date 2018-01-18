package com.ityun.weixin.myapplication.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ityun.weixin.myapplication.R;

/**
 * Created by Administrator on 2018/1/18 0018.
 */

public class LoadDialog {

    private Context context;

    private Dialog dialog;

    private TextView loaddialog_text;

    private String  name;

    View view;

    public LoadDialog(Context context) {
        this.context = context;
    }

    public Dialog build() {
        dialog = new Dialog(context, R.style.loading_dialog);
        view = LayoutInflater.from(context).inflate(R.layout.load_dialog, null);
        loaddialog_text = view.findViewById(R.id.loaddialog_text);
        if(name!=null)
        {
            loaddialog_text.setText(name);
        }
        dialog.setCancelable(false);// 不可以用“返回键”取消
        dialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局

        return dialog;
    }
    public LoadDialog setText(CharSequence charSequence) {
        name=charSequence.toString();
        return this;
    }
    public LoadDialog setText(@StringRes int resid) {
        name=context.getResources().getString(resid);
        return this;
    }



    public void changeText(@StringRes int resid)
    {
        if(loaddialog_text!=null)
            loaddialog_text.setText(resid);
    }

    public void changeText(CharSequence charSequence)
    {
        if(loaddialog_text!=null)
            loaddialog_text.setText(charSequence);
    }


    public void show() {
        if (dialog != null)
            dialog.show();
    }

    public void dismiss() {
        if (dialog != null)
            dialog.dismiss();
    }


}
