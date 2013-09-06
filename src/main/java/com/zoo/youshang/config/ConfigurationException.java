package com.zoo.youshang.config;

/**
 * The Exception on behalf of reading a configuration file.
 * 
 * @author sunpeng.peng
 * 
 */
public class ConfigurationException extends RuntimeException {

	private static final long serialVersionUID = 2925193690614511395L;

	private String configFile;

	public ConfigurationException(String configFile) {
		super("Read the config file '" + configFile + "' error.");
		this.configFile = configFile;
	}

	public ConfigurationException(String configFile, Throwable cause) {
		super("Read the config file '" + configFile + "' error.", cause);
		this.configFile = configFile;
	}

	public String getConfigFile() {
		return configFile;
	}

}
