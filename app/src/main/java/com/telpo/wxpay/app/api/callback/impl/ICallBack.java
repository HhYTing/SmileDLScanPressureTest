/*
 * ICallBack.java classes : com.xizi.testcallback.ICallBack
 * 
 * @author 张超川 V 1.0.0 Create at 2014-6-30 下午03:15:49
 */
package com.telpo.wxpay.app.api.callback.impl;



/**
 * @author 张超川 create at 2014-6-30 下午03:15:49
 */
public interface ICallBack<T> {
	/**
	 * 请求前
	 */
	public void prepare(String url, String xmlString);

	/**
	 * 请求成功
	 */
	public void success(Object object);

	/**
	 * 请求失败
	 */
	public void failure(String retCode);

	/**
	 * 请求结束
	 */
	public void end();
}
