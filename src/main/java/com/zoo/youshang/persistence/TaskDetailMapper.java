package com.zoo.youshang.persistence;

import com.zoo.youshang.entity.TaskDetail;
import com.zoo.youshang.entity.TaskDetailExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface TaskDetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_detail
     *
     * @mbggenerated Sun Sep 15 16:10:34 CST 2013
     */
    int countByExample(TaskDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_detail
     *
     * @mbggenerated Sun Sep 15 16:10:34 CST 2013
     */
    List<TaskDetail> selectByExampleWithRowbounds(TaskDetailExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_detail
     *
     * @mbggenerated Sun Sep 15 16:10:34 CST 2013
     */
    List<TaskDetail> selectByExample(TaskDetailExample example);
}