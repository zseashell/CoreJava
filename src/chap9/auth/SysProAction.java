package chap9.auth;

import java.security.PrivilegedAction;

public class SysProAction implements PrivilegedAction<String> {

	private String propertyName;

	public SysProAction(String propertyName) {
		this.propertyName = propertyName;
	}

	@Override
	public String run() {
		return System.getProperty(propertyName);
	}
}
