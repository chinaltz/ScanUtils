package com.litingzhe.scanutils;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.developer.androidutilslib.app.base.ISBaseActivity;
import com.developer.androidutilslib.utils.ISBarUtils;
import com.developer.androidutilslib.utils.ISToastUtils;
import com.developer.androidutilslib.utils.permission.ISPermissionUtils;
import com.litingzhe.scanutils.main.activity.HomeTabActivity;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午5:30.
 * 类描述：启动页面
 */


public class LanucherActivity extends ISBaseActivity {

    @BindView(R.id.lanuchImage)
    ImageView lanuchImage;
    @BindView(R.id.countButton)
    Button countButton;
    private MyCount myCount;

    private boolean isExit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lanucher);
        ButterKnife.bind(this);
        //初始化计时器

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ISBarUtils.setNavBarImmersive(this);
        }
        ISBarUtils.setStatusBarAlpha(this, 0);
//        initData();

        myCount = new MyCount(4000, 1000);
        //开启
        myCount.start();


        ISPermissionUtils.permission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .rationale(new ISPermissionUtils.OnRationaleListener() {
                    @Override
                    public void rationale(ShouldRequest shouldRequest) {

                    }
                })
                .callback(new ISPermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {

                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {

                    }
                })
                .request();


    }


    @OnClick(R.id.countButton)
    public void onViewClicked() {

        myCount.cancel();


        Intent in = new Intent();

        in.setClass(mContext, HomeTabActivity.class);


        startActivity(in);
        finish();

    }


    public class MyCount extends CountDownTimer {


        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            if (countButton.getVisibility() != View.VISIBLE) {
                countButton.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onFinish() {


            Intent in = new Intent();

            in.setClass(mContext, HomeTabActivity.class);

            startActivity(in);
            finish();

        }

        @Override
        public void onTick(long millisUntilFinished) {

            long second = (millisUntilFinished / 1000);
            countButton.setText((second + 1) + "s");

        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {


            if (isExit == false) {
                isExit = true;
                ISToastUtils.showShort("再按一次退出程序");
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        isExit = false;
                    }

                }, 2000);

            } else {

                myCount.cancel();
                finish();

            }


            return false;
        }
        return super.onKeyDown(keyCode, event);

    }


}
