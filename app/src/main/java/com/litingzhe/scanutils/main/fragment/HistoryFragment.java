package com.litingzhe.scanutils.main.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.litingzhe.scanutils.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2019/3/28 下午8:40.
 * 类描述：
 */


public class HistoryFragment extends Fragment {


    private  View rootView;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        if (rootView == null) {

            rootView = inflater.inflate(R.layout.fragment_history, null);
        }
        unbinder = ButterKnife.bind(this, rootView);

        return rootView;



    }
}
