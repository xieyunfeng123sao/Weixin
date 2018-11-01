package com.ityun.weixin.myapplication.ui.chat.adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.base.BaseViewHolder;
import com.ityun.weixin.myapplication.util.ImageLoadUtil;
import com.ityun.weixin.myapplication.util.SpUtil;
import com.ityun.weixin.myapplication.util.VoiceUtil;

import java.io.File;
import java.text.SimpleDateFormat;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/5/28 0028.
 */

public class SendVoiceHolder extends BaseViewHolder<EMMessage> {

    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.iv_avatar)
    ImageView iv_avatar;

    @BindView(R.id.layout_voice)
    LinearLayout layout_voice;

    @BindView(R.id.iv_voice)
    ImageView iv_voice;

    @BindView(R.id.tv_voice_length)
    TextView tv_voice_length;

    @BindView(R.id.iv_fail_resend)
    ImageView iv_fail_resend;

    @BindView(R.id.progress_load)
    ProgressBar progress_load;


    AnimationDrawable animationDrawable;


    public SendVoiceHolder(Context context, ViewGroup root, int layoutRes) {
        super(context, root, layoutRes);
        this.context = context;
    }

    @Override
    public void bindData(final EMMessage emMessage) {

        if (emMessage.status() == EMMessage.Status.SUCCESS) {
            progress_load.setVisibility(View.INVISIBLE);
            iv_fail_resend.setVisibility(View.INVISIBLE);
        } else if (emMessage.status() == EMMessage.Status.FAIL) {
            progress_load.setVisibility(View.INVISIBLE);
            iv_fail_resend.setVisibility(View.VISIBLE);
        } else {
            progress_load.setVisibility(View.VISIBLE);
            iv_fail_resend.setVisibility(View.INVISIBLE);
        }

        ImageLoadUtil.getInstance().loadUrl(SpUtil.getUser().getAvatar(),iv_avatar);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
        String time = dateFormat.format(emMessage.getMsgTime());
        EMVoiceMessageBody voiceMessageBody = (EMVoiceMessageBody) emMessage.getBody();
        tv_time.setText(time);
        tv_voice_length.setText(voiceMessageBody.getLength() + "\"");

        layout_voice.setOnClickListener(v -> VoiceUtil.getInstance().startPlay(context, iv_voice,R.drawable.chat_right_voice,voiceMessageBody.getLocalUrl(), new VoiceUtil.OnVoiceListener() {
            @Override
            public void onStart() {
                iv_voice.setImageResource(R.drawable.chat_right_voice);
                animationDrawable = ((AnimationDrawable) iv_voice.getDrawable());
                animationDrawable.start();
            }

            @Override
            public void onEnd() {
                iv_voice.setImageResource(R.drawable.chat_right_voice);
                AnimationDrawable animationDrawable = (AnimationDrawable) iv_voice.getDrawable();
                animationDrawable.stop();
            }
        }));
        bindClick(layout_voice);
        bindOnRestart(iv_fail_resend);
    }

    public void showTime(boolean isShow) {
        tv_time.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

}
