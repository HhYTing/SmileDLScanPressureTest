package com.example.smiledlScanpressuretext;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class InstallUtil {

    public static boolean isInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            if (pinfo.get(i).packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }

    public static boolean saveApk(Context context, File apkFile, String assets) {
        if (!apkFile.exists()) {
            InputStream inputStream = null;
            FileOutputStream fos = null;
            byte[] tmp = new byte[1024];
            try {
                inputStream = context.getAssets().open(assets);
                fos = new FileOutputStream(apkFile);
                int length = 0;
                while ((length = inputStream.read(tmp)) > 0) {
                    fos.write(tmp, 0, length);
                }
                fos.flush();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                try {
                    if(inputStream!=null)
                        inputStream.close();
                    if(fos!=null)
                        fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            return true;
        }
    }

    public static void installApk(Context context, File apkFile){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        if(Build.VERSION.SDK_INT >= 29){
            Uri apkUri = FileProvider.getUriForFile(context, "com.telpo.auto_protest.fileprovider", apkFile); //与manifest中定义的provider中的authorities="包名.fileprovider"保持一致
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        }else{
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(apkFile);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        }
        context.startActivity(intent);

    }

}
