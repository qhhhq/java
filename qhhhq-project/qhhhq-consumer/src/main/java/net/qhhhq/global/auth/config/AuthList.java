package net.qhhhq.global.auth.config;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="conf")
public class AuthList {

	List<AuthConfig> trans;

	@XmlElement(name = "tran")
	public List<AuthConfig> getTrans() {
		return trans;
	}

	public void setTrans(List<AuthConfig> trans) {
		this.trans = trans;
	}


}
