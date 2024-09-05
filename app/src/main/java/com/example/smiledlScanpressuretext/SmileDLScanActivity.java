package com.example.smiledlScanpressuretext;

import static android.content.Intent.ACTION_SCREEN_OFF;
import static android.content.Intent.ACTION_SCREEN_ON;
import static android.content.Intent.ACTION_USER_PRESENT;
import static com.dawn.decoderapijni.ServiceTools.MSG_INIT_DONE;
import static com.dawn.decoderapijni.ServiceTools.MSG_INIT_FIRMWARE_UPGRADE;
import static com.dawn.decoderapijni.SoftEngine.SCN_EVENT_DEC_SUCC;
import static com.dawn.decoderapijni.SoftEngine.SCN_EVENT_DEC_TIMEOUT;
import static com.dawn.decoderapijni.SoftEngine.SCN_EVENT_SCANNER_OVERHEAT;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
import com.alipay.zoloz.smile2pay.service.Zoloz;
import com.alipay.zoloz.smile2pay.service.ZolozCallback;
import com.dawn.decoderapijni.ServiceTools;
import com.dawn.decoderapijni.SoftEngine;
import com.example.smiledlScanpressuretext.databinding.ActivitySmiledlscanBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.telpo.wxpay.app.MerchantInfo;
import com.telpo.wxpay.app.api.alipayapi.api.AlipayCallBack;
import com.telpo.wxpay.app.api.alipayapi.api.AlipayClient;
import com.telpo.wxpay.app.api.alipayapi.api.AlipayResponse;
import com.telpo.wxpay.app.api.alipayapi.api.DefaultAlipayClient;
import com.telpo.wxpay.app.api.alipayapi.api.request.ZolozAuthenticationCustomerSmilepayInitializeRequest;
import com.telpo.wxpay.app.api.alipayapi.api.response.ZolozAuthenticationCustomerSmilepayInitializeResponse;

public class SmileDLScanActivity extends AppCompatActivity {

    private final int HANDLER_START_TEST = 1;
    private final int HANDLER_STOP_TEST = 2;
    private final int HANDLER_TEST_DISPLAY_DATA = 3;
    private final int HANDLER_TEST_TOTAL_TIME_UPDATE = 4;
    private final int HANDLER_ADD_NEW_MESSAGE = 5;
    private final int HANDLER_SMILE_PAY_RESPONSE = 6;
    private final int HANDLER_IOT_ACTIVITE_STATE = 7;

    private final int HANDLER_SCAN_NEW_MESSAGE = 8;
    private final int HANDLER_SCANNER_OVERHEAT = 9;
    private final int HANDLER_SCANNER_OTHER_ERROR = 10;

    private static final int SINGLE_MODE = 0;
    private static final int CONTINUOUS_MODE = 1;

    private static final String TAG = Constants.TAG;
    private boolean isRunning = false;
    private Thread writeThread;
    private ActivitySmiledlscanBinding binding;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private int totalTime = 0;  // 已经测试的时间
    private int count = 0;  // 已经测试的次数，成功次数
    private int scan_count = 0;  // 扫码头已经测试的次数，成功次数
    private int smile_count = 0;  // 刷脸已经测试的次数，成功次数
    private int allCcount = 0;  // 已经测试的总次数，成功次数
    private long debugTime = -1; // 设置测试的时间
    private int intervalTime = 0;   // 测试间隔时间
    private ScheduledExecutorService totalTimeScheduledExecutor = Executors.newSingleThreadScheduledExecutor();

    private IOTSDK iOTSDK;

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
                    binding.tvSmileTestCount.setText(smile_count + "  (ALL:" + allCcount + ")");
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
                    String code = msg.getData().getString("code");
                    String fToken = msg.getData().getString("fToken");
                    String subCode = msg.getData().getString("subCode");
                    //刷脸成功
                    if (CODE_SUCCESS.equalsIgnoreCase(code) && fToken != null) {
                        promptText("刷脸成功，返回ftoken为:" + fToken);
                    } else if (CODE_EXIT.equalsIgnoreCase(code)) {
                        promptText(TXT_EXIT);
                    } else if (CODE_TIMEOUT.equalsIgnoreCase(code)) {
                        promptText(TXT_TIMEOUT);
                    } else if (CODE_OTHER_PAY.equalsIgnoreCase(code)) {
                        promptText(TXT_OTHER_PAY);
                    } else {
                        String txt = TXT_OTHER;
                        if (!TextUtils.isEmpty(subCode)) {
                            txt = txt + "(" + subCode + ")";
                        }
                        promptText(txt);
                    }

                    isSmile = true;

                    if(isRunning) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
//                            startScan();
//                            isSmile = false;
                                smilePay();
                            }

                        }, intervalTime);
                    }
                    break;
                case HANDLER_IOT_ACTIVITE_STATE:
                    String iotState = msg.getData().getString("iotState");
                    binding.startButton.setEnabled(true);
                    if(iotState != null){
                        if (iotState.equals("1")) {
                            binding.tvIotState.setText(getString(R.string.iot_state_disconnect));
                        }else if (iotState.equals("2")) {
                            binding.tvIotState.setText(getString(R.string.iot_state_connected));
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
    private Intent scanIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySmiledlscanBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        if (!checkPermission()) {
            requestPermission();
        }

//        binding.startButton.setEnabled(false);

        if(isWifiConnected()){
            binding.tvNetworkState.setText(getString(R.string.network_state_connected));
        }else {
            binding.tvNetworkState.setText(getString(R.string.network_state_disconnect));
        }

        handler.post(new Runnable() {
            @Override
            public void run() {
                zoloz = com.alipay.zoloz.smile2pay.service.Zoloz.getInstance(SmileDLScanActivity.this);
                iOTSDK = IOTSDK.getInstance(SmileDLScanActivity.this, new InitFinishCallback() {
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
                if (isWifiConnected()) {
                    /*if (iOTSDK.getIOTState(SmileDLScanActivity.this).equals("2")){
                        binding.startButton.setText(getString(R.string.stop_test));
                        startPressTest();
                    }else {
                        showIOTNotActivitedDialog();
                    }*/
                    startPressTest();
                } else {
                    startActivityForResult(new Intent(Settings.ACTION_WIFI_SETTINGS), WIFI_SETTINGS_REQUEST_CODE);
                }
            }

        });

        File apkFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "DLScan.apk");
        if (!InstallUtil.isInstalled(this,"com.dawn.java")) {
            if (InstallUtil.saveApk(this,apkFile,"DLScan.apk")) {
                InstallUtil.installApk(this,apkFile);
            }
        }else{
            PackageManager packageManager = this.getPackageManager();
            scanIntent = packageManager.getLaunchIntentForPackage("com.dawn.java");
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        /*if (isRunning && !isSmile) {
            smilePay();
        }*/
    }

    private void stopPressTest() {
        isRunning = false;
        handler.sendEmptyMessage(HANDLER_STOP_TEST);
//        stopScan();
    }

    private void startPressTest() {
        totalTime = 0;
        smile_count = 0;
        scan_count = 0;
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

        if (InstallUtil.isInstalled(this,"com.alipay.zoloz.smile")) {
            smilePay();
        }else {
//            startScan();
        }

    }

    private void startScan() {
        if(!isRunning){
            return;
        }
        startActivity(scanIntent);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopPressTest();
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

    private String mapHexByteToPercentageRange(byte hexByte) {
        int[] percentageRange = new int[2];
        int value = (hexByte - 0x01) & 0xFF; // 将字节转换为0到255的整数值

        if(hexByte >= 0x01 && hexByte <= 0x0A) {
            // 计算百分比范围
            percentageRange[0] = value * 10; // 起始百分比
            percentageRange[1] = percentageRange[0] + 10; // 终止百分比

            String range = percentageRange[0] + "% - " + percentageRange[1] + "%";
            return range;
        }else if(hexByte == 0x0B) {
            return "> maximum";
        }

        return "unknown";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == WIFI_SETTINGS_REQUEST_CODE) {
            if (isWifiConnected()) {
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

    private void showWifiNotConnectedDialog() {
        new AlertDialog.Builder(this)
                .setTitle("WiFi未连接")
                .setMessage("请先连接WiFi后再开始测试。")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivityForResult(new Intent(Settings.ACTION_WIFI_SETTINGS), WIFI_SETTINGS_REQUEST_CODE);
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void showIOTNotActivitedDialog() {
        new AlertDialog.Builder(this)
                .setTitle("IOT未激活")
                .setMessage("是否激活IOT？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new IOTActivateAsyncTask(SmileDLScanActivity.this).execute();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
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

                iotState = iOTSDK.getIOTState(SmileDLScanActivity.this);
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
    static final String CODE_SUCCESS = "1000";
    static final String CODE_EXIT = "1003";
    static final String CODE_TIMEOUT = "1004";
    static final String CODE_OTHER_PAY = "1005";
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
        zoloz.zolozGetMetaInfo(MerchantInfo.mockInfo(), new ZolozCallback() {
            @Override
            public void response(Map smileToPayResponse) {
                if (smileToPayResponse == null) {
                    Log.i(Constants.TAG, "response is null");
                    promptText(TXT_OTHER);
                    return;
                }
                String code = (String) smileToPayResponse.get("code");
                String metaInfo = (String) smileToPayResponse.get("metainfo");
                //获取metainfo成功
                if (CODE_SUCCESS.equalsIgnoreCase(code) && metaInfo != null) {
                    Log.i(Constants.TAG, "metanfo is:" + metaInfo);

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
                } else {
                    promptText(TXT_OTHER);
                }
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
        zoloz.zolozVerify(zimId, params, new ZolozCallback() {
            @Override
            public void response(final Map smileToPayResponse) {
                if (smileToPayResponse == null) {
                    promptText(TXT_OTHER);
                    return;
                }
                String code = (String) smileToPayResponse.get("code");
                String fToken = (String) smileToPayResponse.get("ftoken");
                String subCode = (String) smileToPayResponse.get("subCode");
                String msg = (String) smileToPayResponse.get("msg");
                Log.i(Constants.TAG, "ftoken is:" + fToken);

                Message msgObj = new Message();
                msgObj.what = HANDLER_SMILE_PAY_RESPONSE;
                Bundle bundle = new Bundle();
                bundle.putString("code", code);
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
                Toast.makeText(SmileDLScanActivity.this, txt, Toast.LENGTH_LONG).show();
            }
        });
    }

}