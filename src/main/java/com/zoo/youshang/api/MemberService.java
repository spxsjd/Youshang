/**
 * 
 */
package com.zoo.youshang.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.jboss.resteasy.annotations.Form;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zoo.youshang.api.data.Codes;
import com.zoo.youshang.api.data.MemberRegisterRequest;
import com.zoo.youshang.api.error.ServiceAssert;
import com.zoo.youshang.api.error.ServiceAssert.Executor;
import com.zoo.youshang.api.error.ServiceBizException;
import com.zoo.youshang.api.protocol.BigFileOutputStream;
import com.zoo.youshang.api.protocol.FormFileUploadHelper;
import com.zoo.youshang.cache.MemberProfileCacher;
import com.zoo.youshang.config.Configuration;
import com.zoo.youshang.config.ConfigurationItem;
import com.zoo.youshang.entity.MemberProfile;
import com.zoo.youshang.entity.MemberProfileExample;
import com.zoo.youshang.persistence.MemberProfileMapper;
import com.zoo.youshang.util.MD5Util;

/**
 * @author sunpeng
 * 
 */
@Path("/member")
@Produces(MediaType.APPLICATION_JSON)
public class MemberService {

	private static final Logger logger = LoggerFactory
			.getLogger(MemberService.class);
	@Autowired
	private MemberProfileMapper memberProfileMapper;

	@Autowired
	private MemberProfileCacher memberProfileCacher;

	private String avatorBasicPath;
	private List<String> avatorTypes;
	private String avatorDefaultFile;

	public MemberService() {
		String defaultAvatorPath = Configuration.getInstance()
				.getConfigFullPath("avators");
		this.avatorBasicPath = ConfigurationItem.Upload.getConfigurationValue(
				"upload.avator.path", defaultAvatorPath);
		File file = new File(this.avatorBasicPath);
		if (!file.exists()) {
			file.mkdirs();
		}

		String[] types = ConfigurationItem.Upload
				.getConfigurationValue("upload.avator.types", ".jpg,.gif,.png")
				.toLowerCase().split(",");
		this.avatorTypes = Arrays.asList(types);

		this.avatorDefaultFile = ConfigurationItem.Upload
				.getConfigurationValue("avator.default.file", "default.png");
	}

	@POST
	public MemberProfile register(@Form MemberRegisterRequest request) {
		logger.debug("Register member: " + request.toString());
		final MemberProfile profile = new MemberProfile();
		profile.setAvatarPath(this.avatorDefaultFile);
		profile.setMobile(request.getMobile());
		profile.setName(request.getName());
		profile.setPassword(MD5Util.encode(request.getPassword()));
		profile.setUsername(request.getUsername());
		profile.setWeibo(request.getWeibo());
		ServiceAssert.checkExecutor(new Executor<Void>() {
			@Override
			public Void execute() {
				memberProfileMapper.insertSelective(profile);
				return null;
			}

		}, SQLIntegrityConstraintViolationException.class,
				Codes.MemberIdentityHasExist);
		return memberProfileMapper.selectByPrimaryKey(profile.getId());
	}

	@GET
	public MemberProfile query(@QueryParam("id") Long id) {
		logger.debug("Query member profile......");
		MemberProfile profile = memberProfileMapper.selectByPrimaryKey(id);
		ServiceAssert.notNull(profile, Codes.MemberNotFound);
		return profile;
	}

	@PUT
	@Path("/login")
	public MemberProfile login(@QueryParam("mobile") String mobile,
			@QueryParam("password") String password) {
		MemberProfileExample example = new MemberProfileExample();
		example.createCriteria().andMobileEqualTo(mobile);
		List<MemberProfile> list = memberProfileMapper.selectByExample(example);
		ServiceAssert.notEmpty(list, Codes.MemberNotFound);
		MemberProfile profile = list.get(0);
		String md5password = MD5Util.encode(password);
		ServiceAssert.isTrue(md5password.equals(profile.getPassword()),
				Codes.MemberPasswordError);
		MemberProfile updatedProfile = new MemberProfile();
		updatedProfile.setId(profile.getId());
		updatedProfile.setLastLoginTime(new Date());
		memberProfileMapper.updateByPrimaryKeySelective(updatedProfile);
		return profile;
	}

	@PUT
	@Path("/logout")
	public void logout(@QueryParam("id") String id) {

	}

	@GET
	@Path("/avator")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response queryAvator(@QueryParam("id") Long id) {

		MemberProfile profile = this.query(id);
		String avatorFileName = (StringUtils.isBlank(profile.getAvatarPath()) ? this.avatorDefaultFile
				: profile.getAvatarPath());
		String avatorPath = this.avatorBasicPath
				+ System.getProperty("file.separator") + avatorFileName;
		try {
			FileInputStream fileStream = new FileInputStream(avatorPath);
			// 直接返回输出流
			return Response
					.ok(new BigFileOutputStream(fileStream))
					.header("Content-Disposition",
							"attachment; filename=\"" + avatorFileName + "\"")
					.build();
		} catch (FileNotFoundException e) {
			logger.error("Not found the uploaded avator.", e);
			throw new ServiceBizException(Codes.AvatorNotExist);
		}

	}

	@POST
	@Path("/avator")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public void modifyAvator(@QueryParam("id") Long id,
			MultipartFormDataInput formDataInput) {
		Map<String, List<InputPart>> uploadForm = formDataInput
				.getFormDataMap();// 提交的form表单
		List<InputPart> inputParts = uploadForm.get("file");// 从form表单中获取name="file"的元素内容
		ServiceAssert.isTrue(inputParts.size() > 0, Codes.AvatorNotUpload);
		InputPart inputPart = inputParts.get(0);
		String fileType = FormFileUploadHelper.getFileType(inputPart);
		ServiceAssert.isTrue(this.avatorTypes.contains(fileType),
				Codes.AvatorTypeInvalid);
		try {

			String savedFileName = id.toString() + fileType;
			String savedFilePath = this.avatorBasicPath
					+ System.getProperty("file.separator") + savedFileName;

			MemberProfile profile = new MemberProfile();
			profile.setId(id);
			profile.setAvatarPath(savedFileName);
			int count = memberProfileMapper
					.updateByPrimaryKeySelective(profile);
			ServiceAssert.isTrue(count > 0, Codes.MemberNotFound);

			FormFileUploadHelper.saveFile(inputPart, savedFilePath);
		} catch (IOException e) {
			logger.error("Save the uploaded avator error.", e);
			throw new ServiceBizException(Codes.AvatorSaveFailure);
		}
	}

	@GET
	@Path("/mobile")
	public void prepareMobile(@QueryParam("mobile") String mobile) {
		String authcode = Integer.toString(RandomUtils.nextInt(1000000));
		memberProfileCacher.saveAuthcode(mobile, authcode);
		// 发送通知
	}

	@PUT
	@Path("/mobile")
	public void verifyMobile(@QueryParam("mobile") String mobile,
			@QueryParam("code") String authCode) {
		String cachedCode = memberProfileCacher.getAucthCode(mobile);
		ServiceAssert.notNull(cachedCode, Codes.MobileAuthCodeNotFound);
		ServiceAssert.isTrue(cachedCode.equals(authCode),
				Codes.MobileAuthCodeError);
		memberProfileCacher.deleteAucthCode(mobile);
	}

	@POST
	@Path("/mobile")
	public void bindMobile(@QueryParam("id") Long id,
			@QueryParam("mobile") String mobile,
			@QueryParam("code") String authCode) {
		this.verifyMobile(mobile, authCode);
		MemberProfile profile = new MemberProfile();
		profile.setId(id);
		profile.setMobile(mobile);
		int count = memberProfileMapper.updateByPrimaryKeySelective(profile);
		ServiceAssert.isTrue(count > 0, Codes.MemberNotFound);
	}

}
