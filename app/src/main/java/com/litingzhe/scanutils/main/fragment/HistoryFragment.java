package com.litingzhe.scanutils.main.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.litingzhe.scanutils.MyApplication;
import com.litingzhe.scanutils.R;
import com.litingzhe.scanutils.activity.BarCodeDetialActivity;
import com.litingzhe.scanutils.db.BarCodeDataDao;
import com.litingzhe.scanutils.main.adapter.HistoryAdapter;
import com.litingzhe.scanutils.model.BarCodeData;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static cn.bingoogolapple.baseadapter.BGABaseAdapterUtil.dp2px;


/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2019/3/28 下午8:40.
 * 类描述：
 */


public class HistoryFragment extends Fragment {


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
    @BindView(R.id.listView)
    SwipeMenuListView listView;
    private View rootView;
    private Unbinder unbinder;

    private ArrayList<BarCodeData> data = new ArrayList<>();
    private BarCodeDataDao barCodeDataDao;

    private HistoryAdapter adapter;

    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        barCodeDataDao = MyApplication.getInstance().getDaoSession().getBarCodeDataDao();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        if (rootView == null) {

            rootView = inflater.inflate(R.layout.fragment_history, null);
        }
        unbinder = ButterKnife.bind(this, rootView);
        mContext = getActivity();
        adapter = new HistoryAdapter(mContext, data);

        listView.setAdapter(adapter);
        navTitle.setText("历史记录");
        navRight.setVisibility(View.VISIBLE);
        navRightIcon.setVisibility(View.VISIBLE);
        navRightIcon.setImageResource(R.drawable.ic_delete);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {


                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // 设置 MenuCreator
        listView.setMenuCreator(creator);

        // 2. 设置侧滑按钮 监听事件
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                BarCodeData item = data.get(position);
                switch (index) {
                    case 0:

                        barCodeDataDao.delete(item);

                        break;

                }
                return false;
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {


                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("温馨提醒");
                builder.setMessage("您是否要删除本条扫码记录");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                        BarCodeData barCodeData = data.get(i);
                        barCodeDataDao.delete(barCodeData);
                        refreshNoteList();

                    }
                });
                builder.show();
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BarCodeData barCodeData = data.get(i);
                Intent intent = new Intent(mContext, BarCodeDetialActivity.class);
                intent.putExtra("result", barCodeData.getBarCode());

                intent.putExtra("codeType", barCodeData.getBarCodeType());
                startActivity(intent);
            }
        });


        return rootView;


    }

    @Override
    public void onResume() {
        super.onResume();
        refreshNoteList();
    }

    private void refreshNoteList() {

        data.clear();
        QueryBuilder<BarCodeData> qb = barCodeDataDao.queryBuilder();
        data.addAll(qb.list());
        adapter.notifyDataSetChanged();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.nav_right)
    public void onViewClicked() {

        if (data.size() == 0)
            return;
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("温馨提醒");
        builder.setMessage("您是否要删除所有的扫码记录");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
                barCodeDataDao.deleteAll();
                refreshNoteList();

            }
        });
        builder.show();


    }
}
