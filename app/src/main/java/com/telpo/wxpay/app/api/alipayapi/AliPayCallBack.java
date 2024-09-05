package com.telpo.wxpay.app.api.alipayapi;

import com.alibaba.fastjson.JSON;
import com.telpo.wxpay.app.api.callback.impl.ICallBack;

import net.tsz.afinal.http.AjaxCallBack;

import java.lang.reflect.ParameterizedType;

/**
 * 请求API返回CallBack
 */
public abstract class AliPayCallBack<T> extends AjaxCallBack<String> implements ICallBack<T> {

	@SuppressWarnings("unused")
	private final static String TAG = AliPayCallBack.class.getSimpleName();

	// 获得泛型的Class
	private Class<T> mEntityClass;

	@SuppressWarnings("unchecked")
	public AliPayCallBack() {
		try {
			mEntityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onSuccess(String result) {
//		LogUtil.i(TAG, "返回结果：" + result);
		super.onSuccess(result);
		T data = JSON.parseObject(result, mEntityClass);
		success(data);
		end();
	}

	@Override
	public void onFailure(Throwable t, int errorNo, String strMsg) {
		super.onFailure(t, errorNo, strMsg);
		// LogUtil.i(TAG, "failure:" + strMsg);
		end();
	}

}
