package com.developer.androidutilslib.model;

/**
 * Copyright 李挺哲
 * 创建人：litingzhe
 * 邮箱：453971498@qq.com
 * Created by litingzhe on 2017/4/11 下午3:23.
 * Info CPU信息
 */

public class ISCPUInfo {

	public String User;

	public String System;

	public String IOW;

	public String IRQ;

	public ISCPUInfo() {
		super();
	}

	public ISCPUInfo(String user, String system, String iOW, String iRQ) {
		super();
		User = user;
		System = system;
		IOW = iOW;
		IRQ = iRQ;
	}

}
