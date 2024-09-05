package com.telpo.wxpay.app.api;

import com.telpo.wxpay.app.api.alipayapi.AliPayCallBack;
import com.telpo.wxpay.app.api.wechatpayapi.WeChatPayCallBack;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxParams;

import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.BasicHttpEntity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * @description: API基类，每个接口类都继承此类
 */
public abstract class BaseAPI<T> {

	@SuppressWarnings("unused")
	private final static String TAG = BaseAPI.class.getSimpleName();

	// 使用FinalHttp请求
	protected FinalHttp mFinalHttp;

	public BaseAPI() {
		mFinalHttp = new FinalHttp();
		// 设置默认超时时间为30秒
		mFinalHttp.configTimeout(8000);
		SSLSocketFactory sf;
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);
			sf = new SSLSocketFactoryEx(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			mFinalHttp.configSSLSocketFactory(sf);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @description: 网络请求方法，子类统一调用
	 * @param url
	 * @param xmlString
	 *            提交的XML参数
	 * @param callBack
	 * @return void
	 */
	protected void post(String url, String xmlString, WeChatPayCallBack<T> callBack) {
		BasicHttpEntity entity = new BasicHttpEntity();
		entity.setContent(new ByteArrayInputStream(xmlString.getBytes()));
		callBack.prepare(url, xmlString);
		mFinalHttp.post(url, entity, "text/xml", callBack);
	}

	/**
	 * 支付宝请求
	 * 
	 * @param chartype
	 * @param params
	 * @param callBack
	 */
	protected void aliPayPost(String chartype, AjaxParams params, AliPayCallBack<T> callBack) {
//		LogUtil.i("请求tag", params.toString());
		callBack.prepare(PayURLs.ALIAPY_HOST, "");
		mFinalHttp.post(PayURLs.ALIAPY_HOST + "?charset=" + chartype, params, callBack);
	}

	/**
	 * SSLSocketFactory类，防止设备无证书错误
	 */
	private static class SSLSocketFactoryEx extends SSLSocketFactory {

		SSLContext sslContext = SSLContext.getInstance("TLS");

		public SSLSocketFactoryEx(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
			super(truststore);

			TrustManager tm = new X509TrustManager() {

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
				}

				@Override
				public void checkServerTrusted(X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
				}
			};

			sslContext.init(null, new TrustManager[] { tm }, null);
		}

		@Override
		public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
			return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
		}

		@Override
		public Socket createSocket() throws IOException {
			return sslContext.getSocketFactory().createSocket();
		}
	}
}
