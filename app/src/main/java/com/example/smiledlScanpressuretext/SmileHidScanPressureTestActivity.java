package com.example.smiledlScanpressuretext;

import static android.content.Intent.ACTION_SCREEN_OFF;
import static android.content.Intent.ACTION_USER_PRESENT;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.iot.sdk.APIManager;
import com.alipay.iot.sdk.InitFinishCallback;
import com.alipay.iot.sdk.device.DeviceAPI;
import com.alipay.zoloz.smile2pay.MetaInfoCallback;
import com.alipay.zoloz.smile2pay.Zoloz;
import com.alipay.zoloz.smile2pay.verify.Smile2PayResponse;
import com.alipay.zoloz.smile2pay.verify.VerifyCallback;
import com.common.apiutil.powercontrol.PowerControl;
import com.common.apiutil.util.SDKUtil;
import com.dawn.decoderapijni.SoftEngine;
import com.example.smiledlScanpressuretext.databinding.ActivitySmiledlhidscanBinding;
import com.example.smiledlScanpressuretext.databinding.ActivitySmiledlscanBinding;
import com.example.smiledlScanpressuretext.hid.KeyEventResolver;
import com.scanner.libs.ConnectServiceListener;
import com.scanner.libs.ScanCallback;
import com.scanner.libs.ScannerLib;
import com.telpo.wxpay.app.MerchantInfo;
import com.telpo.wxpay.app.api.alipayapi.api.AlipayCallBack;
import com.telpo.wxpay.app.api.alipayapi.api.AlipayClient;
import com.telpo.wxpay.app.api.alipayapi.api.AlipayResponse;
import com.telpo.wxpay.app.api.alipayapi.api.DefaultAlipayClient;
import com.telpo.wxpay.app.api.alipayapi.api.request.ZolozAuthenticationCustomerSmilepayInitializeRequest;
import com.telpo.wxpay.app.api.alipayapi.api.response.ZolozAuthenticationCustomerSmilepayInitializeResponse;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SmileHidScanPressureTestActivity extends AppCompatActivity implements KeyEventResolver.OnKeyEventListener{

    private final int HANDLER_START_TEST = 1;
    private final int HANDLER_STOP_TEST = 2;
    private final int HANDLER_TEST_TOTAL_TIME_UPDATE = 3;
    private final int HANDLER_SMILE_PAY_RESPONSE = 4;
    private final int HANDLER_IOT_ACTIVITE_STATE = 5;

    private static final int SINGLE_MODE = 0;
    private static final int CONTINUOUS_MODE = 1;

    private static final String TAG = Constants.TAG;
    private boolean isRunning = false;
    private Thread writeThread;
    private ActivitySmiledlhidscanBinding binding;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private int totalTime = 0;  // 已经测试的时间
    private int count = 0;  // 已经测试的次数，成功次数
    private int smile_count = 0;  // 刷脸已经测试的次数，成功次数
    private int scan_count = 0;  // 扫码已经测试的次数，成功次数
    private int scan_data_count = 0;  // 扫码数据测试的次数，成功次数
    private int allCcount = 0;  // 已经测试的总次数，成功次数
    private long debugTime = -1; // 设置测试的时间
    private int intervalTime = 0;   // 测试间隔时间
    private ScheduledExecutorService totalTimeScheduledExecutor = Executors.newSingleThreadScheduledExecutor();

    private IOTSDK iOTSDK;

    private KeyEventResolver mKeyEventResolver;

    private static final int WIFI_SETTINGS_REQUEST_CODE = 1;

    private boolean isSmile = false;

    private final Handler handler = new Handler(new WeakReference<Handler.Callback>(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
//            Log.v(MyApplication.TAG,"Handler Thread:" + Thread.currentThread().getName());
            switch (msg.what) {
                case HANDLER_TEST_TOTAL_TIME_UPDATE:
                    totalTime++;
                    binding.tvAlreadyTestTime.setText(totalTime + "");
                    break;
                case HANDLER_START_TEST:
                    binding.etSetTestTime.setEnabled(false);
                    binding.etTestInterval.setEnabled(false);
                    binding.startButton.setText(getString(R.string.stop_test));
                    binding.tvSmileTestCount.setText(smile_count + "");
                    binding.tvScanDataCount.setText(scan_data_count + "");
                    binding.tvAlreadyTestTime.setText(totalTime + "");
                    break;
                case HANDLER_STOP_TEST:
                    binding.etSetTestTime.setEnabled(true);
                    binding.etTestInterval.setEnabled(true);
                    binding.startButton.setText(getString(R.string.start_test));
                    break;
                case HANDLER_SMILE_PAY_RESPONSE:
                    smile_count++;
                    binding.tvSmileTestCount.setText(String.valueOf(smile_count));
                    int code = msg.getData().getInt("code");
                    String fToken = msg.getData().getString("fToken");
                    String subCode = msg.getData().getString("subCode");
                    //刷脸成功
                    if (code == CODE_SUCCESS && fToken != null) {
                        promptText("刷脸成功，返回ftoken为:" + fToken);
                        Log.e(TAG, "刷脸成功，返回ftoken为:" + fToken);
                    } else if (code == CODE_EXIT) {
                        promptText(TXT_EXIT);
                        Log.e(TAG, TXT_EXIT);
                    } else if (code == CODE_TIMEOUT) {
                        promptText(TXT_TIMEOUT);
                        Log.e(TAG, TXT_TIMEOUT);
                    } else if (code == CODE_OTHER_PAY) {
                        promptText(TXT_OTHER_PAY);
                        Log.e(TAG, TXT_OTHER_PAY);
                    } else {
                        String txt = TXT_OTHER;
                        if (!TextUtils.isEmpty(subCode)) {
                            txt = txt + "(" + subCode + ")";
                        }
                        promptText(txt);
                        Log.e(TAG, txt);
                    }

                    isSmile = true;

                    if(isRunning) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                smilePay();
                            }

                        }, intervalTime);
                    }
                    break;
                case HANDLER_IOT_ACTIVITE_STATE:
                    String iotState = msg.getData().getString("iotState");
                    if(iotState != null){
                        if (iotState.equals("1")) {
                            binding.tvIotState.setText(getString(R.string.iot_state_disconnect));
                        }else if (iotState.equals("2")) {
                            binding.tvIotState.setText(getString(R.string.iot_state_connected));
                            binding.startButton.setEnabled(true);
                        }else {
                            binding.tvIotState.setText(getString(R.string.iot_state_unknown));
                        }
                    }else {
                        binding.tvIotState.setText(getString(R.string.iot_state_not_support));
                    }
                    break;

            }
            return false;
        }
    }).get());
    private PowerControl powerControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySmiledlhidscanBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        SDKUtil.getInstance(this).initSDK();
        powerControl = new PowerControl(this);
        powerControl.decodePower(1);

        if (!checkPermission()) {
            requestPermission();
        }

        if(isNetworkConnected(this)){
            binding.tvNetworkState.setText(getString(R.string.network_state_connected));
        }else {
            binding.tvNetworkState.setText(getString(R.string.network_state_disconnect));
        }

        binding.startButton.setEnabled(false);

        handler.post(new Runnable() {
            @Override
            public void run() {
                zoloz = Zoloz.getInstance(SmileHidScanPressureTestActivity.this);
                iOTSDK = IOTSDK.getInstance(SmileHidScanPressureTestActivity.this, new InitFinishCallback() {
                    @Override
                    public void initFinished(boolean b) {
                        Log.i(Constants.TAG, "IOT--初始化" + (b ? "成功" : "失败"));
                        String iotState = null;
                        if (b) {//"成功"
                            DeviceAPI api = APIManager.getInstance().getDeviceAPI();
                            String str = "\nDeviceStatus=" + api.getDeviceStatus() + (api.getDeviceStatus() == 1 ? "Online" : "offline");
                            str += "\nApdidToken=" + api.getApdidToken();
                            str += "\nDeviceId=" + api.getDeviceId();
                            str += "\nDeviceSn=" + api.getDeviceSn();
                            str += "\nSupplierId=" + api.getSupplierId();
                            Log.i(Constants.TAG, "IOT Meaages: " + str);
                            if (api.getDeviceId() != null && api.getDeviceId().length() > 0) {
                                iotState = "2";
                                Log.i(Constants.TAG, "IOT--已激活");
                            } else {
                                iotState = "1";
                                Log.i(Constants.TAG, "IOT--未激活");
                            }
                            Message message = new Message();
                            message.what = HANDLER_IOT_ACTIVITE_STATE;
                            Bundle bundle = new Bundle();
                            bundle.putString("iotState", iotState);
                            message.setData(bundle);
                            handler.sendMessage(message);
                        }
                    }
                });

        }});


        //计时器线程
        totalTimeScheduledExecutor.scheduleWithFixedDelay(() -> {
            if (isRunning) {
                handler.sendEmptyMessage(HANDLER_TEST_TOTAL_TIME_UPDATE);

                if (debugTime > 0 && totalTime == debugTime) {
                    stopPressTest();
                }
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);

        binding.startButton.setOnClickListener(v -> {
            if (isRunning) {
                stopPressTest();
                binding.startButton.setText(getString(R.string.start_test));
            }else {
                if (isNetworkConnected(this)) {
                    if (InstallUtil.isInstalled(this,"com.alipay.zoloz.smile")) {
                        startPressTest();
                    }else {
                        showNotSupportSmileDialog();
                    }
                } else {
                    startActivityForResult(new Intent(Settings.ACTION_WIFI_SETTINGS), WIFI_SETTINGS_REQUEST_CODE);
                }
            }

        });

        mKeyEventResolver = new KeyEventResolver(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void stopPressTest() {
        isRunning = false;
        handler.sendEmptyMessage(HANDLER_STOP_TEST);
    }

    private void startPressTest() {
        totalTime = 0;
        smile_count = 0;
        scan_count = 0;
        scan_data_count = 0;
        //设置测试时间
        String strDebugTime = binding.etSetTestTime.getText().toString();
        if (!strDebugTime.equals("") && !strDebugTime.equals("0")) {
            debugTime = Long.parseLong(strDebugTime);
        }

        String interval = binding.etTestInterval.getText().toString();
        if(!interval.isEmpty()){
            intervalTime = Integer.parseInt(interval);
        }

        isRunning = true;
        handler.sendEmptyMessage(HANDLER_START_TEST);

        smilePay();

    }

    private final BroadcastReceiver screenOnOffReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USER_PRESENT.equals(action)) {
                // 解锁屏幕后打开模组
                SoftEngine.getInstance().open();
            } else if (ACTION_SCREEN_OFF.equals(action)) {
                // 屏幕熄灭时关闭模组
                SoftEngine.getInstance().close();
            }
        }
    };

    /**
     * 截获按键事件.发给ScanGunKeyEventHelper
     */
    @Override
    public boolean dispatchKeyEvent(android.view.KeyEvent event) {
        //要是重虚拟键盘输入怎不拦截
        if ("Virtual".equals(event.getDevice().getName())) {
            return super.dispatchKeyEvent(event);
        }
        mKeyEventResolver.analysisKeyEvent(event);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        powerControl.decodePower(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopPressTest();
        mKeyEventResolver.onDestroy();
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int result2 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // For Android 11 and above
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s", getApplicationContext().getPackageName())));
                startActivityForResult(intent, PERMISSION_REQUEST_CODE);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, PERMISSION_REQUEST_CODE);
            }
        } else {
            // For below Android 11
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == WIFI_SETTINGS_REQUEST_CODE) {
            if (isNetworkConnected(this)) {
                binding.tvNetworkState.setText(getString(R.string.network_state_connected));
            } else {
                showWifiNotConnectedDialog();
                binding.tvNetworkState.setText(getString(R.string.network_state_disconnect));
            }
        }
    }

    private boolean isWifiConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return networkInfo != null && networkInfo.isConnected();
    }

    public boolean isNetworkConnected(Context context) {
        //获取连接管理器
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        //获取网络信息
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        //判断是否有网络连接
        if(networkInfo==null){
            //无网络
            Log.e(TAG, "networkInfo is null");
            return false;
        }

        //获取网络状态
        NetworkInfo.State networkState=networkInfo.getState();
        if(networkState!= NetworkInfo.State.CONNECTED){
            //未连接
            Log.e(TAG, "networkState is not connected");
            return false;
        }

        //获取网络是否可用
        if(!networkInfo.isAvailable()){
            //不可用
            Log.e(TAG, "networkInfo is not available");
            return false;
        }

        //获取网络类型
        int networkType=networkInfo.getType();
        if(networkType==ConnectivityManager.TYPE_WIFI){
            //WiFi
            Log.d(TAG, "networkType is WIFI");
            return true;
        } else if(networkType==ConnectivityManager.TYPE_MOBILE){
            //移动数据
            Log.d(TAG, "networkType is MOBILE");
            //获取网络子类型
            int subtype=networkInfo.getSubtype();
            if(subtype== TelephonyManager.NETWORK_TYPE_LTE|subtype==TelephonyManager.NETWORK_TYPE_IWLAN){
                Log.d(TAG, "networkSubtype is LTE or IWLAN");
            }
            return true;
        }else if(networkType==ConnectivityManager.TYPE_ETHERNET){
            //有线网络
            Log.d(TAG, "networkType is ETHERNET");
            return true;
        }
        return false;
    }

    private void showWifiNotConnectedDialog() {
        new AlertDialog.Builder(this)
                .setTitle("未连接网络")
                .setMessage("请先连接网络后再开始测试。")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivityForResult(new Intent(Settings.ACTION_WIFI_SETTINGS), WIFI_SETTINGS_REQUEST_CODE);
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void showNotSupportSmileDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Simle")
                .setMessage("不支持Smile，请安装smile.")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new IOTActivateAsyncTask(SmileHidScanPressureTestActivity.this).execute();
                    }
                }).show();
    }

    @Override
    public void onScanSuccess(String barcode) {
        if (isRunning){
            scan_data_count++;
            binding.tvScanDataCount.setText(String.valueOf(scan_data_count));
        }
    }

    @Override
    public void onBackPress() {

    }

    private class IOTActivateAsyncTask extends AsyncTask<Void, Boolean, Boolean> {
        private Context mContext;
        private String iotState;

        IOTActivateAsyncTask(Context context) {
            mContext = context;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean isSucceed = false;

            binding.startButton.setEnabled(false);
            if(iotState == null){
                isSucceed = IOTSDK.activateIOT();
            }

            long startTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - startTime < 10000 && iotState == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                iotState = iOTSDK.getIOTState(SmileHidScanPressureTestActivity.this);
            }

            return isSucceed;
        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
            // 耗时操作完成后，进行UI操作（例如显示Toast）
            Toast.makeText(mContext, aVoid?"激活成功":"激活失败", Toast.LENGTH_SHORT).show();
            Message message = new Message();
            message.what = HANDLER_IOT_ACTIVITE_STATE;
            Bundle bundle = new Bundle();
            bundle.putString("iotState", iotState);
            message.setData(bundle);
            handler.sendMessage(message);
        }
    }

    private void sendMsg(String data, int flag) {
        Message msg = handler.obtainMessage(flag);
        Bundle bundle = new Bundle();
        bundle.putString("msg_str", data);
        msg.setData(bundle);
        msg.sendToTarget();
    }

    private Zoloz zoloz;
    static final String TXT_EXIT = "已退出刷脸支付";
    static final String TXT_TIMEOUT = "操作超时";
    static final String TXT_OTHER_PAY = "已退出刷脸支付";
    static final String TXT_OTHER = "抱歉未支付成功，请重新支付";

    // 值为"1000"调用成功
    // 值为"1003"用户选择退出
    // 值为"1004"超时
    // 值为"1005"用户选用其他支付方式
    static final int CODE_SUCCESS = 1000;
    static final int CODE_EXIT = 1003;
    static final int CODE_TIMEOUT = 1004;
    static final int CODE_OTHER_PAY = 1005;
    //刷脸支付相关
    static final String SMILEPAY_CODE_SUCCESS = "10000";

    public static final String KEY_INIT_RESP_NAME = "zim.init.resp";
    /**
     * 发起刷脸支付请求，先zolozGetMetaInfo获取本地app信息，然后调用服务端获取刷脸付协议.
     */
    protected void smilePay() {
        if(!isRunning){
            return;
        }
        zoloz.getMetaInfo(MerchantInfo.mockInfo(), new MetaInfoCallback() {
            @Override
            public void onMetaInfo(String metaInfo,Map<String, Object> extInfo) {
                if (TextUtils.isEmpty(metaInfo)) {
                    Log.e(TAG, "response is null");
                    promptText(TXT_OTHER);
                    return;
                }
                String subCode = (extInfo != null) ? (String)extInfo.get("subCode") : "";
                String subMsg = (extInfo != null) ? (String)extInfo.get("subMsg") : "";
                //获取metainfo成功
                Log.i(TAG, "metanfo is:" + metaInfo);

                //正式接入请上传metaInfo到服务端，不要忘记UrlEncode，使用服务端使用的sdk从服务端访问openapi获取zimId和zimInitClientData；
                AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                        MerchantInfo.appId,
                        MerchantInfo.appKey,
                        "json",
                        "utf-8",
                        null,
                        "RSA2");
                ZolozAuthenticationCustomerSmilepayInitializeRequest request
                        = new ZolozAuthenticationCustomerSmilepayInitializeRequest();
                request.setBizContent(metaInfo);

                //起一个异步线程发起网络请求
                alipayClient.execute(request,
                        new AlipayCallBack() {
                            @Override
                            public AlipayResponse onResponse(AlipayResponse response) {
                                if (response != null && SMILEPAY_CODE_SUCCESS.equals(response.getCode())) {
                                    try {
                                        ZolozAuthenticationCustomerSmilepayInitializeResponse zolozResponse
                                                = (ZolozAuthenticationCustomerSmilepayInitializeResponse) response;

                                        String result = zolozResponse.getResult();
                                        JSONObject resultJson = JSON.parseObject(result);
                                        String zimId = resultJson.getString("zimId");
                                        String zimInitClientData = resultJson.getString("zimInitClientData");
                                        //人脸调用
                                        smile(zimId, zimInitClientData);
                                    } catch (Exception e) {
                                        promptText(TXT_OTHER);
                                    }
                                } else {
                                    promptText(TXT_OTHER);
                                }
                                return null;
                            }
                        });

            }
        });
    }

    /**
     * 发起刷脸支付请求.
     *
     * @param zimId    刷脸付token，从服务端获取，不要mock传入
     * @param protocal 刷脸付协议，从服务端获取，不要mock传入
     */
    private void smile(String zimId, String protocal) {
        Map params = new HashMap();
        params.put(KEY_INIT_RESP_NAME, protocal);
        zoloz.verify(zimId, params, new VerifyCallback() {
            @Override
            public void onResponse(Smile2PayResponse smile2PayResponse) {
                if (smile2PayResponse == null) {
                    promptText(TXT_OTHER);
                    return;
                }
                int code = smile2PayResponse.getCode();
                String fToken = (String) smile2PayResponse.getFaceToken();
                String subCode = (String) smile2PayResponse.getSubCode();
                String msg = (String) smile2PayResponse.getSubMsg();
                Log.i(TAG, "ftoken is:" + fToken);

                Message msgObj = new Message();
                msgObj.what = HANDLER_SMILE_PAY_RESPONSE;
                Bundle bundle = new Bundle();
                bundle.putInt("code", code);
                bundle.putString("fToken", fToken);
                bundle.putString("subCode", subCode);
                bundle.putString("msg", msg);
                msgObj.setData(bundle);
                handler.sendMessage(msgObj);
            }

        });
    }

    private void promptText(final String txt) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SmileHidScanPressureTestActivity.this, txt, Toast.LENGTH_LONG).show();
            }
        });
    }

}