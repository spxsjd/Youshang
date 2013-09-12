/**
 * 
 */
package com.zoo.youshang.api;

import javax.ws.rs.Path;

import org.jboss.resteasy.annotations.Form;
import org.springframework.beans.factory.annotation.Autowired;

import com.zoo.youshang.api.data.AddCommentRequest;
import com.zoo.youshang.api.data.Codes;
import com.zoo.youshang.api.error.ServiceAssert;
import com.zoo.youshang.entity.Comment;
import com.zoo.youshang.entity.TaskProfile;
import com.zoo.youshang.persistence.CommentMapper;
import com.zoo.youshang.persistence.TaskProfileMapper;

/**
 * @author sunpeng
 * 
 */
@Path("/comment")
public class CommentService {

	@Autowired
	private CommentMapper commentMapper;
	@Autowired
	private TaskProfileMapper taskProfileMapper;

	public Comment addComment(@Form AddCommentRequest request) {
		TaskProfile taskProfile = taskProfileMapper.selectByPrimaryKey(request
				.getTaskId());
		ServiceAssert.notNull(taskProfile, Codes.TaskNotFound);

		Comment comment = new Comment();
		comment.setTaskId(taskProfile.getId());
		comment.setAssesseeId(taskProfile.getExecutorId());
		comment.setCommenterId(request.getCommenterId());
		comment.setDegree(request.getDegree());
		comment.setContents(request.getContents());
		commentMapper.insert(comment);

		return comment;
	}
}
