package com.ityun.weixin.myapplication.ui.chat.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.base.BaseViewHolder;
import com.ityun.weixin.myapplication.bean.User;
import com.ityun.weixin.myapplication.listener.BmobTableListener;
import com.ityun.weixin.myapplication.model.UserModel;
import com.ityun.weixin.myapplication.util.ImageLoadUtil;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by Administrator on 2018/5/28 0028.
 */

public class ReceiveTextHolder extends BaseViewHolder<EMMessage> {

    @BindView(R.id.tv_time)
    public TextView tv_time;

    @BindView(R.id.iv_avatar)
    public ImageView iv_avatar;

    @BindView(R.id.tv_message)
    public TextView tv_message;

    private Context context;

    public ReceiveTextHolder(Context context, ViewGroup root, int layoutRes) {
        super(context, root, layoutRes);
        this.context = context;
    }

    @Override
    public void bindData(EMMessage emMessage) {
        String message = emMessage.getBody().toString();
        String showMessage = message.substring(5, message.length() - 1);
        tv_message.setText(showMessage);
//        SpannableStringBuilder sb =  handler(tv_message,imMessage.getMessage());
//        // 对内容做处理
//        tv_message.setText(sb);
////        tv_message.setText(imMessage.getMessage());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
        String time = dateFormat.format(emMessage.getMsgTime());
        tv_time.setText(time);
        bindClick(tv_message);
        UserModel.getInstance().queryByNum(emMessage.getUserName(), new BmobTableListener() {
            @Override
            public void onSucess(Object object) {
                User user = (User) object;
                ImageLoadUtil.getInstance().loadUrl(user.getAvatar(),iv_avatar);
            }

            @Override
            public void onFail(BmobException e) {

            }
        });
    }

    public void showTime(boolean isShow) {
        tv_time.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

//    private SpannableStringBuilder handler(final TextView gifTextView, String content) {
//        SpannableStringBuilder sb = new SpannableStringBuilder(content);
//        String regex = "(\\#\\[face/png/f_static_)\\d{3}(.png\\]\\#)";
//        Pattern p = Pattern.compile(regex);
//        Matcher m = p.matcher(content);
//        while (m.find()) {
//            String tempText = m.group();
//            try {
//                String num = tempText.substring("#[face/png/f_static_".length(), tempText.length()- ".png]#".length());
//                String gif = "face/gif/f" + num + ".gif";
//                /**
//                 * 如果open这里不抛异常说明存在gif，则显示对应的gif
//                 * 否则说明gif找不到，则显示png
//                 * */
//                InputStream is = context.getAssets().open(gif);
//                sb.setSpan(new AnimatedImageSpan(new AnimatedGifDrawable(is,new AnimatedGifDrawable.UpdateListener() {
//                            @Override
//                            public void update() {
//                                gifTextView.postInvalidate();
//                            }
//                        })), m.start(), m.end(),
//                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                is.close();
//            } catch (Exception e) {
//                String png = tempText.substring("#[".length(),tempText.length() - "]#".length());
//                try {
//                    sb.setSpan(new ImageSpan(context, BitmapFactory.decodeStream(context.getAssets().open(png))), m.start(), m.end(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                } catch (IOException e1) {
//                    // TODO Auto-generated catch block
//                    e1.printStackTrace();
//                }
//                e.printStackTrace();
//            }
//        }
//        return sb;
//    }
}
