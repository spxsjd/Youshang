package com.zoo.youshang.config;

import java.util.Properties;

/**
 * The system configuration item enum.
 * 
 * @author sunpeng.peng
 * 
 */
public enum ConfigurationItem {

	/**
	 * Logback日志配置文件
	 */
	Log("logback.xml"),

	/**
	 * 上下文总体配置文件
	 */
	Context("context.properties"),

	/**
	 * 消息内容配置文件
	 */
	Message("message.properties"),
	
	/**
	 * 数据库配置文件
	 */
	DataSource("datasource.properties"),

	/**
	 * 测试配置文件
	 */
	Test("test.properties"),

	/**
	 * 定时任务配置文件
	 */
	Task("task.properties");

	private String fileName;

	private ConfigurationItem(String file) {
		this.fileName = file;
	}

	/**
	 * Get the configuration file name.
	 * 
	 * @return file name string
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Get the configuration file full path.
	 * 
	 * @return the full path string
	 */
	public String getConfigFullPath() {

		return Configuration.getInstance().getConfigFullPath(this.fileName);
	}

	/**
	 * Get the configuration properties object.
	 * 
	 * @return properties object
	 */
	public Properties getConfiguration() {
		Properties p = Configuration.getInstance().getConfiguration(this.fileName);
		return p;
	}

	/**
	 * Get one configuration property value.
	 * 
	 * @param name
	 *            property name
	 * @return property value
	 */
	public String getConfigurationValue(String name) {
		Properties properties = Configuration.getInstance().getConfiguration(this.fileName);
		if (properties == null) {
			return null;
		} else {
			return properties.getProperty(name);
		}

	}

	/**
	 * Get one configuration property value.
	 * 
	 * @param name
	 *            property name
	 * @param defaultValue
	 *            property default value
	 * @return property value
	 */
	public String getConfigurationValue(String name, String defaultValue) {
		String value = this.getConfigurationValue(name);
		if (value == null) {
			return defaultValue;
		} else {
			return value;
		}

	}

}