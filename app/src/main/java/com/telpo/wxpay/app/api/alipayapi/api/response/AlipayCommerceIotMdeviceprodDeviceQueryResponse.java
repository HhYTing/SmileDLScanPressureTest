package com.telpo.wxpay.app.api.alipayapi.api.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.telpo.wxpay.app.api.alipayapi.api.AlipayResponse;

/**
 * Created by bruce on 2017/12/26.
 */

public class AlipayCommerceIotMdeviceprodDeviceQueryResponse extends AlipayResponse {
    @JSONField(name = "biz_tid")
    String biz_tid;

    @JSONField(name = "bind_status")
    String bind_status;//":"UNBIND",

    @JSONField(name = "ext_info")
    String ext_info;//":"{\"source_type\":\"SDK激活\"}",

    @JSONField(name = "item_id")
    String item_id;//":"2019111304877238",

    @JSONField(name = "status")
    String status;//":"ACTIVED",

    @JSONField(name = "supplier_sn")
    String supplier_sn;//":"201810251600319950"

    public String getBiz_tid() {
        return biz_tid;
    }

    public void setBiz_tid(String biz_tid) {
        this.biz_tid = biz_tid;
    }

    public String getBind_status() {
        return bind_status;
    }

    public void setBind_status(String bind_status) {
        this.bind_status = bind_status;
    }

    public String getExt_info() {
        return ext_info;
    }

    public void setExt_info(String ext_info) {
        this.ext_info = ext_info;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSupplier_sn() {
        return supplier_sn;
    }

    public void setSupplier_sn(String supplier_sn) {
        this.supplier_sn = supplier_sn;
    }
}
