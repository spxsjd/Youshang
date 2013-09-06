package com.zoo.youshang.api.injection;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Type;

import org.jboss.resteasy.core.ValueInjector;
import org.jboss.resteasy.spi.BadRequestException;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.HttpResponse;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

public class LongPathParamInjector implements ValueInjector {

	private boolean encode;
	private String paramName;
	private Long defaultValue = null;

	public LongPathParamInjector(Class<?> type, Type genericType, AccessibleObject target, LongPathParam pathParam, String defaultValue, boolean encode, Annotation[] annotations, ResteasyProviderFactory factory) {
		this.encode = encode;
		this.paramName = pathParam.value();

		if (defaultValue != null) {
			try {
				this.defaultValue = Long.parseLong(defaultValue);
			} catch (Exception e) {
				throw new BadRequestException("Unable to parse the defaultValue '" + defaultValue + "' to integer", e);
			}
		}
	}

	@Override
	public Object inject(HttpRequest request, HttpResponse response) {
		
		String value = request.getUri().getPathParameters(!encode).getFirst(paramName);
		if (value == null) {
			return defaultValue;
		}
		try {
			return Long.parseLong(value);
		} catch (NumberFormatException e) {
			throw new FieldInjectionException(paramName, "Long", value);
		}
	}

	@Override
	public Object inject() {
		throw new RuntimeException("It is illegal to inject a @IntegerQueryParam into a singleton");
	}

}
