/**
 * 
 */
package com.zoo.youshang.api;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FileUtils;
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
import com.zoo.youshang.entity.TaskDetail;
import com.zoo.youshang.entity.TaskDetailExample;
import com.zoo.youshang.entity.TaskProfile;
import com.zoo.youshang.entity.TaskProfileExample;
import com.zoo.youshang.entity.TaskStatus;
import com.zoo.youshang.persistence.TaskDetailMapper;
import com.zoo.youshang.persistence.TaskProfileMapper;

/**
 * @author sunpeng
 * 
 */
@Path("/task")
@Produces(MediaType.APPLICATION_JSON)
public class TaskService {

	private static final Logger logger = LoggerFactory
			.getLogger(TaskService.class);
	@Autowired
	private TaskProfileMapper taskProfileMapper;

	@Autowired
	private TaskDetailMapper taskDetailMapper;

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
		profile.setEndTime(request.getEndDateTime());
		profile.setDescription(request.getDescription());
		taskProfileMapper.insertSelective(profile);
		Long profileId = profile.getId();
		String taskMediumDirectory = this.mediumBasicPath
				+ System.getProperty("file.separator") + profileId.toString();
		(new File(taskMediumDirectory)).mkdir();

		try {
			TaskProfile updatedProfile = new TaskProfile();
			updatedProfile.setId(profileId);
			boolean shouldToUpdate = false;
			Map<String, List<InputPart>> uploadForm = formDataInput
					.getFormDataMap();// 提交的form表单
			List<InputPart> inputParts;// 从form表单中获取name="file"的元素内容
			if ((inputParts = uploadForm.get("photo")) != null
					&& !inputParts.isEmpty()) {
				String photoFile = saveTaskMediaFile(inputParts.get(0),
						taskMediumDirectory, profileId);
				updatedProfile.setPhotoPath(photoFile);
				shouldToUpdate = true;

			}
			if ((inputParts = uploadForm.get("record")) != null
					&& !inputParts.isEmpty()) {
				String recordFile = saveTaskMediaFile(inputParts.get(0),
						taskMediumDirectory, profileId);
				updatedProfile.setRecordPath(recordFile);
				shouldToUpdate = true;
			}
			if (shouldToUpdate) {
				taskProfileMapper.updateByPrimaryKeySelective(updatedProfile);
			}
		} catch (Exception e) {
			logger.error("Save task medium file error.", e);
			taskProfileMapper.deleteByPrimaryKey(profileId);
			throw new ServiceBizException(Codes.TaskMediumSaveFailure);
		}
		return taskProfileMapper.selectByPrimaryKey(profileId);
	}

	@GET
	@Path("/search")
	public List<TaskDetail> listTasks(@Form SearchTaskRequest request) {
		TaskDetailExample example = new TaskDetailExample();
		TaskDetailExample.Criteria criteria = example.createCriteria();
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
		return taskDetailMapper
				.selectByExampleWithRowbounds(example, rowBounds);

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
	public TaskDetail detailTask(@QueryParam("id") Long id) {
		TaskDetailExample example = new TaskDetailExample();
		example.createCriteria().andIdEqualTo(id);
		List<TaskDetail> list = taskDetailMapper.selectByExample(example);
		ServiceAssert.notEmpty(list, Codes.TaskNotFound);
		return list.get(0);
	}

	@DELETE
	public void deleteTask(@QueryParam("id") Long id,
			@QueryParam("sponsorId") Long sponsorId) {

	}

	@PUT
	@Path("/adopt")
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

	@PUT
	@Path("/finish")
	public void finishTask(@QueryParam("id") Long id) {
		TaskProfile profile = new TaskProfile();
		profile.setId(id);
		profile.setStatus(TaskStatus.CONFIRMING);

		TaskProfileExample example = new TaskProfileExample();
		example.createCriteria().andIdEqualTo(id).andExecutorIdIsNull();
		int count = taskProfileMapper
				.updateByExampleSelective(profile, example);
		ServiceAssert.isTrue(count > 0, Codes.TaskHasBeenAdopted);
	}

	@PUT
	@Path("/confirm")
	public void comfirmTask(@QueryParam("id") Long id,
			@QueryParam("finished") Boolean finished) {
		Byte status = TaskStatus.FINISHED;
		TaskProfile profile = taskProfileMapper.selectByPrimaryKey(id);

		if (!finished) {
			status = TaskStatus.UNFINISHED;
			ServiceAssert.isTrue(
					profile.getStatus().equals(TaskStatus.CONFIRMING),
					Codes.TaskStatusNotSupportAction);
		}
		TaskProfile updateProfile = new TaskProfile();
		updateProfile.setId(id);
		updateProfile.setStatus(status);

		taskProfileMapper.updateByPrimaryKeySelective(updateProfile);
	}

	@POST
	@Path("/republish")
	public TaskProfile republishTask(@QueryParam("id") Long id) {
		logger.debug("republish new task: " + id);
		TaskProfile oldProfile = taskProfileMapper.selectByPrimaryKey(id);
		TaskProfile newProfile = new TaskProfile();
		newProfile.setSponsorId(oldProfile.getSponsorId());
		newProfile.setStatus(TaskStatus.PENDING);
		newProfile.setReward(oldProfile.getReward());
		newProfile.setLocation(oldProfile.getLocation());
		newProfile.setEndTime(oldProfile.getEndTime());
		newProfile.setDescription(oldProfile.getDescription());
		taskProfileMapper.insertSelective(newProfile);
		Long profileId = newProfile.getId();
		String taskMediumDirectory = this.mediumBasicPath
				+ System.getProperty("file.separator") + profileId.toString();
		(new File(taskMediumDirectory)).mkdir();

		String oldMediumDirectory = this.mediumBasicPath
				+ System.getProperty("file.separator")
				+ oldProfile.getId().toString();

		
		try {
			TaskProfile updatedProfile = new TaskProfile();
			updatedProfile.setId(profileId);
			String oldMediaPath,mediaPath;
			boolean shouldToUpdate = false;
			if ((oldMediaPath = oldProfile.getPhotoPath()) != null && !oldMediaPath.isEmpty()) {
				mediaPath = copyMediaFile(oldMediumDirectory,oldMediaPath,taskMediumDirectory,profileId);
				updatedProfile.setPhotoPath(mediaPath);
				shouldToUpdate =true;
			}
			if ((oldMediaPath = oldProfile.getRecordPath()) != null && !oldMediaPath.isEmpty()) {
				mediaPath = copyMediaFile(oldMediumDirectory,oldMediaPath,taskMediumDirectory,profileId);
				updatedProfile.setRecordPath(mediaPath);
				shouldToUpdate =true;
			}
			if (shouldToUpdate) {
				taskProfileMapper.updateByPrimaryKeySelective(updatedProfile);
			}
			
		} catch (Exception e) {
			logger.error("Save task medium file error.", e);
			taskProfileMapper.deleteByPrimaryKey(profileId);
			throw new ServiceBizException(Codes.TaskMediumSaveFailure);
		}
		return taskProfileMapper.selectByPrimaryKey(profileId);
	}

	protected String copyMediaFile(String srcMediumDirectory,
			String srcMediaPath, String taskMediumDirectory, Long taskId)
			throws IOException {
		String oldMediaFullPath = srcMediumDirectory
				+ System.getProperty("file.separator") + srcMediaPath;
		String fileType = srcMediaPath.substring(srcMediaPath.indexOf('.'));
		String fileName = taskId.toString() + fileType;
		String savedFilePath = taskMediumDirectory
				+ System.getProperty("file.separator") + fileName;
		FileUtils.copyFile(new File(oldMediaFullPath), new File(savedFilePath));
		return fileName;

	}

}
