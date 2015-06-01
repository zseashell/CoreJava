package auth;

import java.security.PrivilegedAction;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public class AuthTest {

	public static void main(String[] args) {
		System.setSecurityManager(new SecurityManager());
		try {
			LoginContext context = new LoginContext("LoginTest");
			context.login();
			System.out.println("Authentication Succeed!");
			Subject subject = context.getSubject();
			System.out.println("Subject:" + subject);
			PrivilegedAction<String> action = new SysProAction("user.home");
			String result = Subject.doAsPrivileged(subject, action, null);
			System.out.println(result);
			context.logout();
		} catch (LoginException e) {

		}

	}

}
