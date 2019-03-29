package com.litingzhe.scanutils.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/5/24 下午1:48.
 * 类描述：
 */


@Entity
public class BarCodeData {


    @Id(autoincrement = true)
    private Long barCodeId = null;


    private String barCode;


    private String barCodeType;


    private Date creatDate;


    @Generated(hash = 1908968044)
    public BarCodeData(Long barCodeId, String barCode, String barCodeType,
            Date creatDate) {
        this.barCodeId = barCodeId;
        this.barCode = barCode;
        this.barCodeType = barCodeType;
        this.creatDate = creatDate;
    }


    @Generated(hash = 798862456)
    public BarCodeData() {
    }


    public Long getBarCodeId() {
        return this.barCodeId;
    }


    public void setBarCodeId(Long barCodeId) {
        this.barCodeId = barCodeId;
    }


    public String getBarCode() {
        return this.barCode;
    }


    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }


    public String getBarCodeType() {
        return this.barCodeType;
    }


    public void setBarCodeType(String barCodeType) {
        this.barCodeType = barCodeType;
    }


    public Date getCreatDate() {
        return this.creatDate;
    }


    public void setCreatDate(Date creatDate) {
        this.creatDate = creatDate;
    }

  





}
