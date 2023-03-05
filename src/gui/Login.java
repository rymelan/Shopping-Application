package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Login extends JFrame
				implements ActionListener{
	private JLabel lblUser, lblPassword, lblResult;
	private JTextField txtUser;
	private JPasswordField fldPassword;
	private JButton btnSubmit;

	
	public Login() {
		
		lblUser = new JLabel("User name:");
		lblPassword = new JLabel("Password:  ");
		lblResult = new JLabel();
		txtUser = new JTextField(20);
		fldPassword = new JPasswordField(20);
		
		
		btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(this);

        FlowLayout layout = new FlowLayout();
        setLayout(layout);
        add(lblUser);
        add(txtUser);
        add(lblPassword);
        add(fldPassword);
        add(btnSubmit);
        add(lblResult);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		this.lblResult.setText("success");
		System.exit(0);
	}

}
