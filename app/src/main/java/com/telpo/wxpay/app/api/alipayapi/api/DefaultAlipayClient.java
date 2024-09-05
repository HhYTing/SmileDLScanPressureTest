package com.telpo.wxpay.app.api.alipayapi.api;


import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.telpo.wxpay.app.api.alipayapi.api.http.HttpUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by bruce on 2017/12/25.
 */
public class DefaultAlipayClient implements AlipayClient {
    private static String ar;

    public static String getAr() {
        return ar;
    }

    private static void setAr(String ar1) {
        ar = ar1;
    }

    private final String TAG = "DefaultAlipayClient";
    private String serverUrl;
    private String appId;
    private String privateKey;
    private String prodCode;
    private String format = AlipayConstants.FORMAT_JSON;
    private String sign_type = AlipayConstants.SIGN_TYPE_RSA;

    private String encryptType = AlipayConstants.ENCRYPT_TYPE_AES;

    private String encryptKey;

    private String alipayPublicKey;

    private String charset;

    private int connectTimeout = 3000;
    private int readTimeout = 15000;
    private static ThreadPoolProxy threadPoolProxy;
    private Handler mMainHandler;

    public DefaultAlipayClient(String serverUrl, String appId, String privateKey) {
        this.serverUrl = serverUrl;
        this.appId = appId;
        this.privateKey = privateKey;
        this.alipayPublicKey = null;

        mMainHandler = new Handler(Looper.getMainLooper());
    }

    public DefaultAlipayClient(String serverUrl, String appId, String privateKey, String format) {
        this(serverUrl, appId, privateKey);
        this.format = format;
    }

    public DefaultAlipayClient(String serverUrl, String appId, String privateKey, String format,
                               String charset) {
        this(serverUrl, appId, privateKey);
        this.format = format;
        this.charset = charset;
    }

    public DefaultAlipayClient(String serverUrl, String appId, String privateKey, String format,
                               String charset, String alipayPulicKey) {
        this(serverUrl, appId, privateKey);
        this.format = format;
        this.charset = charset;
        this.alipayPublicKey = alipayPulicKey;
    }

    public DefaultAlipayClient(String serverUrl, String appId, String privateKey, String format,
                               String charset, String alipayPulicKey, String signType) {

        this(serverUrl, appId, privateKey, format, charset, alipayPulicKey);

        this.sign_type = signType;
    }

    public DefaultAlipayClient(String serverUrl, String appId, String privateKey, String format,
                               String charset, String alipayPulicKey, String signType,
                               String encryptKey, String encryptType) {

        this(serverUrl, appId, privateKey, format, charset, alipayPulicKey, signType);
        this.encryptType = encryptType;
        this.encryptKey = encryptKey;
    }

    @Override
    public <T extends AlipayResponse> void execute(AlipayRequest<T> request, AlipayCallBack callBack) {
        _execute(request, null, null, null, callBack);
    }

    /**
     * 组装接口参数，处理加密、签名逻辑
     *
     * @param request
     * @param accessToken
     * @param appAuthToken
     * @return
     */
    private <T extends AlipayResponse> RequestParametersHolder getRequestHolderWithSign(AlipayRequest<?> request,
                                                                                        String accessToken,
                                                                                        String appAuthToken)
            throws AlipayApiException {
        RequestParametersHolder requestHolder = new RequestParametersHolder();

        AlipayHashMap appParams = new AlipayHashMap(request.getTextParams());

        if (!TextUtils.isEmpty(appAuthToken)) {
            appParams.put(AlipayConstants.APP_AUTH_TOKEN, appAuthToken);
        }

        requestHolder.setApplicationParams(appParams);

        if (TextUtils.isEmpty(charset)) {
            charset = AlipayConstants.CHARSET_UTF8;
        }

        AlipayHashMap protocalMustParams = new AlipayHashMap();
        protocalMustParams.put(AlipayConstants.METHOD, request.getApiMethodName());
        protocalMustParams.put(AlipayConstants.VERSION, request.getApiVersion());
        protocalMustParams.put(AlipayConstants.APP_ID, this.appId);
        protocalMustParams.put(AlipayConstants.SIGN_TYPE, this.sign_type);
        //protocalMustParams.put(AlipayConstants.TERMINAL_TYPE, request.getTerminalType());
        //protocalMustParams.put(AlipayConstants.TERMINAL_INFO, request.getTerminalInfo());
        //protocalMustParams.put(AlipayConstants.NOTIFY_URL, request.getNotifyUrl());
        //protocalMustParams.put(AlipayConstants.RETURN_URL, request.getReturnUrl());
        protocalMustParams.put(AlipayConstants.CHARSET, charset);

        if (request.isNeedEncrypt()) {
            protocalMustParams.put(AlipayConstants.ENCRYPT_TYPE, this.encryptType);
        }

        Long timestamp = System.currentTimeMillis();
        DateFormat df = new SimpleDateFormat(AlipayConstants.DATE_TIME_FORMAT);
        df.setTimeZone(TimeZone.getTimeZone(AlipayConstants.DATE_TIMEZONE));
        protocalMustParams.put(AlipayConstants.TIMESTAMP, df.format(new Date(timestamp)));
        requestHolder.setProtocalMustParams(protocalMustParams);

        AlipayHashMap protocalOptParams = new AlipayHashMap();
        protocalOptParams.put(AlipayConstants.FORMAT, format);
        //protocalOptParams.put(AlipayConstants.ACCESS_TOKEN, accessToken);
        //protocalOptParams.put(AlipayConstants.ALIPAY_SDK, AlipayConstants.SDK_VERSION);
        //protocalOptParams.put(AlipayConstants.PROD_CODE, request.getProdCode());
        requestHolder.setProtocalOptParams(protocalOptParams);

        if (!TextUtils.isEmpty(this.sign_type)) {

            String signContent = AlipaySignature.getSignatureContent(requestHolder);
//            TLog.i("Test", "signContent:" + signContent);
            try {

                protocalMustParams.put(AlipayConstants.SIGN,
                        AlipaySignature.rsaSign(signContent, privateKey, charset, this.sign_type));

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            protocalMustParams.put(AlipayConstants.SIGN, "");
        }
        return requestHolder;
    }

    private <T extends AlipayResponse> void _execute(AlipayRequest<T> request, AlipayParser<T> parser,
                                                     String authToken,
                                                     String appAuthToken, AlipayCallBack callBack) {

        try {
            doPost(request, authToken, appAuthToken, callBack);
        } catch (AlipayApiException e) {

        }

    }

    /**
     * @param request
     * @param accessToken
     * @return
     */
    private <T extends AlipayResponse> void doPost(final AlipayRequest<T> request,
                                                   String accessToken,
                                                   String appAuthToken, final AlipayCallBack callBack)
            throws AlipayApiException {
        Map<String, Object> result = new HashMap<String, Object>();
        final RequestParametersHolder requestHolder = getRequestHolderWithSign(request, accessToken,
                appAuthToken);

        //String rsp = HttpUtils.doPost(url);
        final HashMap<String, String> params = new HashMap<String, String>();
        params.putAll(requestHolder.getProtocalMustParams());
        params.putAll(requestHolder.getProtocalOptParams());
        params.putAll(requestHolder.getApplicationParams());

        if (threadPoolProxy == null) {
            synchronized (DefaultAlipayClient.this) {
                if (threadPoolProxy == null) {
                    threadPoolProxy = new ThreadPoolProxy(3, 25, 10);       //xtk
                }
            }
        }

        threadPoolProxy.executeTask(new Runnable() {
            @Override
            public void run() {

                String responseStr = HttpUtils.postData(serverUrl, params);
//                TLog.i(TAG, "response:" + responseStr);
                if (TextUtils.isEmpty(responseStr)) {
                    if (callBack != null) {
                        callBack.onResponse(null);
                    }
                    return;
                }
                AlipayResponse response = getResponse(responseStr, request.getResponseClass());
                if (callBack != null) {
                    callBack.onResponse(response);
                }

            }
        });

    }

    private <T extends AlipayResponse> T getResponse(String rsp, Class<T> clazz) {

        JSONObject resObject = JSONObject.parseObject(rsp);

        String resultObj = resObject.get(getItemName(clazz)).toString();

        JSONObject jso = JSON.parseObject(resultObj);
        String jsarr = jso.getString("trade_no");
        setAr(jsarr);

        T obj = JSONObject.parseObject(resultObj, clazz);

        return obj;
    }

    private <T extends AlipayResponse> String getItemName(Class<T> clazz) {

        String inputStr = clazz.getSimpleName();
        String replaceStr = inputStr;
        for (int i = inputStr.length() - 1; i >= 0; i--) {
            char c = inputStr.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i != 0) {
                    replaceStr = replaceStr.replace("" + c, "_" + Character.toLowerCase(c));
                } else {
                    replaceStr = replaceStr.replace(c, Character.toLowerCase(c));
                }
                continue;
            }
        }

        return replaceStr;
    }

}
