/**
 * 
 */
package com.zoo.youshang.api;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.Form;
import org.springframework.beans.factory.annotation.Autowired;

import com.zoo.youshang.api.data.AddCommentRequest;
import com.zoo.youshang.api.data.Codes;
import com.zoo.youshang.api.error.ServiceAssert;
import com.zoo.youshang.entity.Comment;
import com.zoo.youshang.entity.CommentDegree;
import com.zoo.youshang.entity.MemberProfile;
import com.zoo.youshang.entity.TaskProfile;
import com.zoo.youshang.persistence.CommentMapper;
import com.zoo.youshang.persistence.MemberProfileMapper;
import com.zoo.youshang.persistence.TaskProfileMapper;

/**
 * @author sunpeng
 * 
 */
@Path("/comment")
@Produces(MediaType.APPLICATION_JSON)
public class CommentService {

	@Autowired
	private CommentMapper commentMapper;
	@Autowired
	private TaskProfileMapper taskProfileMapper;
	@Autowired
	private MemberProfileMapper memberProfileMapper;

	@POST
	public Comment addComment(@Form AddCommentRequest request) {
		TaskProfile taskProfile = taskProfileMapper.selectByPrimaryKey(request
				.getTaskId());
		ServiceAssert.notNull(taskProfile, Codes.TaskNotFound);

		Long commenterId = request.getCommenterId();
		Long assesseeId = (taskProfile.getSponsorId().equals(commenterId) ? assesseeId = taskProfile
				.getExecutorId() : taskProfile.getSponsorId());
		Comment comment = new Comment();
		comment.setTaskId(taskProfile.getId());
		comment.setAssesseeId(assesseeId);
		comment.setCommenterId(commenterId);
		comment.setDegree(request.getDegree());
		comment.setContents(request.getContents());
		commentMapper.insertSelective(comment);
		int sorce = computeSorceByDegree(request.getDegree());
		if (sorce != 0) {
			MemberProfile memberProfile = memberProfileMapper
					.selectByPrimaryKey(assesseeId);
			memberProfile.setScore(memberProfile.getScore() + sorce);
			memberProfileMapper.updateByPrimaryKeySelective(memberProfile);
		}
		return commentMapper.selectByPrimaryKey(comment.getId());
	}

	protected int computeSorceByDegree(Byte degree) {
		return degree.equals(CommentDegree.BAD) ? -1 : (degree
				.equals(CommentDegree.GOOD) ? 1 : 0);
	}
}
