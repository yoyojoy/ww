package com.cs.model.system;

import com.cs.model.BaseEntity;

/**
 * 系统配置
 * @author yangjj
 */
public class SysConfig extends BaseEntity {

	private static final long serialVersionUID = -5166009101370183395L;

	private String sysKey;
	private String sysValue;
	private String description;
	private boolean state;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean getState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public String getSysKey() {
		return sysKey;
	}
	public void setSysKey(String sysKey) {
		this.sysKey = sysKey;
	}
	public String getSysValue() {
		return sysValue;
	}
	public void setSysValue(String sysValue) {
		this.sysValue = sysValue;
	}
	@Override
	public String toString() {
		return "SysConfig [sysKey=" + sysKey + ", sysValue=" + sysValue
				+ ", description=" + description + ", state=" + state + "]";
	}
}
