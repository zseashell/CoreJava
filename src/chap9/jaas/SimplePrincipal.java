package chap9.jaas;

import java.security.Principal;

public class SimplePrincipal implements Principal {

	private String descr;

	private String value;

	public SimplePrincipal(String descr, String value) {
		this.descr = descr;
		this.value = value;
	}

	@Override
	public String getName() {
		return descr + "=" + value;
	}

	public boolean equals(Object otherObj) {
		if (this == otherObj)
			return true;
		if (otherObj == null)
			return false;
		if (getClass() != otherObj.getClass())
			return false;
		SimplePrincipal other = (SimplePrincipal) otherObj;
		return getName().equals(other.getName());
	}

	public int hashCode() {
		return getName().hashCode();
	}

}
