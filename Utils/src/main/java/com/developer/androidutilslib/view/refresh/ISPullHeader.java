
package com.developer.androidutilslib.view.refresh;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.developer.androidutilslib.utils.ISDateUtils;
import com.developer.androidutilslib.utils.ISViewUtils;
import com.isoftstone.androidutilslib.R;
//import ISViewUtils;


/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 * Info 下拉刷新的Header View类
 */
public class ISPullHeader extends ISListViewBaseHeader {

    /**
     * 主View.
     */
    private LinearLayout headerView;

    //	/** 箭头图标View. */
//	private ImageView arrowImageView;
//
//	/** 进度图标View. */
//	private ProgressBar headerProgressBar;
//
//	/** 文本提示的View. */
    private TextView tipsTextview;
//
//	/** 时间的View. */
//	private TextView headerTimeView;
//
//	/** 向上的动画. */
//	private Animation mRotateUpAnim;
//
//	/** 向下的动画. */
//	private Animation mRotateDownAnim;
//
//	/** 动画时间. */
//	private final int ROTATE_ANIM_DURATION = 180;


    /** 保存上一次的刷新时间. */
//	private String lastRefreshTime = null;

    /**
     * Header的高度.
     */
    private int headerHeight;

    /**
     * 初始化Header.
     *
     * @param context the context
     */
    public ISPullHeader(Context context) {
        super(context);
        initView();
    }

    /**
     * 初始化View.
     */
    private void initView() {

        //顶部刷新栏整体内容
        headerView = new LinearLayout(context);
        headerView.setOrientation(LinearLayout.VERTICAL);
        headerView.setGravity(Gravity.CENTER);

        headerView.setPadding(0, 10, 0, 10);
//		ISViewUtils.setPadding(headerView,);


        //顶部刷新栏文本内容
        LinearLayout headTextLayout = new LinearLayout(context);
        tipsTextview = new TextView(context);
        headTextLayout.setOrientation(LinearLayout.VERTICAL);
        headTextLayout.setGravity(Gravity.CENTER_VERTICAL);

        headTextLayout.setPadding(0, 0, 0, 0);
        LayoutParams layoutParamsWW2 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);



        headTextLayout.addView(tipsTextview, layoutParamsWW2);
        tipsTextview.setTextColor(Color.rgb(107, 107, 107));
        tipsTextview.setTextSize(15);

        LayoutParams layoutParamsWW3 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParamsWW3.gravity = Gravity.CENTER;
        layoutParamsWW3.rightMargin = 10;

        LinearLayout headerLayout = new LinearLayout(context);
        headerLayout.setOrientation(LinearLayout.HORIZONTAL);
        headerLayout.setGravity(Gravity.CENTER);

        headerLayout.addView(headTextLayout, layoutParamsWW3);

        LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.BOTTOM;


        ImageView imageView=new ImageView(context);
        imageView.setImageResource(R.drawable.loading_icon);
        LayoutParams imageViewlp = new LayoutParams(55, 55);
        imageViewlp.gravity = Gravity.CENTER;

        headerView.addView(imageView,imageViewlp);
        //添加大布局
        headerView.addView(headerLayout, lp);



        this.addView(headerView, lp);
        //获取View的高度
        ISViewUtils.measureView(this);
        headerHeight = this.getMeasuredHeight();


        setState(STATE_NORMAL);
    }

    /**
     * 设置状态.
     *
     * @param state the new state
     */
    public void setState(int state) {
        if (state == currentState) return;

        switch (state) {
            case STATE_NORMAL:


                tipsTextview.setText("下拉刷新");


                break;
            case STATE_READY:


                if (currentState != STATE_READY) {
                    tipsTextview.setText("松开刷新");

                }
                break;
            case STATE_REFRESHING:

                tipsTextview.setText("正在刷新...");
                break;
            default:
        }

        currentState = state;
    }

    /**
     * 设置header可见的高度.
     *
     * @param height the new visiable height
     */
    public void setVisiableHeight(int height) {
        if (height < 0) height = 0;
        LayoutParams lp = (LayoutParams) headerView.getLayoutParams();
        lp.height = height;
        headerView.setLayoutParams(lp);
    }

    /**
     * 获取header可见的高度.
     *
     * @return the visiable height
     */
    public int getVisiableHeight() {
        LayoutParams lp = (LayoutParams) headerView.getLayoutParams();
        return lp.height;
    }

    /**
     * 获取HeaderView.
     *
     * @return the header view
     */
    public LinearLayout getHeaderView() {
        return headerView;
    }


    /**
     * 获取header的高度.
     *
     * @return 高度
     */
    public int getHeaderHeight() {
        return headerHeight;
    }

    /**
     * 设置字体颜色.
     *
     * @param color the new text color
     */
    public void setTextColor(int color) {
        tipsTextview.setTextColor(color);
    }

    /**
     * 设置背景颜色.
     *
     * @param color the new background color
     */
    public void setBackgroundColor(int color) {
        headerView.setBackgroundColor(color);
    }


    /**
     * 得到当前状态.
     *
     * @return the state
     */
    public int getState() {
        return currentState;
    }

    /**
     * 设置提示状态文字的大小.
     *
     * @param size the new state text size
     */
    public void setStateTextSize(int size) {
        tipsTextview.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    /**
     * 设置提示时间文字的大小.
     *
     * @param size the new time text size
     */

    /**
     * 获取提示文字View
     *
     * @return
     */
    public TextView getTipsTextview() {
        return tipsTextview;
    }

    /**
     * 获取提示时间View
     * @return
     */


}
