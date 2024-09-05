package com.example.smiledlScanpressuretext;

import android.content.Context;
import android.util.Log;

import com.alipay.iot.sdk.APIManager;
import com.alipay.iot.sdk.InitFinishCallback;
import com.alipay.iot.sdk.device.DeviceAPI;

public class IOTSDK {

//    private static String iotStatus = null;
    private Context mContext;

    private static class IOTSDKHolder {
        private static final IOTSDK instance = new IOTSDK();
    }
    public static IOTSDK getInstance(Context context, InitFinishCallback callback) {
        IOTSDKHolder.instance.initIOT(context, callback);
        return IOTSDKHolder.instance;
    }
    public static boolean activateIOT() {
        int result = -1;
        result = ShellUtils.execCommand("pm enable com.alipay.iot.master", false).result;
        result += ShellUtils.execCommand("pm enable com.alipay.iot.service", false).result;
        if(result == 0){
            return true;
        }
        return false;
    }

    public void initIOT(Context context, InitFinishCallback callback) {
        if (InstallUtil.isInstalled(context, "com.alipay.iot.profile")) {
            try {
                APIManager.getInstance().initialize(context, "2088611308670178", callback);
            } catch (Exception E) {
                E.printStackTrace();
            }
        }else {
            callback.initFinished(false);
        }
    }


    /**
     * 获取IOT状态
     * @param context
     * @return
     */
    public String getIOTState(Context context) {
        String iotStatus = null;
        if (InstallUtil.isInstalled(context, "com.alipay.iot.profile")) {
            DeviceAPI api = APIManager.getInstance().getDeviceAPI();
            if (api.getDeviceId() != null && api.getDeviceId().length() > 0) {
                iotStatus = "2";
                Log.i(Constants.TAG, "IOT--已激活");
            } else {
                iotStatus = "1";
                Log.i(Constants.TAG, "IOT--未激活");
            }
        }
        return iotStatus;
    }
}
