package com.telpo.wxpay.app;

import android.os.Build;

import java.util.HashMap;
import java.util.Map;

public class MerchantInfo {
    public static final String appId = GlobalParams.ALIPAY_APP_ID;
    public static final String appKey = GlobalParams.ALIPAY_PRIVATE_KEY;
    public static final String partnerId = "2088611308670178";
//    public static final String PID = "2088021544115661";
    public static final String PID = "2088611308670178";
    public static final String Store_id = "telpoposDevice";
    public static final String brandCode = "telpo";
    public static final String terminal_id = Build.SERIAL;
    

    public static Map mockInfo2() {
        HashMap localHashMap = new HashMap();
        localHashMap.put("partnerId", partnerId);
        localHashMap.put("merchantId", PID);//这里填写PID,和partnerId不同
        localHashMap.put("appId", appId);
        localHashMap.put("deviceNum", terminal_id);//商户机具终端编号,需要和扫码支付、刷脸支付的terminal_id保持一致
        localHashMap.put("storeCode", Store_id);//商户编号，必须要填，和刷脸付的Store_id保持一致
//        localHashMap.put("alipayStoreCode", "TEST");
        localHashMap.put("brandCode", brandCode);//必须要填一串有意义的字母，用于标识商家
//        localHashMap.put("areaCode", "TEST");
        localHashMap.put("geo", "0.000000,0.000000");
//        localHashMap.put("wifiMac", "TEST");
//        localHashMap.put("wifiName", "TEST");
//        localHashMap.put("deviceMac", "TEST");
        return localHashMap;
    }
    
    public static Map mockInfo() {
        Map merchantInfo = new HashMap();
        //以下信息请根据真实情况填写
        //商户id
        merchantInfo.put("partnerId", partnerId);
        merchantInfo.put("merchantId", partnerId);
        //开放平台注册的appid
        merchantInfo.put("appId", appId);
        //机具编号，便于关联商家管理的机具
        merchantInfo.put("deviceNum", "TEST_ZOLOZ_TEST");
        //真实店铺号
        merchantInfo.put("storeCode", "TEST");
        //口碑店铺号
        merchantInfo.put("alipayStoreCode", "TEST");

        return merchantInfo;
    }
}
