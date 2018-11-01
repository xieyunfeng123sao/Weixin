package com.ityun.weixin.myapplication.ui.chat.adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.base.BaseViewHolder;
import com.ityun.weixin.myapplication.util.VoiceUtil;

import java.io.File;
import java.text.SimpleDateFormat;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/5/28 0028.
 */

public class ReceiveVoiceHolder extends BaseViewHolder<EMMessage> {

    @BindView(R.id.tv_time)
    public TextView tv_time;

    @BindView(R.id.iv_avatar)
    public ImageView iv_avatar;

    @BindView(R.id.layout_voice)
    public LinearLayout layout_voice;
    @BindView(R.id.iv_voice)
    public ImageView iv_voice;
//    @BindView(R.id.open_state)
//    public View open_state;

    @BindView(R.id.tv_voice_length)
    public TextView tv_voice_length;

    AnimationDrawable animationDrawable;


    public ReceiveVoiceHolder(Context context, ViewGroup root, int layoutRes) {
        super(context, root, layoutRes);
        this.context = context;
    }

    @Override
    public void bindData(final EMMessage emMessage) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
        String time = dateFormat.format(emMessage.getMsgTime());
        tv_time.setText(time);
        EMVoiceMessageBody voiceMessageBody= (EMVoiceMessageBody) emMessage.getBody();
//        voiceMessageBody.getLocalUrl();
//        File file = new File(voiceMessageBody.getLocalUrl());
//        if (file.exists()) {
//            double voiceLength = MediaUtil.getVoiceLength(imMessage.getVoicePath());
//            tv_voice_length.setText((int) (voiceLength / 1000) + "\"");
//        }
//        if (imMessage.getVoiceHasOpen() == 1) {
//            open_state.setVisibility(View.GONE);
//        } else {
//            open_state.setVisibility(View.VISIBLE);
//        }
//        iv_avatar.setTextString(imMessage.getMemberName());
        layout_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VoiceUtil.getInstance().startPlay(context, iv_voice,R.drawable.chat_left_voice,voiceMessageBody.getRemoteUrl(), new VoiceUtil.OnVoiceListener() {
                    @Override
                    public void onStart() {
                        iv_voice.setImageResource(R.drawable.chat_left_voice);
                        animationDrawable = ((AnimationDrawable) iv_voice.getDrawable());
                        animationDrawable.start();
//                        imMessage.setVoiceHasOpen(1);
//                        IMUtil.getInstance().upDataVoiceOpen(imMessage);
//                        open_state.setVisibility(View.GONE);
                    }
                    @Override
                    public void onEnd() {
                        iv_voice.setImageResource(R.drawable.chat_left_voice);
                        AnimationDrawable animationDrawable = (AnimationDrawable) iv_voice.getDrawable();
                        animationDrawable.stop();
                    }
                });
            }
        });
        bindClick(layout_voice);
    }




    public void showTime(boolean isShow) {
        tv_time.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }
}
