package com.ityun.weixin.myapplication.util;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2018/5/31 0031.
 */

public class VoiceUtil {

    private static VoiceUtil getinstance;

    private MediaPlayer mPlayer;

    private ImageView stopImageView;

    public static VoiceUtil getInstance() {
        if (getinstance == null)
            getinstance = new VoiceUtil();
        return getinstance;
    }


    public void startPlay(Context context, ImageView imageView, int id, String path, final OnVoiceListener onVoiceListener) {
        if (path != null) {
            File file = new File(path);
            if (file.exists()) {
                try {
                    if (mPlayer != null) {
                        mPlayer.stop();
                        mPlayer.release();
                        mPlayer = null;
                    }
                    if (stopImageView != null) {
                        stopImageView.setImageResource(id);
                        AnimationDrawable animationDrawable = (AnimationDrawable) stopImageView.getDrawable();
                        animationDrawable.stop();
                        if (imageView == stopImageView) {
                            if (onVoiceListener != null) {
                                onVoiceListener.onEnd();
                                stopImageView = null;
                            }
                            return;
                        }
                    }

                    if (onVoiceListener != null)
                        onVoiceListener.onStart();
                    stopImageView = imageView;
                    mPlayer = new MediaPlayer();
                    FileInputStream fis = new FileInputStream(new File(path));
                    mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            if (onVoiceListener != null)
                                onVoiceListener.onEnd();
                        }
                    });
                    mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                        @Override
                        public boolean onError(MediaPlayer mp, int what, int extra) {
                            if (onVoiceListener != null)
                                onVoiceListener.onEnd();
                            return false;
                        }
                    });
                    mPlayer.setDataSource(fis.getFD());
                    mPlayer.prepare();
                    mPlayer.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                if (onVoiceListener != null)
                    onVoiceListener.onEnd();
                Toast.makeText(context, "", Toast.LENGTH_LONG).show();
            }
        }
    }


    public void stopVoice() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    public interface OnVoiceListener {
        void onStart();

        void onEnd();
    }
}
