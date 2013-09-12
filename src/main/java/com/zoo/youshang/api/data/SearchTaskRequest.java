/**
 * 
 */
package com.zoo.youshang.api.data;

import javax.ws.rs.QueryParam;

/**
 * @author sunpeng
 * 
 */
public class SearchTaskRequest extends AbstractSearchRequest {

	@QueryParam("status")
	private Byte status;

	@QueryParam("sponsorId")
	private Long sponsorId;

	@QueryParam("executorId")
	private Long executorId;

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Long getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(Long sponsorId) {
		this.sponsorId = sponsorId;
	}

	public Long getExecutorId() {
		return executorId;
	}

	public void setExecutorId(Long executorId) {
		this.executorId = executorId;
	}

}
