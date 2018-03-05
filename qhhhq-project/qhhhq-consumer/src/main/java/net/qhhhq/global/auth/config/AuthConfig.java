package net.qhhhq.global.auth.config;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class AuthConfig {

	private List<AuthServiceConfig> service;
	private String serviceCode;
	private String messageType;
	private String messageCode;

	@XmlElement(name="service")
	public List<AuthServiceConfig> getService() {
		return service;
	}
	public void setService(List<AuthServiceConfig> service) {
		this.service = service;
	}
	@XmlAttribute(name="serviceCode")
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	@XmlAttribute(name="messageType")
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	@XmlAttribute(name="messageCode")
	public String getMessageCode() {
		return messageCode;
	}
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

}
