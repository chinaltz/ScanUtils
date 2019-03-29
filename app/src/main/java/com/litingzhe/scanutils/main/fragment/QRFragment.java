package com.litingzhe.scanutils.main.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.developer.androidutilslib.utils.ISToastUtils;
import com.developer.androidutilslib.utils.permission.ISPermissionUtils;
import com.litingzhe.scanutils.MyApplication;
import com.litingzhe.scanutils.R;
import com.litingzhe.scanutils.db.BarCodeDataDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.qrcode.core.BarcodeType;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2018/6/16 下午9:09.
 * 类描述：
 */


public class QRFragment extends Fragment implements QRCodeView.Delegate {


    @BindView(R.id.transtantAdapterView)
    View transtantAdapterView;
    @BindView(R.id.nav_left_icon)
    ImageView navLeftIcon;
    @BindView(R.id.nav_left_text)
    TextView navLeftText;
    @BindView(R.id.nav_back)
    LinearLayout navBack;
    @BindView(R.id.nav_title)
    TextView navTitle;
    @BindView(R.id.nav_right_icon)
    ImageView navRightIcon;
    @BindView(R.id.nav_right_text)
    TextView navRightText;
    @BindView(R.id.nav_right)
    LinearLayout navRight;
    @BindView(R.id.zxingview)
    ZXingView zxingview;
    Unbinder unbinder;
    @BindView(R.id.flash)
    Button flash;
    @BindView(R.id.seletcImage)
    Button seletcImage;
    private View rootView;
    private FragmentActivity mContext;
    private boolean isOpen = false;
    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;
    private BarCodeDataDao barCodeDao;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mContext = getActivity();
        if (rootView == null) {

            rootView = inflater.inflate(R.layout.fragment_qrscan, null);


        }

        unbinder = ButterKnife.bind(this, rootView);
        zxingview.setDelegate(this);
        zxingview.getScanBoxView().setOnlyDecodeScanBoxArea(true);
        zxingview.setType(BarcodeType.ALL, null);


        zxingview.changeToScanQRCodeStyle();
        barCodeDao = MyApplication.getInstance().getDaoSession().getBarCodeDataDao();

        return rootView;


    }

    @Override
    public void onPause() {
        super.onPause();
        zxingview.stopCamera();
    }

    @Override
    public void onResume() {
        super.onResume();

        ISPermissionUtils.permission(Manifest.permission_group.CAMERA)
                .rationale(new ISPermissionUtils.OnRationaleListener() {
                    @Override
                    public void rationale(ShouldRequest shouldRequest) {
                        shouldRequest.again(true);
                    }
                })

                .callback(new ISPermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        zxingview.startCamera();

                        zxingview.startSpotAndShowRect();
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {

                    }

                }).request();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        zxingview.onDestroy();
        unbinder.unbind();

    }


    private void vibrate() {
        Vibrator vibrator = (Vibrator) mContext.getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }


    @Override
    public void onScanQRCodeSuccess(String result, String codeType) {
        vibrate();

        ISToastUtils.showShort("码数据"+result + "码类型"+codeType);
        zxingview.startSpot(); // 开始识别
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {
        // 这里是通过修改提示文案来展示环境是否过暗的状态，接入方也可以根据 isDark 的值来实现其他交互效果
        String tipText = zxingview.getScanBoxView().getTipText();
        String ambientBrightnessTip = "\n环境过暗，请打开闪光灯";
        if (isDark) {
            if (!tipText.contains(ambientBrightnessTip)) {
                zxingview.getScanBoxView().setTipText(tipText + ambientBrightnessTip);
            }
        } else {
            if (tipText.contains(ambientBrightnessTip)) {
                tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip));
                zxingview.getScanBoxView().setTipText(tipText);
            }
        }

    }

    @Override
    public void onScanQRCodeOpenCameraError() {


        ISToastUtils.showShort("打开相机失败");


    }


    @OnClick({R.id.flash, R.id.seletcImage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.flash:


                if (!isOpen) {
                    zxingview.openFlashlight();
                    isOpen = true;
                } else {
                    zxingview.closeFlashlight();
                    isOpen = false;
                }

                // 打开闪光灯

                break;
            case R.id.seletcImage:
                Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(getContext())
                        .cameraFileDir(null)
                        .maxChooseCount(1)
                        .selectedPhotos(null)
                        .pauseOnScroll(false)
                        .build();
                startActivityForResult(photoPickerIntent, REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY);
                break;
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY) {
            final String picturePath = BGAPhotoPickerActivity.getSelectedPhotos(data).get(0);
            // 本来就用到 QRCodeView 时可直接调 QRCodeView 的方法，走通用的回调
            zxingview.decodeQRCode(picturePath);


        }
    }
}
