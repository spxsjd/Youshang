/**
 * 
 */
package com.zoo.youshang.api.data;

import java.util.Date;

import javax.ws.rs.QueryParam;

/**
 * @author sunpeng
 * 
 */
public class PublishTaskRequest {

	@QueryParam("location")
	private String location;

	@QueryParam("sponsorId")
	private Long sponsorId;

	@QueryParam("endTime")
	private Long endTime;

	@QueryParam("reward")
	private Double reward;

	@QueryParam("description")
	private String description;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Long getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(Long sponsorId) {
		this.sponsorId = sponsorId;
	}

	public Long getEndTime() {
		return endTime;
	}

	public Date getEndDateTime() {
		return new Date(endTime);
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public Double getReward() {
		return reward;
	}

	public void setReward(Double reward) {
		this.reward = reward;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
