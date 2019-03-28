package com.developer.androidutilslib.model;



/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 * Info 圆形模型
 */
public class ISCircle {
	
	public ISPoint point;
	public double r;

	public ISCircle() {
		super();
	}

	public ISCircle(ISPoint point, double r) {
		super();
		this.point = point;
		this.r = r;
	}

	@Override
	public String toString() {
		return "(" + point.x + "," + point.y + "),r="+r;
	}

}
