package com.ityun.weixin.myapplication.ui.fragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.bean.User;
import com.ityun.weixin.myapplication.listener.BmobTableListener;
import com.ityun.weixin.myapplication.model.UserModel;
import com.ityun.weixin.myapplication.ui.chat.ChatActivity;
import com.ityun.weixin.myapplication.util.DateUtil;
import com.ityun.weixin.myapplication.util.ImageLoadUtil;

import java.util.List;

import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by Administrator on 2018/2/13 0013.
 */

public class WeixinAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;

    private List<BmobIMConversation> mlist;

    public static final int ONE_ITEM = 1;
//    public static final int TWO_ITEM = 2;

    public WeixinAdapter(Context context) {
        this.context = context;
    }


    public void setData(List<BmobIMConversation> mlist) {
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
        List<BmobIMMessage> mgsList=mlist.get(position).getMessages();
        if(mgsList!=null&&mgsList.size()!=0)
        {
            ((RecyleItemHolder)holder).user_last_msg.setText(mgsList.get(mgsList.size()-1).getContent());
            ((RecyleItemHolder)holder).user_last_msg_time.setText(DateUtil.timeToText(mgsList.get(mgsList.size()-1).getUpdateTime()));
        }
        else
        {
            ((RecyleItemHolder)holder).user_last_msg.setText("");
            ((RecyleItemHolder)holder).user_last_msg_time.setText("");
        }
        ImageLoadUtil.getInstance().loadUrl(mlist.get(position).getConversationIcon(),((RecyleItemHolder)holder).user_img);
        UserModel.getInstance().queryById(mlist.get(position).getConversationId(), new BmobTableListener<User>() {
            @Override
            public void onSucess(User object) {
                ((RecyleItemHolder)holder).user_nickname.setText(object.getNickname());
            }

            @Override
            public void onFail(BmobException e) {
                ((RecyleItemHolder)holder).user_nickname.setText(mlist.get(position).getConversationTitle());
            }
        });

            ((RecyleItemHolder)holder).item_weixin_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, ChatActivity.class);
                    context.startActivity(intent);
                }
            });
    }

    @Override
    public int getItemViewType(int position) {
            return  ONE_ITEM;

    }




    @Override
    public int getItemCount() {
       return  mlist!=null?(mlist.size()+0):0;
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
            item_weixin_ll=itemView.findViewById(R.id.item_weixin_ll);
            user_img=itemView.findViewById(R.id.user_img);
            user_nickname=itemView.findViewById(R.id.user_nickname);
            user_last_msg=itemView.findViewById(R.id.user_last_msg);
            user_last_msg_time=itemView.findViewById(R.id.user_last_msg_time);

        }
    }
}
