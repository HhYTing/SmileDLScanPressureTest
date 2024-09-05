package com.telpo.wxpay.app.api.alipayapi;

import com.telpo.wxpay.app.api.BaseAPI;

import net.tsz.afinal.http.AjaxParams;

public class AliPayAPI<T> extends BaseAPI<T> {

	/**
	 * 预下单请求接口
	 */
	public void precreate(String chartype, AjaxParams params, AliPayCallBack<T> callBack) {
		aliPayPost(chartype, params, callBack);
	}

	/**
	 * 查询订单接口
	 *
	 * @param xmlString
	 * @param params
	 * @param callBack
	 */
	public void query(String xmlString, AjaxParams params, AliPayCallBack<T> callBack) {
		aliPayPost(xmlString, params, callBack);
	}

	/**
	 * 付款（条形码）支付接口
	 *
	 * @param xmlString
	 * @param params
	 * @param callBack
	 */
	public void pay(String xmlString, AjaxParams params, AliPayCallBack<T> callBack) {
		aliPayPost(xmlString, params, callBack);
	}

}
