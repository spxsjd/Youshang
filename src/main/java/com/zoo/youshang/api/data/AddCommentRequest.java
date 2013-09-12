/**
 * 
 */
package com.zoo.youshang.api.data;

import javax.ws.rs.QueryParam;

/**
 * @author sunpeng
 * 
 */
public class AddCommentRequest {

	@QueryParam("location")
	private Long taskId;
	
	@QueryParam("commenterId")
	private Long commenterId;
	
	@QueryParam("degree")
	private Byte degree;
	
	@QueryParam("contents")
	private String contents;

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getCommenterId() {
		return commenterId;
	}

	public void setCommenterId(Long commenterId) {
		this.commenterId = commenterId;
	}

	public Byte getDegree() {
		return degree;
	}

	public void setDegree(Byte degree) {
		this.degree = degree;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
	
	
}
