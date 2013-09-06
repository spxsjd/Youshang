package com.zoo.youshang.persistence.paging;

import org.apache.commons.lang.StringUtils;

/**
 * @author sunpeng
 */
public class MysqlDialect implements Dialect {
	private static final String LIMIT = "limit";

	@Override
	public String preparePagedBoundSql(String boundSql, int offset, int limit) {
		return StringUtils.trim(boundSql) + " " + LIMIT + " " + offset + "," + limit;
	}

}
