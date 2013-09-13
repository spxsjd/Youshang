package com.zoo.youshang.entity;

import java.util.Date;

public class Comment {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.id
     *
     * @mbggenerated Fri Sep 13 11:32:43 CST 2013
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.commenter_id
     *
     * @mbggenerated Fri Sep 13 11:32:43 CST 2013
     */
    private Long commenterId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.assessee_id
     *
     * @mbggenerated Fri Sep 13 11:32:43 CST 2013
     */
    private Long assesseeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.task_id
     *
     * @mbggenerated Fri Sep 13 11:32:43 CST 2013
     */
    private Long taskId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.degree
     *
     * @mbggenerated Fri Sep 13 11:32:43 CST 2013
     */
    private Byte degree;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.contents
     *
     * @mbggenerated Fri Sep 13 11:32:43 CST 2013
     */
    private String contents;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column comment.create_time
     *
     * @mbggenerated Fri Sep 13 11:32:43 CST 2013
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.id
     *
     * @return the value of comment.id
     *
     * @mbggenerated Fri Sep 13 11:32:43 CST 2013
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.id
     *
     * @param id the value for comment.id
     *
     * @mbggenerated Fri Sep 13 11:32:43 CST 2013
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.commenter_id
     *
     * @return the value of comment.commenter_id
     *
     * @mbggenerated Fri Sep 13 11:32:43 CST 2013
     */
    public Long getCommenterId() {
        return commenterId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.commenter_id
     *
     * @param commenterId the value for comment.commenter_id
     *
     * @mbggenerated Fri Sep 13 11:32:43 CST 2013
     */
    public void setCommenterId(Long commenterId) {
        this.commenterId = commenterId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.assessee_id
     *
     * @return the value of comment.assessee_id
     *
     * @mbggenerated Fri Sep 13 11:32:43 CST 2013
     */
    public Long getAssesseeId() {
        return assesseeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.assessee_id
     *
     * @param assesseeId the value for comment.assessee_id
     *
     * @mbggenerated Fri Sep 13 11:32:43 CST 2013
     */
    public void setAssesseeId(Long assesseeId) {
        this.assesseeId = assesseeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.task_id
     *
     * @return the value of comment.task_id
     *
     * @mbggenerated Fri Sep 13 11:32:43 CST 2013
     */
    public Long getTaskId() {
        return taskId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.task_id
     *
     * @param taskId the value for comment.task_id
     *
     * @mbggenerated Fri Sep 13 11:32:43 CST 2013
     */
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.degree
     *
     * @return the value of comment.degree
     *
     * @mbggenerated Fri Sep 13 11:32:43 CST 2013
     */
    public Byte getDegree() {
        return degree;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.degree
     *
     * @param degree the value for comment.degree
     *
     * @mbggenerated Fri Sep 13 11:32:43 CST 2013
     */
    public void setDegree(Byte degree) {
        this.degree = degree;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.contents
     *
     * @return the value of comment.contents
     *
     * @mbggenerated Fri Sep 13 11:32:43 CST 2013
     */
    public String getContents() {
        return contents;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.contents
     *
     * @param contents the value for comment.contents
     *
     * @mbggenerated Fri Sep 13 11:32:43 CST 2013
     */
    public void setContents(String contents) {
        this.contents = contents;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column comment.create_time
     *
     * @return the value of comment.create_time
     *
     * @mbggenerated Fri Sep 13 11:32:43 CST 2013
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column comment.create_time
     *
     * @param createTime the value for comment.create_time
     *
     * @mbggenerated Fri Sep 13 11:32:43 CST 2013
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}