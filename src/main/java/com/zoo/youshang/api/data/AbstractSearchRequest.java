/**
 * 
 */
package com.zoo.youshang.api.data;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

/**
 * @author sunpeng
 * 
 */
public abstract class AbstractSearchRequest {

	@QueryParam("offset")
	@DefaultValue("0")
	private Integer offset = 0;

	@QueryParam("limit")
	@DefaultValue("10")
	private Integer limit = 10;

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
