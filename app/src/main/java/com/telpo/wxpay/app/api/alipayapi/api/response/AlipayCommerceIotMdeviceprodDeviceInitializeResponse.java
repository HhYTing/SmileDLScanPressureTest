package com.telpo.wxpay.app.api.alipayapi.api.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.telpo.wxpay.app.api.alipayapi.api.AlipayResponse;

/**
 * Created by bruce on 2017/12/26.
 */

public class AlipayCommerceIotMdeviceprodDeviceInitializeResponse extends AlipayResponse {
    @JSONField(name = "biz_tid")
    String biz_tid;

    public String getBiz_tid() {
        return biz_tid;
    }

    public void setBiz_tid(String biz_tid) {
        this.biz_tid = biz_tid;
    }
}
