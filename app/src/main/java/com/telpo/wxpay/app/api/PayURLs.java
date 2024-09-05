package com.telpo.wxpay.app.api;

public class PayURLs {
	public static String HOST = "https://api.mch.weixin.qq.com/";

	// 扫码支付
	public static String UNIFIEDORDER = HOST + "pay/unifiedorder";// 统一下单
	public static String ORDERQUERY = HOST + "pay/orderquery";// 查询订单
	public static String CLOSEORDER = HOST + "pay/closeorder";// 关闭订单
	public static String REFUND = HOST + "secapi/pay/refund";// 申请退款
	public static String REFUNDQUERY = HOST + "pay/refundquery";// 查询退款
	public static String DOWNLOADBILL = HOST + "/pay/downloadbill";// 下载对账单

	// 微信刷卡支付
	public static String MICROPAY = HOST + "pay/micropay";

	// 支付宝支付接口
	public static String ALIAPY_HOST = "https://openapi.alipay.com/gateway.do";

}
