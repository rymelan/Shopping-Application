package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gui.AdminScreen;
import gui.ManagerScreen;
import gui.StartupScreen;
import logic.Admin;
import logic.Customer;
import logic.DatabaseHandler;
import logic.Manager;
import logic.User;

public class LoginListener implements ActionListener{

	private DatabaseHandler dbh = new DatabaseHandler();
	private StartupScreen start;
	public LoginListener(StartupScreen origin) {
		start = origin;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(start.getUserTxt().length() < 8 || (start.getPass().length < 8)) {
			JOptionPane.showMessageDialog(new JFrame(), "Username or password incorrect(has to be at least 8 letters long)");
			return;
		}
		try {
			dbh.loadFromFile();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		User u = dbh.getUser(start.getUserTxt(), String.valueOf(start.getPass()));
		
		if(u == null) {
			JOptionPane.showMessageDialog(new JFrame(), "Username or password incorrect");
		}else if(u.getClass() == Customer.class){
			try {
				start.setLogout(u);
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(u.getClass() == Admin.class) {
			try {
				new AdminScreen((Admin) u);
				start.dispose();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(u.getClass() == Manager.class) {
			try {
				new ManagerScreen((Manager) u);
				start.dispose();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
