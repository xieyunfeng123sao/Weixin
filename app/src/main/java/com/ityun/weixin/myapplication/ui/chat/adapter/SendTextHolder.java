package com.ityun.weixin.myapplication.ui.chat.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.ityun.weixin.myapplication.R;
import com.ityun.weixin.myapplication.base.BaseViewHolder;
import com.ityun.weixin.myapplication.util.ImageLoadUtil;
import com.ityun.weixin.myapplication.util.SpUtil;
import java.text.SimpleDateFormat;
import butterknife.BindView;

/**
 * Created by Administrator on 2018/5/28 0028.
 */

public class SendTextHolder extends BaseViewHolder<EMMessage> {

    @BindView(R.id.tv_time)
    public TextView tv_time;

    @BindView(R.id.iv_avatar)
    public ImageView iv_avatar;


    @BindView(R.id.tv_message)
    public TextView tv_message;

    @BindView(R.id.tv_send_status)
    public TextView tv_send_status;

    @BindView(R.id.progress_load)
    public ProgressBar progress_load;

    @BindView(R.id.iv_fail_resend)
    public ImageView iv_fail_resend;

    private Context context;


    public SendTextHolder(Context context, ViewGroup root, int layoutRes) {
        super(context, root, layoutRes);
        this.context = context;
    }

    @Override
    public void bindData(EMMessage emMessage) {
//        SpannableStringBuilder sb = handler(tv_message, imMessage.getMessage());
        // 对内容做处理
        EMTextMessageBody body = (EMTextMessageBody) emMessage.getBody();
        tv_message.setText(body.getMessage());
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
        String time = dateFormat.format(emMessage.getMsgTime());
        tv_time.setText(time);
        tv_time.setOnLongClickListener(this);
        bindOnRestart(iv_fail_resend);
        bindClick(tv_message);
        ImageLoadUtil.getInstance().loadUrl(SpUtil.getUser().getAvatar(),iv_avatar);
    }


    public void showTime(boolean isShow) {
        tv_time.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

//
//    private SpannableStringBuilder handler(final TextView gifTextView, String content) {
//        SpannableStringBuilder sb = new SpannableStringBuilder(content);
//        String regex = "(\\#\\[face/png/f_static_)\\d{3}(.png\\]\\#)";
//        Pattern p = Pattern.compile(regex);
//        Matcher m = p.matcher(content);
//        while (m.find()) {
//            String tempText = m.group();
//            try {
//                String num = tempText.substring("#[face/png/f_static_".length(), tempText.length() - ".png]#".length());
//                String gif = "face/gif/f" + num + ".gif";
//                /**
//                 * 如果open这里不抛异常说明存在gif，则显示对应的gif
//                 * 否则说明gif找不到，则显示png
//                 * */
//                InputStream is = context.getAssets().open(gif);
//                sb.setSpan(new AnimatedImageSpan(new AnimatedGifDrawable(is, new AnimatedGifDrawable.UpdateListener() {
//                            @Override
//                            public void update() {
//                                gifTextView.postInvalidate();
//                            }
//                        })), m.start(), m.end(),
//                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                is.close();
//            } catch (Exception e) {
//                String png = tempText.substring("#[".length(), tempText.length() - "]#".length());
//                try {
//                    sb.setSpan(new ImageSpan(context, BitmapFactory.decodeStream(context.getAssets().open(png))), m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                } catch (IOException e1) {
//                    // TODO Auto-generated catch block
//                    e1.printStackTrace();
//                }
//                e.printStackTrace();
//            }
//        }
//        return sb;
//    }
//
//
//    public void setUserName(String name) {
//        iv_avatar.setTextString(name);
//    }


}
