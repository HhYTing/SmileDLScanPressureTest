package com.telpo.wxpay.app.api.alipayapi.api.request;

/**
 * Created by bruce on 2017/12/25.
 */

public class IotDeviceInitalizeParam {
    String device_sn;//	String	必选	60	设备sn，通常置于设备标签中	SN123456
    String supplier_id;//	String	必选	30	供应商id,该值由支付宝同学提供	201748294792423
    String item_id;//	String	必选	30	物料id,需支付宝同学提供	2018098738748
    String mac;//	String	可选	20	mac地址，网络设备网卡地址	00-01-6C-06-A6-29
    String imei;//	String	可选	32	IMEI移动设备识别码	359355041886388
    String net_type;//	String	可选	10	设备无线网络类型，可选项[2G,3G,4G,WIRED,BT,OTHER,NONE]
    //            2G-2G移动网络
//3G-3G移动网络
//4G-4G移动网络
//    WIRED-有线网络
//    WIFI-无线网络
//    BT-蓝牙
//    OTHER-其他网络类型
//    NONE-无联网功能	WIRED
    String os_version;//	String	可选	20	软件版本	Linux_v2.2
    String plain_text;//	String	特殊可选	200	加签明文，针对带miniSDK的特殊设备使用，其他设备不需要填该内容	84940eu&woeruw&2018
    String sign_info;//	String	特殊可选	200	加签信息，针对带miniSDK的特殊设备使用，其他设备不需要填写该内容	KDIE294839JDHG40
    String ext_info;//	String	可选	1000	扩展信息，必须为JSON格式	{"pay_channel":"double"}

    public String getDevice_sn() {
        return device_sn;
    }

    public void setDevice_sn(String device_sn) {
        this.device_sn = device_sn;
    }

    public String getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(String supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getNet_type() {
        return net_type;
    }

    public void setNet_type(String net_type) {
        this.net_type = net_type;
    }

    public String getOs_version() {
        return os_version;
    }

    public void setOs_version(String os_version) {
        this.os_version = os_version;
    }

    public String getPlain_text() {
        return plain_text;
    }

    public void setPlain_text(String plain_text) {
        this.plain_text = plain_text;
    }

    public String getSign_info() {
        return sign_info;
    }

    public void setSign_info(String sign_info) {
        this.sign_info = sign_info;
    }

    public String getExt_info() {
        return ext_info;
    }

    public void setExt_info(String ext_info) {
        this.ext_info = ext_info;
    }
}