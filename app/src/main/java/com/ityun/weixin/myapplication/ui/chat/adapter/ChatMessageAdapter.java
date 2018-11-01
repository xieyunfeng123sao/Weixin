package com.ityun.weixin.myapplication.ui.chat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hyphenate.chat.EMMessage;
import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/5/29 0029.
 */

public class ChatMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private List<EMMessage> mlist;
    //发送文字
    private int SEND_TXT = 0;
    // 发送语音
    private int SEND_VOICE = 1;
    // 发送图片
    private int SEND_IMG = 2;
    // 接收文字
    private int RECEIVE_TXT = 3;
    // 接收语音
    private int RECEIVE_VOICE = 4;
    // 接收图片
    private int REVEIVE_IMG = 5;
    // 发送位置
    private int SEND_LOC = 6;
    // 接收位置
    private int RECEIVE_LOC = 7;
    // 发送视频
    private int SEND_VIDEO = 8;
    // 接收视频
    private int RECEIVE_VIDEO = 9;
    // 发送文件
    private int SEND_FILE = 10;
    // 接收文件
    private int RECEIVE_FILE = 11;

    /**
     * 显示时间间隔:10分钟
     */
    private final long TIME_INTERVAL = 10 * 60 * 1000;

    private String userName = "";

    public ChatMessageAdapter(Context context) {
        this.context = context;
//        userName = SpUtil.getUser().getUserName();
    }

    public void setData(List<EMMessage> mlist) {
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == SEND_TXT) {
            return new SendTextHolder(context, parent, R.layout.item_chat_sent_message);
        } else if (viewType == RECEIVE_TXT) {
            return new ReceiveTextHolder(context, parent, R.layout.item_chat_received_message);
        } else if (viewType == SEND_VOICE) {
            return new SendVoiceHolder(context, parent, R.layout.item_chat_sent_voice);
        } else if (viewType == RECEIVE_VOICE) {
            return new ReceiveVoiceHolder(context, parent, R.layout.item_chat_received_voice);
        }
//
// else if (viewType == SEND_IMG) {
//            return new SendImageHolder(context, parent, R.layout.item_send_img);
//        } else if (viewType == REVEIVE_IMG) {
//            return new ReceiveImageHolder(context, parent, R.layout.item_receive_img);
//        } else if (viewType == SEND_LOC) {
//            return new SendLocHolder(context, parent, R.layout.item_send_loc);
//        } else if (viewType == RECEIVE_LOC) {
//            return new ReceiveLocHolder(context, parent, R.layout.item_receive_loc);
//        } else if (viewType == SEND_VIDEO) {
//            return new SendVideoHolder(context, parent, R.layout.item_send_video);
//        } else if (viewType == RECEIVE_VIDEO) {
//            return new ReceiveVideoHolder(context, parent, R.layout.item_receive_video);
//        } else if (viewType == SINGLE_REFUSE) {
//            return new SingleRefuseAdapter(context, parent, R.layout.item_receive_refuse);
//        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((BaseViewHolder) holder).bindData(mlist.get(position));

        ((BaseViewHolder) holder).setOnItemLongClick(new BaseViewHolder.OnRecyclerViewListener() {
            @Override
            public void onItemLongClick(int position) {
                if (onItemLongClick != null)
                    onItemLongClick.onClick(position);
            }

            @Override
            public void onItemRestartClick(int position) {
                if (onItemLongClick != null)
                    onItemLongClick.onRestartClick(position);
            }
        });
        if (holder instanceof SendTextHolder) {
            ((SendTextHolder) holder).showTime(shouldShowTime(position));
        } else if (holder instanceof ReceiveTextHolder) {
            ((ReceiveTextHolder) holder).showTime(shouldShowTime(position));
        } else if (holder instanceof SendVoiceHolder) {
            ((SendVoiceHolder) holder).showTime(shouldShowTime(position));
        } else if (holder instanceof ReceiveVoiceHolder) {
            ((ReceiveVoiceHolder) holder).showTime(shouldShowTime(position));
        }
// else if (holder instanceof SendImageHolder) {
//            ((SendImageHolder) holder).showTime(shouldShowTime(position));
//            ((SendImageHolder) holder).setUserName(userName);
//        } else if (holder instanceof ReceiveImageHolder) {
//            ((ReceiveImageHolder) holder).showTime(shouldShowTime(position));
//        } else if (holder instanceof SendLocHolder) {
//            ((SendLocHolder) holder).showTime(shouldShowTime(position));
//            ((SendLocHolder) holder).setUserName(userName);
//        } else if (holder instanceof ReceiveLocHolder) {
//            ((ReceiveLocHolder) holder).showTime(shouldShowTime(position));
//        } else if (holder instanceof SendVideoHolder) {
//            ((SendVideoHolder) holder).showTime(shouldShowTime(position));
//            ((SendVideoHolder) holder).setUserName(userName);
//        } else if (holder instanceof ReceiveVideoHolder) {
//            ((ReceiveVideoHolder) holder).showTime(shouldShowTime(position));
//        } else if (holder instanceof SingleRefuseAdapter) {
//            ((SingleRefuseAdapter) holder).showTime(shouldShowTime(position));
//        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mlist.get(position).direct().ordinal() == 0) {
            if (mlist.get(position).getType().ordinal() == 0) {
                return SEND_TXT;
            } else if (mlist.get(position).getType().ordinal() == 1) {
                return SEND_IMG;
            } else if (mlist.get(position).getType().ordinal() == 2) {
                return SEND_VIDEO;
            } else if (mlist.get(position).getType().ordinal() == 3) {
                return SEND_LOC;
            } else if (mlist.get(position).getType().ordinal() == 4) {
                return SEND_VOICE;
            } else if (mlist.get(position).getType().ordinal() == 5) {
                return SEND_FILE;
            }

        } else {
            if (mlist.get(position).getType().ordinal() == 0) {
                return RECEIVE_TXT;
            } else if (mlist.get(position).getType().ordinal() == 1) {
                return REVEIVE_IMG;
            } else if (mlist.get(position).getType().ordinal() == 2) {
                return RECEIVE_VIDEO;
            } else if (mlist.get(position).getType().ordinal() == 3) {
                return RECEIVE_LOC;
            } else if (mlist.get(position).getType().ordinal() == 4) {
                return RECEIVE_VOICE;
            } else if (mlist.get(position).getType().ordinal() == 5) {
                return RECEIVE_FILE;
            }
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return null != mlist ? mlist.size() : 0;
    }

    private boolean shouldShowTime(int position) {
        if (position == 0) {
            return true;
        }
        long lastTime = mlist.get(position - 1).getMsgTime();
        long curTime = mlist.get(position).getMsgTime();
        return curTime - lastTime > TIME_INTERVAL;
    }

    OnItemLongClick onItemLongClick;

    public void setOnItemLongClick(OnItemLongClick onItemLongClick) {
        this.onItemLongClick = onItemLongClick;
    }

    public interface OnItemLongClick {
        void onClick(int position);

        void onRestartClick(int position);
    }
}
