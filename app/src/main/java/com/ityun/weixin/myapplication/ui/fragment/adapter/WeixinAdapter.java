package com.ityun.weixin.myapplication.ui.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ityun.weixin.myapplication.R;

import java.util.List;

/**
 * Created by Administrator on 2018/2/13 0013.
 */

public class WeixinAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;

    private List mlist;

    public static final int ONE_ITEM = 1;
//    public static final int TWO_ITEM = 2;

    public WeixinAdapter(Context context) {
        this.context = context;
    }


    public void setData(List mlist) {
        this.mlist = mlist;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder holder = null;
//        if (ONE_ITEM == viewType) {
//            View v = mInflater.inflate(R.layout.item_recyle_weixin_top, parent, false);
//            holder = new RecyleTopHolder(v);
//        } else {
            View v = mInflater.inflate(R.layout.item_recyle_weixin_msg, parent, false);
            holder = new RecyleItemHolder(v);
//        }
        return holder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position>0)
        {
            ((RecyleItemHolder)holder).item_weixin_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
//        if(position==0)
//        {
            return  ONE_ITEM;
//        }
//        return TWO_ITEM;
    }




    @Override
    public int getItemCount() {
       return  mlist!=null?(mlist.size()+1):1;
    }


    class RecyleTopHolder extends RecyclerView.ViewHolder {


        public RecyleTopHolder(View itemView) {
            super(itemView);
        }
    }

    class RecyleItemHolder extends RecyclerView.ViewHolder {
        LinearLayout item_weixin_ll;
        public RecyleItemHolder(View itemView) {
            super(itemView);
            item_weixin_ll=itemView.findViewById(R.id.item_weixin_ll);
        }
    }
}
