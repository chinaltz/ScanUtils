package com.litingzhe.scanutils.main.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.developer.androidutilslib.app.base.ISBaseActivity;
import com.developer.androidutilslib.utils.ISToastUtils;
import com.developer.androidutilslib.view.ISViewPager;
import com.litingzhe.scanutils.R;
import com.litingzhe.scanutils.main.adapter.MainFragmentAdapter;
import com.litingzhe.scanutils.main.fragment.QRFragment;
import com.litingzhe.scanutils.main.fragment.HistoryFragment;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeTabActivity extends ISBaseActivity {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.tab_line)
    View tabLine;
    @BindView(R.id.viewPager)
    ISViewPager viewPager;
    @BindView(R.id.mainView)
    RelativeLayout mainView;

    private String[] titleList = null;
    private int[] icons = new int[]{
            R.drawable.mainpage_home_nor_ic,
            R.drawable.mainpage_category_nor_ic

    };

    private int[] icons_press = new int[]{
            R.drawable.mainpage_home_pressed_ic,
            R.drawable.mainpage_category_pressed_ic

    };

    private HistoryFragment homeBaseUIFragment;
    private QRFragment netAndDbDataFragment;

    private ArrayList<Fragment> fragmentList;
    private boolean isExit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_tab);
        ButterKnife.bind(this);
        InitView();

    }

    //初始化View
    private void InitView() {

        viewPager.setOffscreenPageLimit(1);
        fragmentList = new ArrayList<Fragment>();

        homeBaseUIFragment = new HistoryFragment();

        netAndDbDataFragment = new QRFragment();


        fragmentList.add(netAndDbDataFragment);
        fragmentList.add(homeBaseUIFragment);


        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        titleList = new String[]{
                "扫描", "历史"
        };

        MainFragmentAdapter adapter = new MainFragmentAdapter(getSupportFragmentManager(), titleList, fragmentList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        //设置ViewPager 是否可以滑动
        viewPager.setPagingEnabled(false);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


                for (int i = 0; i < titleList.length; i++) {
                    TabLayout.Tab tab = tabLayout.getTabAt(i);
                    View view = tab.getCustomView();
                    ImageView img = (ImageView) view.findViewById(R.id.tab_icon);
                    TextView title = (TextView) view.findViewById(R.id.tab_title);

                    if (position == i) {
                        img.setImageResource(icons_press[i]);
                        title.setTextColor(getResources().getColor(R.color.mainColor));
                    } else {
                        img.setImageResource(icons[i]);
                        title.setTextColor(getResources().getColor(R.color.tab_unselect_textcolor));
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        //为TabLayout添加tab名称
        for (int i = 0; i < titleList.length; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(getTabView(i));
        }

        viewPager.setCurrentItem(0);
    }


    /**
     * 添加getTabView的方法，来进行自定义Tab的布局View
     *
     * @param position
     * @return
     */
    public View getTabView(int position) {
        LayoutInflater mInflater = LayoutInflater.from(this);
        View view = null;

        view = mInflater.inflate(R.layout.item_bottom_tab, null);
        AutoUtils.auto(view);

        TextView tv = (TextView) view.findViewById(R.id.tab_title);
        tv.setText(titleList[position]);
        ImageView img = (ImageView) view.findViewById(R.id.tab_icon);
        if (position == 0) {
            tv.setTextColor(getResources().getColor(R.color.mainColor));
            img.setImageResource(icons_press[position]);
        } else {
            tv.setTextColor(getResources().getColor(R.color.tab_unselect_textcolor));
            img.setImageResource(icons[position]);
        }
        return view;
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

                finish();

            }


            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


}
