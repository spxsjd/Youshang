package com.zoo.youshang.persistence.paging;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zoo.youshang.persistence.paging.DialectFactory.DialectException;

/**
 * @author sunpeng
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PagingInterceptor implements Interceptor {
	private static final Logger logger = LoggerFactory.getLogger(PagingInterceptor.class);
	//private static final String physicalPagedStr = ConfigurationItem.Context.getConfigurationValue("persistence.physical.page", "true");
	//private static final boolean physicalPaged = Boolean.valueOf(physicalPagedStr);

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		//if (!physicalPaged) {
		//	return invocation.proceed();
		//}
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		MetaObject metaObject = MetaObject.forObject(statementHandler);

		RowBounds rowBounds = (RowBounds) metaObject.getValue(ROW_BOUNDS);
		if (rowBounds == null || rowBounds == RowBounds.DEFAULT) {
			return invocation.proceed();
		}

		String originalSql = (String) metaObject.getValue(BOUND_SQL);
		// DefaultParameterHandler parameterHandler = (DefaultParameterHandler) metaObject.getValue(PARAMETER_HANDLER);
		// Map<?, ?> parameterMap = (Map<?, ?>) parameterHandler.getParameterObject();
		// Object sidx = parameterMap.get(SIDX);
		// Object sord = parameterMap.get(SORD);
		// if (sidx != null && sord != null) {
		// originalSql = originalSql + ORDER_BY + sidx + " " + sord;
		// }

		Dialect dialect = null;
		Configuration configuration = (Configuration) metaObject.getValue(CONFIGURATION);
		try {
			String dialectName = configuration.getVariables().getProperty(DIALECT);
			dialect = DialectFactory.createDialect(dialectName);
		} catch (Exception e) {
			// ignore
		}
		if (dialect == null) {
			throw new DialectException("the value of the dialect property in configuration file is not defined : "
					+ configuration.getVariables().getProperty(DIALECT));
		}

		metaObject.setValue(BOUND_SQL, dialect.preparePagedBoundSql(originalSql, rowBounds.getOffset(), rowBounds.getLimit()));
		// do not forget
		metaObject.setValue(ROW_BOUNDS_OFFSET, RowBounds.NO_ROW_OFFSET);
		metaObject.setValue(ROW_BOUNDS_LIMIT, RowBounds.NO_ROW_LIMIT);
		if (logger.isDebugEnabled()) {
			BoundSql boundSql = statementHandler.getBoundSql();
			logger.debug("Generate paged SQL : " + boundSql.getSql());
		}
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this) ;
	}

	@Override
	public void setProperties(Properties properties) {
		logger.info("==> PagingInterceptor.setProperties(Properties properties)...");
	}

	private static final String ROW_BOUNDS = "delegate.rowBounds";
	private static final String ROW_BOUNDS_OFFSET = "delegate.rowBounds.offset";
	private static final String ROW_BOUNDS_LIMIT = "delegate.rowBounds.limit";
	// private static final String PARAMETER_HANDLER = "delegate.parameterHandler";
	private static final String BOUND_SQL = "delegate.boundSql.sql";
	private static final String CONFIGURATION = "delegate.configuration";
	// private static final String ORDER_BY = " order by ";
	// private static final String SIDX = "_sidx";
	// private static final String SORD = "_sord";
	private static final String DIALECT = "dialect";
}
