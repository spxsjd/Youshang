/**
 * 
 */
package com.zoo.youshang.api.injection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.spi.StringConverter;

/**
 * @author sunpeng
 * 
 */
@Provider
public class DateStringConverter implements StringConverter<Date> {

	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jboss.resteasy.spi.StringConverter#fromString(java.lang.String)
	 */
	@Override
	public Date fromString(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			throw new FieldInjectionException(Date.class, str, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jboss.resteasy.spi.StringConverter#toString(java.lang.Object)
	 */
	@Override
	public String toString(Date value) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return sdf.format(value);
	}

}
