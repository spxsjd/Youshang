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
	 * Logback������������������
	 */
	Log("logback.xml"),

	/**
	 * ���������������������������
	 */
	Context("context.properties"),

	/**
	 * ������������������������
	 */
	Message("message.properties"),

	/**
	 * ���������������������
	 */
	DataSource("datasource.properties"),

	/**
	 * ������������������
	 */
	Test("test.properties"),

	/**
	 * ������������������������
	 */
	Task("task.properties"),

	/**
	 * 缓存配置
	 */
	Cache("cache.properties"),
	/**
	 * 上传配置
	 */
	Upload("upload.properties");

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
		Properties p = Configuration.getInstance().getConfiguration(
				this.fileName);
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
		Properties properties = Configuration.getInstance().getConfiguration(
				this.fileName);
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