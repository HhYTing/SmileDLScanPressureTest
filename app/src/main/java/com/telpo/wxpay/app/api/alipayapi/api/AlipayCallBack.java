package com.telpo.wxpay.app.api.alipayapi.api;



/**
 * Created by bruce on 2017/12/25.
 */

public interface AlipayCallBack {
    <T extends AlipayResponse> T onResponse(T response);
}
