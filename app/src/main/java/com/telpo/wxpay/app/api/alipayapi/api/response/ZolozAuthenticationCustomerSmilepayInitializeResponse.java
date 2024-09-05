package com.telpo.wxpay.app.api.alipayapi.api.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.telpo.wxpay.app.api.alipayapi.api.AlipayResponse;

public class ZolozAuthenticationCustomerSmilepayInitializeResponse
    extends AlipayResponse
{
    private static final long serialVersionUID = 1717839179174256488L;
    @JSONField(name = "result")
    private String result;

    public void setResult(String result)
    {
        this.result = result;
    }

    public String getResult()
    {
        return this.result;
    }
}
