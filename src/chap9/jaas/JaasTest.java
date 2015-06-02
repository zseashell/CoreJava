package chap9.jaas;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class JaasTest {

	public static void main(String[] args) {
		System.setSecurityManager(new SecurityManager());
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				JFrame frame = new JaasFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});

	}

}

class JaasFrame extends JFrame {

	private JTextField username;
	private JPasswordField password;
	private JTextField propertyName;
	private JTextField propertyValue;

	public JaasFrame() {
		setTitle("JaasTest");

		username = new JTextField(20);
		password = new JPasswordField(20);
		propertyName = new JTextField(20);
		propertyValue = new JTextField(20);
		propertyValue.setEditable(false);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2));
		panel.add(new JLabel("username:"));
		panel.add(username);
		panel.add(new JLabel("password:"));
		panel.add(password);
		panel.add(propertyName);
		panel.add(propertyValue);
		add(panel, BorderLayout.CENTER);

		JButton getValueButton = new JButton("Get Value");
		getValueButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getValue();

			}
		});
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(getValueButton);
		add(buttonPanel, BorderLayout.SOUTH);
		pack();

	}

	public void getValue() {
		try {
			LoginContext context = new LoginContext("LoginTest",
					new SimpleCallBackHandler(username.getText(),
							password.getPassword()));
			context.login();
			Subject subject = context.getSubject();
			propertyValue.setText(""
					+ Subject.doAsPrivileged(subject, new SysProAction(
							propertyName.getText()), null));
			context.logout();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e);
		}
	}
}
