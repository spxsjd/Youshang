/**
 * 
 */
package com.zoo.youshang.api.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author sunpeng.peng
 * 
 */
@XmlRootElement(
        name = "Response")
public class ServiceEntity {

	private Object data;
	private String requestId;
	private ServiceCode serviceCode;
	
	
	public static ServiceEntity done(){
		return new ServiceEntity();
	}
	public ServiceEntity() {
		this.requestId = ServiceContext.get().getRequestId();
		this.serviceCode = Codes.Successful;
	}
	
	public ServiceEntity(ServiceCode code) {
		this.requestId = ServiceContext.get().getRequestId();
		this.serviceCode = code;
	}
	
	public ServiceEntity(Object data) {
		this.requestId = ServiceContext.get().getRequestId();
		this.serviceCode = Codes.Successful;
		this.data = data;
	}
	
	public ServiceEntity(ServiceCode code,Object data) {
		this.requestId = ServiceContext.get().getRequestId();
		this.serviceCode = code;
		this.data = data;
	}

	@XmlElement(name = "requestId")
	public String getRequestId() {
		return requestId;
	}

	@XmlElement(name = "code")
	public Integer getCode() {
		return this.serviceCode.getCode();
	}

	@XmlElement(name = "message")
	public String getMessage() {
		return this.serviceCode.getMessage();
	}

	@XmlElement(name = "data")
	public Object getData() {
		return data;
	}

}
