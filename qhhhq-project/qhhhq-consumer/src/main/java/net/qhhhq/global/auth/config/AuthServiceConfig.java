package net.qhhhq.global.auth.config;

import javax.xml.bind.annotation.XmlAttribute;

import net.qhhhq.global.auth.AuthService;

public class AuthServiceConfig {

	/* 权限规则 */
	private AuthService authService;

	private String classPath;
	/* 权限角色 */
	private String role;

	public AuthService getAuthService() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class classType = Class.forName(this.classPath);
		Object obj = classType.newInstance();
		return (AuthService) obj;
	}
	@XmlAttribute(name="service")
	public String getClassPath() {
		return classPath;
	}
	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}
	@XmlAttribute(name="role")
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

}
