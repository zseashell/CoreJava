package chap9.jaas;

import java.io.FileReader;
import java.security.Principal;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

public class SimpleLoginModule implements LoginModule {

	private Subject subject;
	private CallbackHandler callbackHandler;
	private Map<String, ?> options;

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler,
			Map<String, ?> sharedState, Map<String, ?> options) {
		this.subject = subject;
		this.callbackHandler = callbackHandler;
		this.options = options;

	}

	@Override
	public boolean login() throws LoginException {
		if (callbackHandler == null)
			throw new LoginException("No Handler!");
		NameCallback nameCallback = new NameCallback("username:");
		PasswordCallback passwordCallback = new PasswordCallback("password:",
				false);

		try {
			callbackHandler.handle(new Callback[] { nameCallback,
					passwordCallback });
		} catch (Exception e) {
			LoginException loginException = new LoginException("Exception when login");
			throw loginException;
		}
		
		return checkLogin(nameCallback.getName(), passwordCallback.getPassword());
	}

	private boolean checkLogin(String username, char[] password)
			throws LoginException {
		try {
			Scanner in = new Scanner(new FileReader("" + options.get("pwfile")));
			while (in.hasNextLine()) {
				String[] inputs = in.nextLine().split("\\|");
				if (inputs[0].equals(username) && Arrays.equals(inputs[1].toCharArray(), password)) {
					String role = inputs[2];
					Set<Principal> principals = subject.getPrincipals();
					principals.add(new SimplePrincipal("username", username));
					principals.add(new SimplePrincipal("role", role));
					return true;
				}
			}
			in.close();
			return false;
		} catch (Exception e) {
			LoginException loginException = new LoginException(
					"Exception when login");
			throw loginException;
		}

	}

	@Override
	public boolean commit() throws LoginException {
		return true;
	}

	@Override
	public boolean abort() throws LoginException {
		return true;
	}

	@Override
	public boolean logout() throws LoginException {
		return true;
	}

}
