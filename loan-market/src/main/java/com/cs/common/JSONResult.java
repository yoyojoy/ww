package com.cs.common;


import java.io.Serializable;

/**
 * 返回的数据模型
 * 
 * @author yangqy
 *
 */
public class JSONResult implements Serializable{
	
	public String toString() {
		return "{data=" + data + ", status=" + status + ", msg="
				+ msg + "}";
	}

	private static final long serialVersionUID = 1L;

	/**
	 * 返回数据
	 */
	private Object data;

	/**
	 * 操作状态 0 失败 1 成功 2:平台中是失败,对于用户来说是成功(比如命中YIMEI让其结束认证)
	 */
	private int status;

	/**
	 * 返回消息
	 */
	private String msg;
	
	public JSONResult(){
		super();
	}
	
	public JSONResult(int status){
		this.status = status;
	}
	
	public JSONResult(int status, Object data){
		this.status = status;
		this.data = data;
	}
	
	public Object getData() {
		return data;
	}

	public JSONResult setData(Object data) {
		this.data = data;
		return this;
	}

	public int getStatus() {
		return status;
	}

	public JSONResult setStatus(int status) {
		this.status = status;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public JSONResult setMsg(String msg) {
		this.msg = msg;
		return this;
	}
	
	public JSONResult set(int status, Object data){
		this.status = status;
		this.data = data;
		return this;
	}
	
	public JSONResult set(String msg, Object data){
		this.msg = msg;
		this.data = data;
		return this;
	}
	
	public JSONResult set(int status, Object data, String msg){
		this.status = status;
		this.msg = msg;
		this.data = data;
		return this;
	}
}
