package net.qhhhq.global.service.config;

import javax.xml.bind.annotation.XmlAttribute;

import net.qhhhq.global.service.Service;

public class ServiceItemConfig {

	private Service service;
	private String implClass;
	private String serviceCode;
	private String messageType;

	public Service getService() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class classType = Class.forName(this.implClass);
		Object obj = classType.newInstance();
		return (Service) obj;
	}
	@XmlAttribute(name="impl-class")
	public String getImplClass() {
		return implClass;
	}
	public void setImplClass(String implClass) {
		this.implClass = implClass;
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

}
