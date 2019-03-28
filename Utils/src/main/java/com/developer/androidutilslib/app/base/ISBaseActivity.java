package com.developer.androidutilslib.app.base;


import android.content.Context;
import android.os.Bundle;

import com.developer.androidutilslib.utils.ISDensityUtils;
import com.zhy.autolayout.AutoLayoutActivity;


/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 * Info 所有Activity要继承这个父类，便于统一管理
 */
public abstract class ISBaseActivity extends AutoLayoutActivity {
    public Context mContext;
    private long lastClick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        mContext = this;
//        使用 今日头条适配方案
//        ISDensityUtils.setDefault(mContext);


    }


    /**
     * 判断是否快速点击
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    private boolean isFastClick() {
        long now = System.currentTimeMillis();
        if (now - lastClick >= 200) {
            lastClick = now;
            return false;
        }
        return true;
    }


}

