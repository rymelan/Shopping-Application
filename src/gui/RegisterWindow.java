package gui;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

import javax.swing.*;

import controllers.RegListener;
import logic.Admin;

public class RegisterWindow extends JFrame {
	private JLabel lblUser, lblPassword, lblRepeat;
	private JTextField txtUser;
	private JPasswordField txtPassword, txtReenter;
	private JButton submit;
	
	public RegisterWindow(){
		
		lblUser = new JLabel("Username:");
		lblPassword = new JLabel("Password");
		txtUser = new JTextField(20);
		lblRepeat = new JLabel("re-enter password: ");
		txtReenter = new JPasswordField(20);
		txtPassword = new JPasswordField(20);
		
		submit = new JButton("Complete registration");
		submit.addActionListener(new RegListener(this));
		FlowLayout layout = new FlowLayout();
        setLayout(layout);
        add(lblUser);
        add(txtUser);
        add(lblPassword);
        add(txtPassword);
        add(lblRepeat);
        add(txtReenter);
        add(submit);
        setSize(300,300);
		setVisible(true);
	}
	
	
	public boolean arePassSame() {
		return Arrays.equals(this.txtPassword.getPassword(), this.txtReenter.getPassword());
	}
	
	public String getUser() {
		return this.txtUser.getText();
	}
	
	public String getPassword() {
		return String.valueOf(this.txtPassword.getPassword());
	}
}

