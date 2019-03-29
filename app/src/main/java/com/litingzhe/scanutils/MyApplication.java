package com.litingzhe.scanutils;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.ActivityCompat;

import com.developer.androidutilslib.utils.ISAppUtils;
import com.developer.androidutilslib.utils.ISCrashUtils;
import com.developer.androidutilslib.utils.ISFileUtils;
import com.developer.androidutilslib.utils.ISLogUtils;
import com.developer.androidutilslib.utils.Utils;
import com.litingzhe.scanutils.db.DaoMaster;
import com.litingzhe.scanutils.db.DaoSession;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2019/3/28 下午8:19.
 * 类描述：
 */


public class MyApplication extends Application {

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        Utils.init(this);
        //        文件路径初始化

        this.instance = this;
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

        setDatabase();

    }

    /**
     * 设置greenDao
     */
    private void setDatabase() {

        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "mydb", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }


    public static Context getContext() {
        return instance;
    }

    public static MyApplication getInstance() {
        return instance;
    }

}
