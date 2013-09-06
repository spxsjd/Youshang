package com.zoo.youshang.api.injection;

import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jboss.resteasy.core.ValueInjector;
import org.jboss.resteasy.spi.BadRequestException;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.HttpResponse;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

/**
 * Use SimpleDateFormat to parse date string.
 * 
 * @see DateQueryParam
 * @see java.text.SimpleDateFormat
 * @author lixue.xue
 */
public class DateParameterInjector implements ValueInjector {

	private boolean encode;
	private String paramName;
	private String encodedName;
	private String format;
	private Date defaultValue = null;

	public DateParameterInjector(Class<?> type, Type genericType, AccessibleObject target, DateQueryParam dateQueryParam, String defaultValue, boolean encode, Annotation[] annotations, ResteasyProviderFactory factory) {
		this.encode = encode;
		this.format = dateQueryParam.format();
		this.paramName = dateQueryParam.value();
		try {
			this.encodedName = URLDecoder.decode(paramName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new BadRequestException("Unable to decode query string", e);
		}
		if (defaultValue != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				this.defaultValue = sdf.parse(defaultValue);
			} catch (Exception e) {
				throw new BadRequestException("Unable to parse the defaultValue '" + defaultValue + "' to java.util.Date", e);
			}
		}
	}

	@Override
	public Object inject(HttpRequest request, HttpResponse response) {
		String dateStr = request.getUri().getQueryParameters(!encode).getFirst(encodedName);

		if (dateStr == null) {
			return defaultValue;
		}

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			throw new FieldInjectionException(paramName, "Date", dateStr);
		}

	}

	@Override
	public Object inject() {
		throw new RuntimeException("It is illegal to inject a @DateQueryParam into a singleton");
	}

}
