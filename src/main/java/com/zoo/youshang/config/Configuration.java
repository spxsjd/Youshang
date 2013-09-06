package com.zoo.youshang.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * System configuration loader class.use single instance to manage all
 * configuration information.
 * 
 * @author sunpeng.peng
 * 
 */
public class Configuration {

	private static final Logger logger = LoggerFactory
			.getLogger(Configuration.class);

	protected enum PathKey {
		ConfigPath("config.base.path") {
			public String getDefaultValue() {
				return System.getProperty("user.home")
						+ System.getProperty("file.separator") + ".youshang";

			}
		},
		LogPath("log.base.path") {
			public String getDefaultValue() {
				return System.getProperty("user.home")
						+ System.getProperty("file.separator")
						+ ".youshang/logs";

			}
		};

		private String key;

		private PathKey(String key) {
			this.key = key;
		}

		public String getKey() {
			return key;
		}

		public abstract String getDefaultValue();

	}

	private static final String UTF_8 = "UTF-8";
	private static Configuration instance;
	private String configDirectory;
	private ConcurrentHashMap<String, Properties> cachedConfigMap;

	private Configuration()
	{
		
	}
	public static Configuration getInstance() {
		return instance;
	}

	public static void init() {
		instance = new Configuration();
		instance.cachedConfigMap = new ConcurrentHashMap<String, Properties>();
		instance.configDirectory = checkAndSetDirectory(PathKey.ConfigPath);
		checkAndSetDirectory(PathKey.LogPath);
	}

	private static String checkAndSetDirectory(PathKey pathKey){
		String directory = System.getProperty(pathKey.getKey());
		if (directory == null) {
			directory = pathKey.getDefaultValue();
			System.setProperty(pathKey.getKey(), directory);
		}
		File file = new File(directory);
		if (!file.exists() || !file.isDirectory()) {
			throw new ConfigurationException(directory);
		}
		return directory;
	}


	/**
	 * Get the system configuration file base path
	 * 
	 * @return the base path dictionary
	 */
	public String getConfigBasePath() {
		return this.configDirectory;
	}

	/**
	 * Get the configuration file full path.
	 * 
	 * @param fileName
	 *            the file name
	 * @return the full path
	 */
	public String getConfigFullPath(String fileName) {
		String fullPath = this.getConfigBasePath()
				+ System.getProperty("file.separator") + fileName;
		return fullPath;
	}

	/**
	 * Get configuration Properties Object for one file.
	 * 
	 * @param fileName
	 *            the file name
	 * @return Properties Object
	 */
	protected Properties getConfiguration(String fileName) {
		Properties p = this.cachedConfigMap.get(fileName);
		if (p == null) {
			String fullPath = this.getConfigFullPath(fileName);
			p = loadProperties(fullPath);
			this.cachedConfigMap.putIfAbsent(fileName, p);
		}
		return p;
	}

	public static Properties loadProperties(String fileFullPath) {
		InputStream inStream = null;
		InputStreamReader reader = null;
		Properties p = new Properties();
		try {
			inStream = new FileInputStream(fileFullPath);
			if (isXMLConfiguration(fileFullPath)) {
				p.loadFromXML(inStream);
			} else {
				reader = new InputStreamReader(inStream, UTF_8);
				p.load(reader);
			}
			return p;
		} catch (IOException e) {
			throw new ConfigurationException(fileFullPath, e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error("Close config reader error.", e);
				}
			}
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					logger.error("Close config input stream error.", e);
				}
			}
		}
	}

	/**
	 * Check the configuration file is XML formation.
	 * 
	 * @param fileName
	 *            the file name
	 * @return boolean
	 */
	public static boolean isXMLConfiguration(String fileName) {

		int length = fileName.length();
		String endName = fileName.substring(length - 4, length);
		return ".xml".equalsIgnoreCase(endName);
	}
}
