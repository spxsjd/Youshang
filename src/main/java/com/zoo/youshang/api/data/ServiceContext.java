/**
 * 
 */
package com.zoo.youshang.api.data;

import java.util.Date;
import java.util.UUID;
import com.zoo.youshang.entity.MemberProfile;

/**
 * @author sunpeng.peng
 * 
 */
public class ServiceContext {

	protected static final ThreadLocal<ServiceContext> threadLocal = new InheritableThreadLocal<ServiceContext>();

	private MemberProfile memberProfile;
	private String requestId;
	private Date requestTime;

	private ServiceContext() {
	}

	public static ServiceContext get() {

		ServiceContext serviceContext = threadLocal.get();
		if (serviceContext == null) {
			// 初始化ServiceContext后必须在相应的代码块里的finally里执行ServiceContext.remove();否则系统将
			serviceContext = new ServiceContext();
			serviceContext.setRequestId(UUID.randomUUID().toString());
			serviceContext.setRequestTime(new Date());
			threadLocal.set(serviceContext);
		}
		return serviceContext;
	}

	public static void remove() {
		threadLocal.remove();
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	

	public MemberProfile getMemberProfile() {
		return memberProfile;
	}

	public void setMemberProfile(MemberProfile memberProfile) {
		this.memberProfile = memberProfile;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

}
