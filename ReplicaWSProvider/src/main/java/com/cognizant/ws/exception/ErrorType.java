package com.cognizant.ws.exception;

public class ErrorType {
private String desc;

private String msg;
@Override
public String toString() {
	return "ErrorType [desc=" + desc +  ", msg=" + msg + "]";
}
public String getDesc() {
	return desc;
}
public void setDesc(String desc) {
	this.desc = desc;
}

public String getMsg() {
	return msg;
}
public void setMsg(String msg) {
	this.msg = msg;
}

}
