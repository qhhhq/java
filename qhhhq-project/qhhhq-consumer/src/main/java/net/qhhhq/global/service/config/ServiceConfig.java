package net.qhhhq.global.service.config;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="conf")
public class ServiceConfig {

	private List<ServiceItemConfig> service;

	@XmlElement(name = "service")
	public List<ServiceItemConfig> getService() {
		return service;
	}

	public void setService(List<ServiceItemConfig> service) {
		this.service = service;
	}


}
