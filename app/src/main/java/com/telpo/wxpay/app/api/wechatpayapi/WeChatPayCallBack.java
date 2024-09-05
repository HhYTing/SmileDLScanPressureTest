package com.telpo.wxpay.app.api.wechatpayapi;

import android.util.Log;

import com.telpo.wxpay.app.api.callback.impl.ICallBack;
import com.telpo.wxpay.app.util.ParamsUtil;

import net.tsz.afinal.http.AjaxCallBack;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 请求API返回CallBack
 */
public abstract class WeChatPayCallBack<T> extends AjaxCallBack<String> implements ICallBack<T> {

	@SuppressWarnings("unused")
	private final static String TAG = WeChatPayCallBack.class.getSimpleName();

	// 获得泛型的Class
	@SuppressWarnings("unused")
	private Class<T> mEntityClass;

	@SuppressWarnings("unchecked")
	public WeChatPayCallBack() {
		try {
			mEntityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		} catch (Exception e) {
			e.printStackTrace();
			// AppException.unkonw(e);
		}

	}

	@Override
	public void onSuccess(String result) {
		Log.i(TAG, "返回xml结果：" + result);
		super.onSuccess(result);
		Map<String, String> resultMap = ParamsUtil.decodeXml(result);
		// if (checkSign(resultMap)) {
		if (resultMap.get("return_code") != null && resultMap.get("return_code").equals("SUCCESS")) {
			if (resultMap.get("result_code") != null && resultMap.get("result_code").equals("SUCCESS")) {
				success(resultMap);
			} else {
				failure(resultMap.get("err_code_des"));
			}
		} else {
			failure(resultMap.get("return_msg"));
		}
		end();
	}

	// else {
	// LogUtil.i(TAG, "返回数据校验失败");
	// failure(AppException.TYPE_SERVER + "", AppException.server(0,
	// "返回数据校验失败,请联系软件提供商"));
	//
	// }

	@Override
	public void onFailure(Throwable t, int errorNo, String strMsg) {
		super.onFailure(t, errorNo, strMsg);
		// LogUtil.i(TAG, "failure:" + strMsg);
		end();
	}

	/**
	 * 对接收微信返回的数据进行验签
	 * 
	 * @param map
	 * @return
	 */
	public boolean checkSign(Map<String, String> map) {
		Collection<String> keyset = map.keySet();
		List<String> list = new ArrayList<String>(keyset);
		// 对key键值按字典升序排序
		Collections.sort(list);
		List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
		for (int i = 0; i < list.size(); i++) {
			// System.out.println("key键---值: " + list.get(i) + "," +
			// map.get(list.get(i)));
			if (!list.get(i).equals("sign")) {
				packageParams.add(new BasicNameValuePair(list.get(i), map.get(list.get(i))));
			}
		}
		String resultSign = map.get("sign");
		String sign = ParamsUtil.genPackageSign(packageParams);
		// LogUtil.i("resultSign值: ", resultSign);
		// LogUtil.i("sign值: ", sign);
		if (sign.equals(resultSign)) {
			return true;
		}

		return false;

	}

}
