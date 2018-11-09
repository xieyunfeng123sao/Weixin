package com.ityun.weixin.myapplication.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.widget.ImageView;

import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.conn.Constant;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

public class MediaUtil {

    private MediaRecorder mediaRecorder;

    private String path;

    private String paths = path;

    private File saveFilePath;

    // 所录音的文件
    String[] listFile = null;

    private String name;

    // 用于语音播放
    private MediaPlayer mPlayer = null;

    private Activity activity;

    public boolean isPrepared = false;

    public MediaUtil(Activity context) {
        this.activity = context;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            path = Constant.basePath;
            File files = new File(path);
            if (!files.exists()) {
                files.mkdir();
            }
            listFile = files.list();
        }

    }

    private Timer timer;


    @SuppressLint("SimpleDateFormat")
    public String startRecorder() {
        isPrepared = false;
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setMaxDuration(10500);
        // 从麦克风源进行录音
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        // 设置输出格式
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
        // 设置编码格式
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        paths = path + "/" + new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis()) + ".amr";
        name = new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis());
        saveFilePath = new File(paths);
        mediaRecorder.setOutputFile(saveFilePath.getAbsolutePath());
        try {
            saveFilePath.createNewFile();
            mediaRecorder.prepare();
            try {
                // 开始录音
                mediaRecorder.start();

            } catch (Exception e) {
                // TODO: handle exception
            }

            isPrepared = true;
            if (timer != null) {
                timer.cancel();
                // 一定设置为null，否则定时器不会被回收
                timer = null;
            }
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    int num = getVoiceLevel(7);
                    if (audioStateChangeListener != null) {
                        audioStateChangeListener.wellPrepared(num);
                    }
                }
            }, 0, 300);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return paths;
    }

    public String getName() {
        return name;
    }

    public int getLong() {
        try {
            mPlayer = new MediaPlayer();
            mPlayer.reset();
            mPlayer.setDataSource(paths);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mPlayer.getDuration();
    }


    public int getPathLong(String path) {

        try {
            mPlayer = new MediaPlayer();
            mPlayer.reset();
            mPlayer.setDataSource(path);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mPlayer.getDuration();

    }

    public int getSize() {
        File file = new File(paths);
        int size = 0;
        try {
            size = (int) getFileSize(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    public int getPathSize(String path) {
        if (path != null) {
            File file = new File(path);
            int size = 0;
            try {
                size = (int) getFileSize(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return size / 1000;
        }

        return 0;

    }

    @SuppressWarnings("resource")
    private static long getFileSize(File file)
            throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
        }
        return size;
    }

    public void stopRecorder() {
        if (saveFilePath != null && saveFilePath.exists()) {
            if (mediaRecorder != null) {
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;
            }
            if (timer != null) {
                timer.cancel();
                // 一定设置为null，否则定时器不会被回收
                timer = null;
            }
        }
    }


    public void cancel() {
        stopRecorder();
        if (path != null) {
            File file = new File(path);
            file.delete();
            path = Constant.basePath;
        }
    }

    public int updateMicStatus() {
        if (mediaRecorder != null) {
            // int vuSize = 10 * mMediaRecorder.getMaxAmplitude() / 32768;
            int ratio = mediaRecorder.getMaxAmplitude() / 600;
            int db = 0;// 分贝
            if (ratio > 1)
                db = (int) (20 * Math.log10(ratio));
            if (db / 4 == 0) {
                return 0;
            } else if (db / 4 == 1) {
                return 1;
            } else if (db / 4 == 2) {
                return 2;
            } else if (db / 4 == 3) {
                return 3;
            } else if (db / 4 == 4) {
                return 4;
            } else {
                return 5;
            }
        }
        return 0;
    }

//    public void startPlay(String pathRecorder, final OnVoiceListener onVoiceListener) {

//        if (pathRecorder != null) {
//
//            File file = new File(pathRecorder);
//
//            if (file.exists()) {
//                try {
//                    if (mPlayer != null) {
//                        mPlayer.stop();
//                        mPlayer.release();
//                        mPlayer = null;
//                    }
//                    if (onVoiceListener != null)
//                        onVoiceListener.onStart();
//                    mPlayer = new MediaPlayer();
//                    FileInputStream fis = new FileInputStream(new File(pathRecorder));
//                    mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                    mPlayer.setOnCompletionListener(new OnCompletionListener() {
//                        @Override
//                        public void onCompletion(MediaPlayer mp) {
//                            Log.e("MediaUtil", "===end==");
//                            if (onVoiceListener != null)
//                                onVoiceListener.onEnd();
//                        }
//                    });
//                    mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
//                        @Override
//                        public boolean onError(MediaPlayer mp, int what, int extra) {
//                            if (onVoiceListener != null)
//                                onVoiceListener.onEnd();
//                            return false;
//                        }
//                    });
//                    mPlayer.setDataSource(fis.getFD());
//                    mPlayer.prepare();
//                    mPlayer.start();
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                if (onVoiceListener != null)
//                    onVoiceListener.onEnd();
//                Toast.makeText(context, "", Toast.LENGTH_LONG).show();
//
//            }
//
//        }
//    }

    /**
     * 把字节数组保存为一个文件
     *
     * @EditTime
     */
    public static void getFileFromBytes(byte[] b, String outputFile) {
        BufferedOutputStream stream = null;
        File file = null;
        try {
            file = new File(outputFile);
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 更新音量
     *
     * @param maxLevel
     * @return
     */
    public int getVoiceLevel(int maxLevel) {
        if (mediaRecorder == null)
            return 1;
        if (isPrepared) {
            try {
                // 振幅范围mediaRecorder.getMaxAmplitude():1-32767
                return maxLevel * mediaRecorder.getMaxAmplitude() / 32768 + 1;
            } catch (Exception e) {
            }
        }
        return 1;
    }


    public interface AudioStateChangeListener {
        void wellPrepared(int i);
    }

    public AudioStateChangeListener audioStateChangeListener;

    public void setOnAudioStateChangeListener(ImageView view) {
        audioStateChangeListener = new AudioStateChangeListener() {
            @Override
            public void wellPrepared(int i) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (i) {
                            case 0:
                                view.setImageResource(R.mipmap.chat_icon_voice1);
                                break;
                            case 1:
                                view.setImageResource(R.mipmap.chat_icon_voice2);
                                break;
                            case 2:
                                view.setImageResource(R.mipmap.chat_icon_voice3);
                                break;
                            case 3:
                                view.setImageResource(R.mipmap.chat_icon_voice4);
                                break;
                            case 4:
                                view.setImageResource(R.mipmap.chat_icon_voice5);
                                break;
                            case 5:
                                view.setImageResource(R.mipmap.chat_icon_voice6);
                                break;
                            case 6:
                                view.setImageResource(R.mipmap.chat_icon_voice6);
                                break;
                            case 7:
                                view.setImageResource(R.mipmap.chat_icon_voice6);
                                break;
                        }
                    }
                });
            }
        };
    }


    public static double getVoiceLength(String path) {
        MediaPlayer player = new MediaPlayer();
        try {
            player.setDataSource(path);  //recordingFilePath（）为音频文件的路径
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        double duration = player.getDuration();//获取音频的时间
        player.release();//记得释放资源
        return duration;
    }


}
