/**
 * 
 */
package com.zoo.youshang.api;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.ibatis.session.RowBounds;
import org.jboss.resteasy.annotations.Form;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zoo.youshang.api.data.Codes;
import com.zoo.youshang.api.data.PublishTaskRequest;
import com.zoo.youshang.api.data.SearchTaskRequest;
import com.zoo.youshang.api.error.ServiceAssert;
import com.zoo.youshang.api.error.ServiceBizException;
import com.zoo.youshang.api.protocol.FormFileUploadHelper;
import com.zoo.youshang.config.Configuration;
import com.zoo.youshang.config.ConfigurationItem;
import com.zoo.youshang.entity.TaskProfile;
import com.zoo.youshang.entity.TaskProfileExample;
import com.zoo.youshang.entity.TaskProfileExample.Criteria;
import com.zoo.youshang.entity.TaskStatus;
import com.zoo.youshang.persistence.TaskProfileMapper;

/**
 * @author sunpeng
 * 
 */
@Path("/task")
public class TaskService {

	private static final Logger logger = LoggerFactory
			.getLogger(TaskService.class);
	@Autowired
	private TaskProfileMapper taskProfileMapper;

	private String mediumBasicPath;
	private List<String> mediumTypes;

	public TaskService() {
		String defaultMediumPath = Configuration.getInstance()
				.getConfigFullPath("tasks");
		this.mediumBasicPath = ConfigurationItem.Upload.getConfigurationValue(
				"upload.task.medium.path", defaultMediumPath);
		File file = new File(this.mediumBasicPath);
		if (!file.exists()) {
			file.mkdirs();
		}

		String[] types = ConfigurationItem.Upload
				.getConfigurationValue("upload.task.medium.types",
						".jpg,.gif,.png").toLowerCase().split(",");
		this.mediumTypes = Arrays.asList(types);

	}

	@GET
	@Path("/search")
	public List<TaskProfile> listTasks(@Form SearchTaskRequest request) {
		TaskProfileExample example = new TaskProfileExample();
		Criteria criteria = example.createCriteria();
		if (request.getStatus() != null) {
			criteria.andStatusEqualTo(request.getStatus());
		}
		if (request.getSponsorId() != null) {
			criteria.andSponsorIdEqualTo(request.getSponsorId());
		}
		if (request.getExecutorId() != null) {
			criteria.andExecutorIdEqualTo(request.getExecutorId());
		}
		example.setOrderByClause("update_time desc");
		RowBounds rowBounds = new RowBounds(request.getOffset(),
				request.getLimit());
		return taskProfileMapper.selectByExampleWithRowbounds(example,
				rowBounds);

	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public TaskProfile publishTask(@Form PublishTaskRequest request,
			MultipartFormDataInput formDataInput) {

		logger.debug("Publish new task: " + request.toString());
		final TaskProfile profile = new TaskProfile();
		profile.setSponsorId(request.getSponsorId());
		profile.setStatus(TaskStatus.PENDING);
		profile.setReward(BigDecimal.valueOf(request.getReward()));
		profile.setLocation(request.getLocation());
		profile.setEndTime(request.getEndTime());
		profile.setDescription(request.getDescription());
		profile.setCreateTime(new Date());
		taskProfileMapper.insert(profile);

		String taskMediumDirectory = this.mediumBasicPath
				+ System.getProperty("file.separator")
				+ profile.getId().toString();
		(new File(taskMediumDirectory)).mkdir();

		try {
			Map<String, List<InputPart>> uploadForm = formDataInput
					.getFormDataMap();// 提交的form表单
			List<InputPart> inputParts;// 从form表单中获取name="file"的元素内容
			if ((inputParts = uploadForm.get("photo")) != null
					&& !inputParts.isEmpty()) {
				String photoFile = saveTaskMediaFile(inputParts.get(0),
						taskMediumDirectory, profile.getId());
				profile.setPhotoPath(photoFile);

			}
			if ((inputParts = uploadForm.get("record")) != null
					&& !inputParts.isEmpty()) {
				String recordFile = saveTaskMediaFile(inputParts.get(0),
						taskMediumDirectory, profile.getId());
				profile.setRecordPath(recordFile);
			}
			taskProfileMapper.updateByPrimaryKey(profile);
		} catch (Exception e) {
			logger.error("Save task medium file error.", e);
			taskProfileMapper.deleteByPrimaryKey(profile.getId());
			throw new ServiceBizException(Codes.TaskMediumSaveFailure);
		}
		return profile;
	}

	protected String saveTaskMediaFile(InputPart inputPart,
			String taskMediumDirectory, Long taskId) throws IOException {
		String fileType = FormFileUploadHelper.getFileType(inputPart);
		ServiceAssert.isTrue(this.mediumTypes.contains(fileType),
				Codes.TaskMediumTypeInvalid);
		String fileName = taskId.toString() + fileType;
		String savedFilePath = taskMediumDirectory
				+ System.getProperty("file.separator") + fileName;
		FormFileUploadHelper.saveFile(inputPart, savedFilePath);
		return fileName;

	}

	@GET
	public TaskProfile detailTask(@QueryParam("id") Long id) {
		return taskProfileMapper.selectByPrimaryKey(id);
	}

	@PUT
	public void adoptTask(@QueryParam("id") Long id,
			@QueryParam("executorId") Long executorId) {
		TaskProfile profile = new TaskProfile();
		profile.setId(id);
		profile.setExecutorId(executorId);
		profile.setStatus(TaskStatus.EXECUTING);

		TaskProfileExample example = new TaskProfileExample();
		example.createCriteria().andIdEqualTo(id).andExecutorIdIsNull();
		int count = taskProfileMapper
				.updateByExampleSelective(profile, example);
		ServiceAssert.isTrue(count > 0, Codes.TaskHasBeenAdopted);
	}

	@DELETE
	public void deleteTask(@QueryParam("id") Long id,
			@QueryParam("sponsorId") Long sponsorId) {

	}

	@PUT
	@Path("/finish")
	public void finishTask(@QueryParam("id") Long id,
			@QueryParam("executorId") Long executorId) {
		TaskProfile profile = new TaskProfile();
		profile.setId(id);
		profile.setStatus(TaskStatus.FINISH);

		taskProfileMapper.updateByPrimaryKeySelective(profile);
	}

}
