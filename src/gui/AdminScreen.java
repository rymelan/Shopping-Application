package gui;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import logic.Admin;
import logic.DatabaseHandler;
import logic.Manager;
import logic.Store;
import logic.User;

public class AdminScreen extends JFrame 
			implements ActionListener{
	private JLabel lblWelcome, lblManager, lblManagesStore;
	private JButton btnLogout, btnSearchStores, btnSearchManager, btnViewStore, btnAddStore, btnRemoveStore; 
	private JButton btnRemoveManager, btnManagers, btnSearch, btnAddManager;
	private JTextField txtAddress, txtManager;
	private JComboBox<Store> storesMatch;
	private JPanel options, search, manager, stores, managerInfo;
	private Admin user;
	private DatabaseHandler dbh;
	private AdminScreen screen;
	
	public AdminScreen(Admin admin) throws ClassNotFoundException, IOException {
		screen = this;
		dbh = DatabaseHandler.getInstance();
		user = admin;
		update();
		setLayout(new FlowLayout());
		setTitle("SmartShoppers Admin");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JMenuBar bar = new JMenuBar();
		setJMenuBar(bar);
		
		JMenu menu = new JMenu("menu");
		
		JMenuItem profile = new JMenuItem("Profile");
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
		JMenuItem saveAndExit = new JMenuItem("Save and exit");
		saveAndExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					dbh.saveToFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dispose();  
			}
			
		});
		
		
		
		menu.add(profile);
		menu.add(saveAndExit);
		bar.add(menu);
		
		JPanel combo = new JPanel();
		lblWelcome = new JLabel("Welcome to SmartShoppers");
		btnLogout = new JButton("Logout");
		btnLogout.addActionListener(this);
		btnSearchStores = new JButton("search stores");
		btnSearchStores.addActionListener(this);
		btnManagers = new JButton("Add/Remove Manager");
		btnManagers.addActionListener(this);
		combo.add(lblWelcome);
		combo.add(btnLogout);
		combo.add(btnSearchStores);
		combo.add(btnManagers);
		
		search = new JPanel();
		search.add(new JLabel("Enter store address: "));
		txtAddress = new JTextField(20);
		btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				storesMatch.removeAllItems();
				Pattern pattern = Pattern.compile(txtAddress.getText(), Pattern.CASE_INSENSITIVE);
				for(Store s : dbh.getStores()) {
					if(pattern.matcher(s.getLocation()).find()) {
						storesMatch.addItem(s);
					}
				}
				storesMatch.setVisible(true);
			}
			
		});
		
		stores = new JPanel();
		storesMatch = new JComboBox<Store>();
		btnViewStore = new JButton("View Store");
		btnViewStore.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(storesMatch.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(new JFrame(), "Pick a store first");
				}else {
					try {
						user.setSavedStore((Store) storesMatch.getSelectedItem());
						dbh.saveToFile();
						new ViewStore((Store) storesMatch.getSelectedItem(), user, screen);
					} catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			
		});
		btnAddStore = new JButton("Add store");
		btnAddStore.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					new AddStore(getThis(), user);
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		btnRemoveStore = new JButton("Remove selected store");
		btnRemoveStore.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(storesMatch.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(new JFrame(), "Pick a store first");
				}else {
					int choice = JOptionPane.showConfirmDialog(new JFrame(), "Are you sure you want to delete " + storesMatch.getSelectedItem() + "?");
					if(choice == 0) {
						try {
							//ArrayList<Store> stores = dbh.getStores();
							//stores.remove(storesMatch.getSelectedItem());
							//dbh.setStores(stores);
							user.setSavedStore((Store) storesMatch.getSelectedItem());
							user.addRemoveStore(false);
							dbh.saveToFile();
							update();
							JOptionPane.showMessageDialog(new JFrame(), "Store Deleted!");
						} catch (ClassNotFoundException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
			}
			
		});
		
		search.add(txtAddress);
		search.add(btnSearch);
		stores.add(storesMatch);
		stores.add(btnViewStore);
		stores.add(btnAddStore);
		stores.add(btnRemoveStore);
		storesMatch.setVisible(false);
		
		manager = new JPanel();
		
		manager.add(new JLabel("Enter Manager Username: "));
		txtManager = new JTextField(20);
		btnSearchManager = new JButton("Search");
		btnSearchManager.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Manager manager = dbh.getManager(txtManager.getText()); 
				if(manager == null) {
					JOptionPane.showMessageDialog(new JFrame(), "No manager with that username");
				}else {
					lblManager.setText("Manager username: " + manager.getUsername());
					if(manager.getSavedStore() != null) {
						lblManagesStore.setText("Manager of store at: " + manager.getSavedStore());
					}else {
						lblManagesStore.setText("Not assigned any store yet");
					}
					managerInfo.setVisible(true);
				}
			}
			
		});
		
		btnAddManager = new JButton("Add new Manger");
		btnAddManager.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String username = "manager" + (int)(Math.random()*10000);
				while(dbh.isUsernameTaken(username)) {
					username = "manager" + (int)(Math.random()*10000);
				}
				String password = "Temp" +(int)(Math.random()*10000);
				Manager newManager = new Manager(username, password);
				try {
					user.addManager(newManager);
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					dbh.saveToFile();
					update();
				} catch (IOException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(new JFrame(), "New Manager created!");
				JOptionPane.showMessageDialog(new JFrame(), "Manager username: " + username + "  Manager temporary password: " + password);
				
			}
			
		});
		btnRemoveManager = new JButton("Remove Manager");
		btnRemoveManager.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int choice = JOptionPane.showConfirmDialog(new JFrame(), "Are you sure you want to delete " + txtManager.getText() + "?");
				if(choice == 0) {
					try {
						if(user.removeManager(txtManager.getText())) {
							JOptionPane.showMessageDialog(new JFrame(), "Manager removed!");
						}else {
							JOptionPane.showMessageDialog(new JFrame(), "No manager with that user name");
						}
					} catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		});
		manager.add(txtManager);
		manager.add(btnSearchManager);
		manager.add(btnAddManager);
		manager.add(btnRemoveManager);
		
		managerInfo = new JPanel();
		lblManager = new JLabel();
		lblManagesStore = new JLabel();
		managerInfo.add(lblManager);
		managerInfo.add(lblManagesStore);
		managerInfo.setVisible(false);
		
		options = new JPanel(new CardLayout());
		options.add(search, "search");
		options.add(manager, "Manager");
		
		add(lblWelcome);
		add(btnLogout);
		add(btnSearchStores);
		add(btnManagers);
		add(options);
		add(stores);
		add(managerInfo);
		setSize(800, 500);
		setVisible(true);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		CardLayout cl = (CardLayout)(options.getLayout());
		if(e.getSource() == btnLogout) {
			try {
				new StartupScreen();
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dispose();
		}else if(e.getSource() == btnSearchStores) {
			cl.show(options, "search");
			stores.setVisible(true);
			managerInfo.setVisible(false);
		}else{
			cl.show(options, "Manager");
			stores.setVisible(false);
		}
	}
	
	public void update() throws ClassNotFoundException, IOException {
		dbh.loadFromFile();
		user = (Admin) dbh.getUser(user);
	}
	
	public AdminScreen getThis() {
		return this;
	}

}
