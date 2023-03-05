package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gui.RegisterWindow;
import logic.Customer;
import logic.DatabaseHandler;
import logic.User;

public class RegListener implements ActionListener{
	private String user;
	private String pass;
	private DatabaseHandler db = new DatabaseHandler();
	private RegisterWindow win;
	
	public RegListener(RegisterWindow window) {
		this.win = window;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		user = win.getUser();
		pass = win.getPassword();
		if(user.length() < 8 || (pass.length()< 8)) {
			JOptionPane.showMessageDialog(new JFrame(), "Username or password too short (has to be at least 8 letters long)");
			return;
		} else if(!win.arePassSame()) {
			JOptionPane.showMessageDialog(new JFrame(), "Password needs to be the same as re-entered password");
			return;
		}
		try {
			db.loadFromFile();
		} catch (ClassNotFoundException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		User newUser = new Customer(user, pass);
		if(db.addUser(newUser)) {
			JOptionPane.showMessageDialog(new JFrame(), "Your account was created");
			win.dispose();
		}else {
			JOptionPane.showMessageDialog(new JFrame(), "Username already taken!");
		}
		try {
			db.saveToFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
}
