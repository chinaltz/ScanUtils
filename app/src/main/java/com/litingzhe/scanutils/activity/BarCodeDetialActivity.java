package com.litingzhe.scanutils.activity;

import android.app.SearchManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.androidutilslib.app.base.ISBaseActivity;
import com.developer.androidutilslib.utils.ISStringUtils;
import com.litingzhe.scanutils.MyApplication;
import com.litingzhe.scanutils.R;
import com.litingzhe.scanutils.db.BarCodeDataDao;
import com.litingzhe.scanutils.model.BarCodeData;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BarCodeDetialActivity extends ISBaseActivity {

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
    @BindView(R.id.v_top)
    View vTop;
    @BindView(R.id.iv_codetype)
    ImageView ivCodetype;
    @BindView(R.id.tv_codetype)
    TextView tvCodetype;
    @BindView(R.id.bt_copy)
    Button btCopy;
    @BindView(R.id.top_card)
    CardView topCard;
    @BindView(R.id.card_search)
    CardView cardSearch;
    @BindView(R.id.card_share)
    CardView cardShare;
    @BindView(R.id.tv_code)
    TextView tvCode;
    private String barCode;
    private String barCodeType;
    private BarCodeDataDao barCodeDataDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code_detial);
        ButterKnife.bind(this);
        navTitle.setText("识别结果");
        navBack.setVisibility(View.VISIBLE);
        navLeftIcon.setVisibility(View.VISIBLE);
        barCodeDataDao = MyApplication.getInstance().getDaoSession().getBarCodeDataDao();

        barCode = getIntent().getStringExtra("result");
        barCodeType = getIntent().getStringExtra("codeType");

        BarCodeData barCodeData = new BarCodeData();
        barCodeData.setBarCode(barCode);
        barCodeData.setBarCodeType(barCodeType);
        barCodeData.setCreatDate(new Date());
        barCodeDataDao.insert(barCodeData);


        if (!ISStringUtils.isEmpty(barCodeType)) {
            if (barCodeType.equals("QR_CODE")) {
                ivCodetype.setImageResource(R.drawable.icon_qr_code);
                tvCodetype.setText("QRCODE");

            } else {
                ivCodetype.setImageResource(R.drawable.icon_product_code);
                tvCodetype.setText("ProductCode");
            }

        } else {

            if (barCode.startsWith("http")) {
                tvCodetype.setText("QRCODE");
                ivCodetype.setImageResource(R.drawable.icon_qr_code);
            } else {
                ivCodetype.setImageResource(R.drawable.icon_product_code);
                tvCodetype.setText("ProductCode");
            }


        }
        tvCode.setText(barCode + "");


    }

    @OnClick({R.id.nav_back, R.id.bt_copy, R.id.card_search, R.id.card_share})
    public void onViewClicked(View view) {

        Intent intent;
        switch (view.getId()) {
            case R.id.nav_back:

                finish();
                break;
            case R.id.bt_copy:


                // 获取系统剪贴板
                ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);

// 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
                ClipData clipData = ClipData.newPlainText(null, barCode);

// 把数据集设置（复制）到剪贴板
                clipboard.setPrimaryClip(clipData);

                Toast.makeText(this, "复制成功，可以发给朋友们了。", Toast.LENGTH_LONG).show();
                break;
            case R.id.card_search:
                intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, barCode);
                startActivity(intent);


                break;
            case R.id.card_share:

                intent = new Intent("android.intent.action.SEND");
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
                intent.putExtra(Intent.EXTRA_TEXT, barCode);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                break;
        }
    }
}
