/**
 * 
 */
package com.zoo.youshang.api.data;

import javax.ws.rs.QueryParam;

/**
 * @author sunpeng
 * 
 */
public abstract class AbstractSearchRequest {

	@QueryParam("offset")
	private Integer offset;

	@QueryParam("limit")
	private Integer limit;

	
	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

}
