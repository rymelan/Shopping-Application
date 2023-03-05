package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import controllers.LoginListener;
import logic.Customer;
import logic.DatabaseHandler;
import logic.Store;
import logic.User;


public class StartupScreen extends JFrame 
				implements ActionListener{
	private JLabel lblWelcome, lblUser, lblPassword, lblNoStores;
	private JButton btnLogin, btnSearchStores, btnSubmit, btnReg, btnSearch, btnSaveStore, btnViewStore;
	private JTextField txtUser, txtDistance;
	private JComboBox<Store> storesNearYou;
	private JPasswordField fldPassword;
	private JPanel options, search;
	private User user;
	private DatabaseHandler dbh;
	private int times = 0;
	
	public StartupScreen() throws ClassNotFoundException, IOException {
		dbh = DatabaseHandler.getInstance();
		dbh.loadFromFile();
		setLayout(new FlowLayout());
		setTitle("SmartShoppers");
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
		
		JMenuBar bar = new JMenuBar();
		setJMenuBar(bar);
		
		JMenu menu = new JMenu("menu");
		
		JMenuItem profile = new JMenuItem("Profile");
		StartupScreen screen = this;
		profile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(user == null) {
					JOptionPane.showMessageDialog(new JFrame(), "Please login to access profile");
				}else {
					try {
						
						new Profile(user, screen);
					} catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		});
		
		JMenuItem viewList = new JMenuItem("View shopping list");
		viewList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(user == null) {
					JOptionPane.showMessageDialog(new JFrame(), "Please login to view your list");
				}else if(((Customer)user).getList().size() < 1) {
					JOptionPane.showMessageDialog(new JFrame(), "Your shopping list is empty!");
				}else{
					new ViewList((Customer) user, screen);
				}
			}
			
		});
		JMenuItem saveAndExit = new JMenuItem("Save and exit");
		saveAndExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					user = dbh.getUser(user);
					dbh.saveToFile();
					user = dbh.getUser(user);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dispose();
			}
			
		});
		
		menu.add(profile);
		menu.add(viewList);
		menu.add(saveAndExit);
		bar.add(menu);
		
		JPanel combo = new JPanel();
		lblWelcome = new JLabel("Welcome to SmartShoppers");
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(this);
		combo.add(btnLogin);
		btnSearchStores = new JButton("search stores");
		btnSearchStores.addActionListener(this);
		combo.add(btnSearchStores);
		
		
		btnReg = new JButton("register");
		btnReg.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new RegisterWindow();
			}
			
		});
		lblUser = new JLabel("User name:");
		lblPassword = new JLabel("Password:  ");
		txtUser = new JTextField(20);
		fldPassword = new JPasswordField(20);
	
		
		btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new LoginListener(this));
		
		JPanel log = new JPanel();
		log.add(lblUser);
		log.add(txtUser);
		log.add(lblPassword);
		log.add(fldPassword);
		log.add(btnSubmit);
		log.add(btnReg);
		
		btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if(!txtDistance.getText().matches("^[A-Za-z]*")) {
					storesNearYou.removeAllItems();
					ArrayList<Store> stores = new ArrayList<Store>(); 
					JOptionPane.showInputDialog("please enter address: ");
					if(dbh.getStores().size() >= 1) {
						btnSaveStore.setVisible(true);
						btnViewStore.setVisible(true);
						for(int i = 0; i < dbh.getStores().size(); i++) {
							int RandomStore= (int)(Math.random()*dbh.getStores().size());
							if(!stores.contains(dbh.getStores().get(RandomStore))) {
								storesNearYou.addItem(dbh.getStores().get(RandomStore));
								stores.add(dbh.getStores().get(RandomStore));
							}
							
						}
					}else {
						lblNoStores.setVisible(true);
					}
				}else {
					JOptionPane.showMessageDialog(new JFrame(), "please enter numbers only");
				}
			}
			
		});
		
		search = new JPanel();
		search.add(new JLabel("Search for store within: "));
		txtDistance =  new JTextField(3);
		search.add(txtDistance);
		search.add(new JLabel(" KM"));
		search.add(btnSearch);
		storesNearYou = new JComboBox<Store>();
		search.add(storesNearYou);
		lblNoStores = new JLabel("No stores near you");
		lblNoStores.setVisible(false);
		search.add(lblNoStores);
		btnSaveStore = new JButton("save store");
		btnSaveStore.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(user == null) {
					JOptionPane.showMessageDialog(new JFrame(), "Please login to save stores");
				}else {
					user.setSavedStore((Store) storesNearYou.getSelectedItem());
					JOptionPane.showMessageDialog(new JFrame(), "Store Saved!");
					try {
						dbh.saveToFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		});
		
		search.add(btnSaveStore);
		btnSaveStore.setVisible(false);
		btnViewStore = new JButton("View store");
		btnViewStore.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					new ViewStore((Store) storesNearYou.getSelectedItem(), user, null);
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		btnViewStore.setVisible(false);
		search.add(btnViewStore);
		
		options = new JPanel(new CardLayout());
		options.add(log, "log");
		options.add(search, "search");
		
		add(lblWelcome);
		add(btnLogin);
		add(btnSearchStores);
		add(options);
		setSize(800, 500);
		setVisible(true);
		
		options.setVisible(false);
	}
		
	
	public static void main(String[] args) throws ClassNotFoundException, IOException{
		new StartupScreen();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(times == 0) {
			options.setVisible(true);
		}
		times = 1;
		if(e.getSource() == btnLogin) {
			if(btnLogin.getText() == "Login") {
				CardLayout cl = (CardLayout)(options.getLayout());
				cl.show(options, "log");
			}else {
				user = null;
				try {
					setLogin();
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			
		}else if(e.getSource() == btnSearchStores) {
			CardLayout cl = (CardLayout)(options.getLayout());
			cl.show(options, "search");
		}
	}
	
	public void setLogout(User u) throws ClassNotFoundException, IOException {
		this.btnLogin.setText("Logout");
		CardLayout cl = (CardLayout)(options.getLayout());
		cl.show(options, "search");
		this.user = dbh.getUser(u);
		
	}
	
	public void setLogin() throws IOException, ClassNotFoundException {
		this.btnLogin.setText("Login");
		CardLayout cl = (CardLayout)(options.getLayout());
		dbh.saveToFile();
		cl.show(options, "log");
	}
	
	public String getDistanceTxt() {
		return this.txtDistance.getText();
	}
	
	public String getUserTxt() {
		return this.txtUser.getText();
	}
	
	public char[] getPass() {
		return this.fldPassword.getPassword();
	}
	
	public void update() throws ClassNotFoundException, IOException {
		dbh.loadFromFile();
		user = dbh.getUser(user);
		if(user == null && btnLogin.getText().equals("Logout")) {
			setLogin();
		}
	}
}

