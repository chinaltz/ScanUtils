package cn.bingoogolapple.qrcode.core;

import android.graphics.PointF;

/**
 * 作者:王浩
 * 创建时间:2018/6/15
 * 描述:
 */
public class ScanResult {
    private  String barTypeName;

    String result;
    PointF[] resultPoints;

    public String getBarTypeName() {
        return barTypeName;
    }

    public void setBarTypeName(String barTypeName) {
        this.barTypeName = barTypeName;
    }

    public ScanResult(String result) {
        this.result = result;
    }

    public ScanResult(String result, PointF[] resultPoints) {
        this.result = result;
        this.resultPoints = resultPoints;
    }
}
