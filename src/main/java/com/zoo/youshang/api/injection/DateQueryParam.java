package com.zoo.youshang.api.injection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Define and use SimpleDateFormat to parse parameter. Make sure the return value's type is Date.<br />
 * And you can change date format like:<br />
 * <code>@DateQueryParam(value="Date", format="yyyy-MM-dd")</code>
 * 
 * @see DateParameterInjector
 * @see java.text.SimpleDateFormat
 * @author lixue.xue
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DateQueryParam {

	String value();

	String format() default "yyyy-MM-dd HH:mm:ss";
}
