package com.zoo.youshang.persistence.paging;

/**
 * @author sunpeng
 */
public interface Dialect {

	public String preparePagedBoundSql(String boundSql, int offset, int limit);

}
