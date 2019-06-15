package com.springboot.sample.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class Result implements Serializable {
	public static final String SUCCESS = "1";
	public static final String FAIL = "0";
	
	private String ret;
	private String errMsg;
	private String errCode;
	private Object data;

	public Result() {
		super();
	}
	
	public Result(String ret, String errMsg, String errCode, Object data) {
		super();
		this.ret = ret;
		this.errMsg = errMsg;
		this.errCode = errCode;
		this.data = data;
	}
}
