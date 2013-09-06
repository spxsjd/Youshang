/**
 * 
 */
package com.zoo.youshang.api.injection;

import static org.jboss.resteasy.util.FindAnnotation.findAnnotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import javax.ws.rs.CookieParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.Encoded;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.Form;
import org.jboss.resteasy.annotations.Suspend;
import org.jboss.resteasy.core.ConstructorInjectorImpl;
import org.jboss.resteasy.core.ContextParameterInjector;
import org.jboss.resteasy.core.CookieParamInjector;
import org.jboss.resteasy.core.FormInjector;
import org.jboss.resteasy.core.FormParamInjector;
import org.jboss.resteasy.core.HeaderParamInjector;
import org.jboss.resteasy.core.MatrixParamInjector;
import org.jboss.resteasy.core.MessageBodyParameterInjector;
import org.jboss.resteasy.core.MethodInjectorImpl;
import org.jboss.resteasy.core.PathParamInjector;
import org.jboss.resteasy.core.PropertyInjectorImpl;
import org.jboss.resteasy.core.QueryParamInjector;
import org.jboss.resteasy.core.SuspendInjector;
import org.jboss.resteasy.core.ValueInjector;
import org.jboss.resteasy.spi.ConstructorInjector;
import org.jboss.resteasy.spi.InjectorFactory;
import org.jboss.resteasy.spi.MethodInjector;
import org.jboss.resteasy.spi.PropertyInjector;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

/**
 * @author sunpeng.peng
 * 
 */
@Provider
public class DefaultInjectorFactory implements InjectorFactory {

	private ResteasyProviderFactory providerFactory;

	public DefaultInjectorFactory(ResteasyProviderFactory factory) {
		this.providerFactory = factory;
	}

	@SuppressWarnings("rawtypes")
	public ConstructorInjector createConstructor(Constructor constructor) {
		return new ConstructorInjectorImpl(constructor, providerFactory);
	}

	@SuppressWarnings("rawtypes")
	public PropertyInjector createPropertyInjector(Class resourceClass) {
		return new PropertyInjectorImpl(resourceClass, providerFactory);
	}

	@SuppressWarnings("rawtypes")
	public MethodInjector createMethodInjector(Class root, Method method) {
		return new MethodInjectorImpl(root, method, providerFactory);
	}

	@SuppressWarnings("rawtypes")
	public ValueInjector createParameterExtractor(Class injectTargetClass, AccessibleObject injectTarget, Class type, Type genericType, Annotation[] annotations) {
		return createParameterExtractor(injectTargetClass, injectTarget, type, genericType, annotations, true);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ValueInjector createParameterExtractor(Class injectTargetClass, AccessibleObject injectTarget, Class type, Type genericType, Annotation[] annotations, boolean useDefault) {
		DefaultValue defaultValue = findAnnotation(annotations, DefaultValue.class);
		boolean encode = findAnnotation(annotations, Encoded.class) != null || injectTarget.isAnnotationPresent(Encoded.class) || type.isAnnotationPresent(Encoded.class);
		String defaultVal = null;
		if (defaultValue != null)
			defaultVal = defaultValue.value();

		QueryParam query;
		HeaderParam header;
		MatrixParam matrix;
		PathParam uriParam;
		CookieParam cookie;
		FormParam formParam;

		Suspend suspend;
		DateQueryParam dateQueryPatam;
		IntegerQueryParam integerQueryParam;
		IntegerPathParam integerPathParam;
		LongQueryParam longQueryParam;
		LongPathParam longPathParam;

		if ((query = findAnnotation(annotations, QueryParam.class)) != null) {
			return new QueryParamInjector(type, genericType, injectTarget, query.value(), defaultVal, encode, annotations, providerFactory);
		} else if ((dateQueryPatam = findAnnotation(annotations, DateQueryParam.class)) != null) {
			return new DateParameterInjector(type, genericType, injectTarget, dateQueryPatam, defaultVal, encode, annotations, providerFactory);
		} else if ((integerQueryParam = findAnnotation(annotations, IntegerQueryParam.class)) != null) {
			return new IntegerQueryParamInjector(type, genericType, injectTarget, integerQueryParam, defaultVal, encode, annotations, providerFactory);
		} else if ((integerPathParam = findAnnotation(annotations, IntegerPathParam.class)) != null) {
			return new IntegerPathParamInjector(type, genericType, injectTarget, integerPathParam, defaultVal, encode, annotations, providerFactory);
		} else if ((longQueryParam = findAnnotation(annotations, LongQueryParam.class)) != null) {
			return new LongQueryParamInjector(type, genericType, injectTarget, longQueryParam, defaultVal, encode, annotations, providerFactory);
		} else if ((longPathParam = findAnnotation(annotations, LongPathParam.class)) != null) {
			return new LongPathParamInjector(type, genericType, injectTarget, longPathParam, defaultVal, encode, annotations, providerFactory);
		} else if ((header = findAnnotation(annotations, HeaderParam.class)) != null) {
			return new HeaderParamInjector(type, genericType, injectTarget, header.value(), defaultVal, annotations, providerFactory);
		} else if ((formParam = findAnnotation(annotations, FormParam.class)) != null) {
			return new FormParamInjector(type, genericType, injectTarget, formParam.value(), defaultVal, annotations, providerFactory);
		} else if ((cookie = findAnnotation(annotations, CookieParam.class)) != null) {
			return new CookieParamInjector(type, genericType, injectTarget, cookie.value(), defaultVal, annotations, providerFactory);
		} else if ((uriParam = findAnnotation(annotations, PathParam.class)) != null) {
			return new PathParamInjector(type, genericType, injectTarget, uriParam.value(), defaultVal, encode, annotations, providerFactory);
		} else if (findAnnotation(annotations, Form.class) != null) {
			return new FormInjector(type, providerFactory);
		} else if ((matrix = findAnnotation(annotations, MatrixParam.class)) != null) {
			return new MatrixParamInjector(type, genericType, injectTarget, matrix.value(), defaultVal, annotations, providerFactory);
		} else if ((suspend = findAnnotation(annotations, Suspend.class)) != null) {
			return new SuspendInjector(suspend, type);
		} else if (findAnnotation(annotations, Context.class) != null) {
			return new ContextParameterInjector(type, providerFactory);
		} else if (useDefault) {
			return new MessageBodyParameterInjector(injectTargetClass, injectTarget, type, genericType, annotations, providerFactory);
		} else {
			return null;
		}
	}

}
