package com.telpo.wxpay.app.api.alipayapi.api.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.telpo.wxpay.app.api.alipayapi.api.AlipayResponse;

/**
 * Created by bruce on 2017/12/25.
 */

public class AlipayCommerceLogisticsUserOrderQueryResponse extends AlipayResponse {

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    @JSONField(name = "order_info")
    String orderInfo = "";
}
