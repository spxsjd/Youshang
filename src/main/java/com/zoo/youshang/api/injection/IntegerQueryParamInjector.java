package com.zoo.youshang.api.injection;

import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Type;
import java.net.URLDecoder;

import org.jboss.resteasy.core.ValueInjector;
import org.jboss.resteasy.spi.BadRequestException;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.HttpResponse;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

public class IntegerQueryParamInjector implements ValueInjector {

	private boolean encode;
	private String paramName;
	private String encodedName;
	private Integer defaultValue = null;

	public IntegerQueryParamInjector(Class<?> type, Type genericType, AccessibleObject target, IntegerQueryParam queryParam, String defaultValue, boolean encode, Annotation[] annotations, ResteasyProviderFactory factory) {
		this.encode = encode;
		this.paramName = queryParam.value();

		try {
			this.encodedName = URLDecoder.decode(paramName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new BadRequestException("Unable to decode query string", e);
		}
		if (defaultValue != null) {
			try {
				this.defaultValue = Integer.parseInt(defaultValue);
			} catch (Exception e) {
				throw new BadRequestException("Unable to parse the defaultValue '" + defaultValue + "' to integer", e);
			}
		}
	}

	@Override
	public Object inject(HttpRequest request, HttpResponse response) {
		String integerStr;
		if (encode) {
			integerStr = request.getUri().getQueryParameters(false).getFirst(encodedName);
		} else {
			integerStr = request.getUri().getQueryParameters().getFirst(paramName);
		}

		if (integerStr == null) {
			return defaultValue;
		}

		try {
			return Integer.parseInt(integerStr);
		} catch (NumberFormatException e) {
			throw new FieldInjectionException(paramName, "integer", integerStr);
		}
	}

	@Override
	public Object inject() {
		throw new RuntimeException("It is illegal to inject a @IntegerQueryParam into a singleton");
	}

}
