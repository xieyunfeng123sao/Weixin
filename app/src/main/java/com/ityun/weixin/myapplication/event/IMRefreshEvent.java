package com.ityun.weixin.myapplication.event;

import com.hyphenate.chat.EMMessage;

/**
 * Created by 77367 on 2018/10/18.
 */

public class IMRefreshEvent {

    public EMMessage emMessage;

    public IMRefreshEvent(EMMessage emMessage) {
        this.emMessage = emMessage;
    }

}
