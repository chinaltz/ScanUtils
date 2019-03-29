package com.litingzhe.scanutils.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.androidutilslib.utils.ISDateUtils;
import com.developer.androidutilslib.utils.ISStringUtils;
import com.litingzhe.scanutils.R;
import com.litingzhe.scanutils.model.BarCodeData;

import java.util.ArrayList;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2019/3/29 下午9:07.
 * 类描述：
 */


public class HistoryAdapter extends BaseAdapter {
    private ArrayList<BarCodeData> data;
    private Context mContext;

    public HistoryAdapter(Context mContext, ArrayList<BarCodeData> data) {
        this.data = data;
        this.mContext = mContext;
    }


    public ArrayList<BarCodeData> getData() {
        return data;
    }

    public void setData(ArrayList<BarCodeData> data) {
        this.data = data;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(
                    R.layout.item_sacn_history, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {

            holder = (ViewHolder) view.getTag();
        }
        BarCodeData barCodeData = data.get(i);

        holder.tvTime.setText(ISDateUtils.getStringByFormat(barCodeData.getCreatDate(),
                ISDateUtils.dateFormatYMDHM));

        if (barCodeData.getBarCode().startsWith("http")) {
            holder.ivType.setImageResource(R.drawable.icon_url_list);
            holder.tvCodetype.setText("URL");


        } else {
            holder.ivType.setImageResource(R.drawable.icon_bar_list);
            holder.tvCodetype.setText("Product");
        }
        holder.tvBarcode.setText(barCodeData.getBarCode() + "");

        return view;
    }


    static class ViewHolder {
        @BindView(R.id.iv_type)
        ImageView ivType;
        @BindView(R.id.tv_codetype)
        TextView tvCodetype;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_barcode)
        TextView tvBarcode;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
