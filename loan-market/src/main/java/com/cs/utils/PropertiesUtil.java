package com.cs.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.Properties;

public class PropertiesUtil {
	Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
	
	private static final String SYSTEM_CONFIG = "config/system.properties";

	/**
	 * 系统整体配置
	 */
	private static Properties systemConfig = new Properties();

	static {
		InputStream inputStream = null;
		try {
			inputStream = PropertiesUtil.class.getResourceAsStream("/" + SYSTEM_CONFIG);
			systemConfig.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 属性文件所对应的属性对象变量
	 */
	private Properties props = new Properties();

	private File file;

	private long lastModified;

	public PropertiesUtil(String name) {
		build(name);
	}

	private void build(String name) {
		buildProp(name);
		//buildFile(name);
	}

	private void buildProp(String name) {
		props = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = this.getClass().getResourceAsStream("/" + name);
			if(inputStream != null){
				props.load(inputStream);
			}else{
				logger.error(name + "配置文件不存在");
			}
		} catch (IOException e) {
			logger.error("读取配置文件异常",e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getBackupPath(String name) {
		String dirPath = getProperty("properties.config.path");
		if (!StringUtils.isEmpty(dirPath)) {
			File file = new File(dirPath);
			if(file.exists()){
				return dirPath + File.separator + name;
			}else{
				if(file.mkdirs()){
					return dirPath + File.separator + name;
				}
			}
		}
		return null;
	}

	public void buildFile(String name) {
		String backPath = getBackupPath(name);
		OutputStreamWriter osw = null;
		if (!StringUtils.isEmpty(backPath)) {
			file = new File(backPath);
			try {
				osw = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
				props.store(osw, "备份");
				lastModified = file.lastModified();
			} catch (Exception e) {
				logger.error(name + "配置文件不存在", e);
			} finally {
				try {
					if (osw != null)
						osw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public Properties getProps() {
		if (this.isModified()) {
			InputStreamReader isr = null;
			try {
				isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
				props.load(isr);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (isr != null) {
						isr.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return props;
	}

	public boolean isModified() {
		if (file != null && file.exists()) {
			if (file.lastModified() == lastModified) {
				return true;
			}
		}
		return false;
	}

	public void setProps(Properties props) {
		this.props = props;
	}

	public String getProperty(String key) {
		String val = getProps().getProperty(key);
		if (StringUtils.isEmpty(val)) {
			val = systemConfig.getProperty(key);
			return StringUtils.isEmpty(val) ? val : "";
		}
		return val.trim();
	}
	
	public boolean contains (Object value){
		return getProps().contains(value);
	}
	
	public static Properties getSysConfig(){
		return systemConfig;
	}
	
	public static String getSysProperty(String key){
		return systemConfig.getProperty(key);
	}
}
