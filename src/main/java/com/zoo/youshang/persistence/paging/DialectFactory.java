package com.zoo.youshang.persistence.paging;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sunpeng
 */
public class DialectFactory {
	private static final ConcurrentHashMap<String, Dialect> DIALECTS = new ConcurrentHashMap<String, Dialect>();

	public static Dialect createDialect(String dialectName) {
		if (DIALECTS.containsKey(dialectName)) {
			return DIALECTS.get(dialectName);
		}
		try {
			Class<?> dialectClass = Class.forName(dialectName);
			DIALECTS.putIfAbsent(dialectName, (Dialect) dialectClass.newInstance());
		} catch (Exception e) {
			throw new DialectException("Fail to instantiate dialect class:" + dialectName, e);
		}
		return DIALECTS.get(dialectName);
	}

	public static class DialectException extends RuntimeException {
		private static final long serialVersionUID = 1312652770862229678L;

		public DialectException(String message) {
			super(message);
		}

		public DialectException(String message, Throwable e) {
			super(message, e);
		}
	}

}
