/**
 * 
 */
package com.zoo.youshang.api.error;

import java.util.Collection;

import com.zoo.youshang.api.data.ServiceCode;

/**
 * @author sunpeng
 * 
 */
public abstract class ServiceAssert {

	public interface Executor<T> {
		public T execute();
	}

	public static void notNull(Object obj, ServiceCode code)
			throws ServiceBizException {
		if (obj == null) {
			throw new ServiceBizException(code);
		}
	}

	public static <T extends Collection<?>> void notEmpty(T collection,
			ServiceCode code) throws ServiceBizException {
		if (collection == null || collection.isEmpty()) {
			throw new ServiceBizException(code);
		}
	}

	public static void isTrue(Boolean value, ServiceCode code) {
		if (value == null || !value.booleanValue()) {
			throw new ServiceBizException(code);
		}
	}

	public static <R> R checkExecutor(Executor<R> executor,
			Class<? extends Exception> clazz, ServiceCode code)
			throws ServiceBizException, UnkownServiceException {
		try {
			return executor.execute();
		} catch (Exception e) {
			if (clazz.isInstance(e)) {
				throw new ServiceBizException(code);
			} else {
				throw new UnkownServiceException(e);
			}
		}
	}

	public static <T extends Exception> void checkException(T e,
			Class<? extends Exception> clazz, ServiceCode code)
			throws ServiceBizException, UnkownServiceException {
		if (clazz.isInstance(e)) {
			throw new ServiceBizException(code);
		} else {
			throw new UnkownServiceException(e);
		}
	}
}
