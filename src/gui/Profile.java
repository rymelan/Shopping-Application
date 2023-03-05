package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logic.Admin;
import logic.Customer;
import logic.DatabaseHandler;
import logic.Manager;
import logic.User;

public class Profile extends JFrame{
	private JLabel lblUser, lblPassword, lblStore, lblSecurity;
	private JButton changeUser, changePassword, changeStore, changeSecurity;
	private JPanel username, password, store, security;
	private DatabaseHandler dbh;
	
	public Profile(User u, JFrame screen) throws ClassNotFoundException, IOException {
		dbh = DatabaseHandler.getInstance();
		setLayout(new FlowLayout());
		setTitle("Profile");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		lblUser = new JLabel("Username: " + u.getUsername());
		changeUser = new JButton("Change Username");
		changeUser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean done = false;
				String temp = JOptionPane.showInputDialog("please enter password: ");
				while(!u.authenticate(u.getUsername(), temp)) {
					JOptionPane.showMessageDialog(new JFrame(), "incorrect password");
					temp = JOptionPane.showInputDialog("please enter password: ");
				}
				while(!done) {
					String user = JOptionPane.showInputDialog("please enter new username: ");
					if(user.length()<8) {
						JOptionPane.showMessageDialog(new JFrame(), "username has to be at least 8 characters long");
					}else if(dbh.isUsernameTaken(user)) {
						JOptionPane.showMessageDialog(new JFrame(), "username taken");
					}else {
						u.setUsername(temp, user);
						done = true;
					}
				}
				try {
					dbh.saveToFile();
					dbh.loadFromFile();
					new Profile(dbh.getUser(u.getUsername(), temp), screen);
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dispose();
			}
			
		});;
		username = new JPanel();
		username.add(lblUser);
		username.add(changeUser);
		
		lblPassword = new JLabel("Password: ********	");
		changePassword = new JButton("Change Password");
		changePassword.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean done = false;
				String temp = JOptionPane.showInputDialog("please enter password: ");
				while(!u.authenticate(u.getUsername(), temp)) {
					JOptionPane.showMessageDialog(new JFrame(), "incorrect password");
					temp = JOptionPane.showInputDialog("please enter password: ");
				}
				while(!done) {
					String pass = JOptionPane.showInputDialog("please enter new password: ");
					if(pass.length()<8) {
						JOptionPane.showMessageDialog(new JFrame(), "password has to be at least 8 characters long");
					}else {
						u.setPassword(temp, pass);
						done = true;
					}
				}
				try {
					dbh.saveToFile();
					new Profile(u, screen);
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dispose();
			}
			
		});
		password = new JPanel();
		password.add(lblPassword);
		password.add(changePassword);
		
		if(u.getSavedStore() == null) {
			lblStore = new JLabel("Saved Store: no store saved		");
		}else {
			lblStore = new JLabel("Saved Store: " + u.getSavedStore().getLocation());
		}
		changeStore = new JButton("Remove Store");
		changeStore.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				u.setSavedStore(null);
				try {
					dbh.saveToFile();
					new Profile(u, screen);
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dispose();
			}
		});
		store = new JPanel();
		store.add(lblStore);
		store.add(changeStore);
		
		lblSecurity = new JLabel("Security options: ");
		String[] choices = {"(0)hide all information", "(1)allow shopping information collection to improve our system"};
		final JComboBox<String> cb = new JComboBox<String>(choices);
		cb.setSelectedIndex(u.getSecurity());
		cb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				u.setSecurity(cb.getSelectedIndex());
				try {
					dbh.saveToFile();
					new Profile(u, screen);
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dispose();
			}
		});
		security = new JPanel();
		security.add(lblSecurity);
		security.add(cb);
		
		JPanel deleteAccount = new JPanel();
		JButton btnDelete = new JButton("Delete account");
		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dbh.removeUser(u.getUsername());
				
				if(screen.getClass() == StartupScreen.class) {
					try {
						dbh.saveToFile();
						((StartupScreen)screen).update();
					} catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				dispose();
			}
		});
		deleteAccount.add(btnDelete);
		if(u.getClass() == Manager.class || u.getClass() == Admin.class) {
			store.setVisible(false);
			security.setVisible(false);
			deleteAccount.setVisible(false);
		}
		add(username);
		add(password);
		add(store);
		add(security);
		add(deleteAccount);
		setSize(500, 300);
		setVisible(true);
	}
}
