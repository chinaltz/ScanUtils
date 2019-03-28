package com.litingzhe.scanutils;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.developer.androidutilslib.utils.ISAppUtils;
import com.developer.androidutilslib.utils.ISCrashUtils;
import com.developer.androidutilslib.utils.ISFileUtils;
import com.developer.androidutilslib.utils.ISLogUtils;
import com.developer.androidutilslib.utils.Utils;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2019/3/28 下午8:19.
 * 类描述：
 */


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Utils.init(this);
        //        文件路径初始化


        //崩溃日志 初始化
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ISFileUtils.initFileDir(getApplicationContext());
            ISCrashUtils.init(ISFileUtils.getCrashDir(this), new ISCrashUtils.OnCrashListener() {
                @Override
                public void onCrash(String crashInfo, Throwable e) {


                    ISLogUtils.e(crashInfo);

                    ISAppUtils.relaunchApp();
                }
            });
            return;
        }


    }
}
