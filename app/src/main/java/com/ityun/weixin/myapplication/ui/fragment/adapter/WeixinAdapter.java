package com.ityun.weixin.myapplication.ui.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chat.adapter.message.EMAMessage;
import com.hyphenate.chat.adapter.message.EMATextMessageBody;
import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.base.App;
import com.ityun.weixin.myapplication.bean.Friend;
import com.ityun.weixin.myapplication.bean.User;
import com.ityun.weixin.myapplication.conn.Constant;
import com.ityun.weixin.myapplication.listener.AdapterItemOnClickListener;
import com.ityun.weixin.myapplication.listener.BmobTableListener;
import com.ityun.weixin.myapplication.model.UserModel;
import com.ityun.weixin.myapplication.util.DateUtil;
import com.ityun.weixin.myapplication.util.ImageLoadUtil;

import java.util.List;
import java.util.Map;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by Administrator on 2018/2/13 0013.
 */

public class WeixinAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;

    private Map<String, EMConversation> mlist;

    private AdapterItemOnClickListener onClickListener;

    public static final int ONE_ITEM = 1;

    public WeixinAdapter(Context context) {
        this.context = context;
    }


    public void setData(Map<String, EMConversation> mlist) {
        this.mlist = mlist;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder holder = null;
        View v = mInflater.inflate(R.layout.item_recyle_weixin_msg, parent, false);
        holder = new RecyleItemHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        EMConversation obj = null;
        int po = 0;
        for (Map.Entry<String, EMConversation> entry : mlist.entrySet()) {
            obj = entry.getValue();
            if (obj != null && position == po) {
                break;
            }
            po++;
        }
        EMMessage message = obj.getLastMessage();
        if (message.getType().ordinal() == EMMessage.Type.TXT.ordinal()) {
            EMTextMessageBody body= (EMTextMessageBody) message.getBody();
            ((RecyleItemHolder) holder).user_last_msg.setText(body.getMessage());
        } else {
            ((RecyleItemHolder) holder).user_last_msg.setText("[" + Constant.MessageType.str[message.getType().ordinal() - 1] + "]");
        }
        ((RecyleItemHolder) holder).user_last_msg_time.setText(DateUtil.timeToHHText(message.getMsgTime()));
        ((RecyleItemHolder) holder).user_nickname.setText(message.getUserName());
        Friend friend = null;
        if (message.direct().ordinal() == EMMessage.Direct.RECEIVE.ordinal()) {
            friend = App.getInstance().getFriend(message.getUserName());
        } else {
            friend = App.getInstance().getFriend(message.getTo());
        }

        if (friend != null) {

            ImageLoadUtil.getInstance().loadUrl(friend.getFriendUser().getAvatar(), ((RecyleItemHolder) holder).user_img);
            ((RecyleItemHolder) holder).user_nickname.setText(friend.getFriendUser().getNickname());
        } else {
            ((RecyleItemHolder) holder).item_weixin_ll.setVisibility(View.GONE);
            UserModel.getInstance().queryByNum(message.getTo(), new BmobTableListener() {
                @Override
                public void onSucess(Object object) {
                    User user = (User) object;
                    ImageLoadUtil.getInstance().loadUrl(user.getAvatar(), ((RecyleItemHolder) holder).user_img);
                    ((RecyleItemHolder) holder).user_nickname.setText(user.getNickname());
                    ((RecyleItemHolder) holder).item_weixin_ll.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFail(BmobException e) {
                    notifyItemRemoved(position);
                }
            });
//            ImageLoadUtil.getInstance().getResouce(R.color.txt_color, ((RecyleItemHolder) holder).user_img);
//            ((RecyleItemHolder) holder).user_nickname.setText("");
        }

        ((RecyleItemHolder) holder).item_weixin_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.OnClick(position);
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        return ONE_ITEM;
    }


    @Override
    public int getItemCount() {
        return mlist != null ? (mlist.size() + 0) : 0;
    }


    class RecyleTopHolder extends RecyclerView.ViewHolder {


        public RecyleTopHolder(View itemView) {
            super(itemView);
        }
    }

    class RecyleItemHolder extends RecyclerView.ViewHolder {

        LinearLayout item_weixin_ll;

        ImageView user_img;

        TextView user_nickname;

        TextView user_last_msg;

        TextView user_last_msg_time;

        public RecyleItemHolder(View itemView) {
            super(itemView);
            item_weixin_ll = itemView.findViewById(R.id.item_weixin_ll);
            user_img = itemView.findViewById(R.id.user_img);
            user_nickname = itemView.findViewById(R.id.user_nickname);
            user_last_msg = itemView.findViewById(R.id.user_last_msg);
            user_last_msg_time = itemView.findViewById(R.id.user_last_msg_time);

        }
    }

    public void setonItemOnClick(AdapterItemOnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
