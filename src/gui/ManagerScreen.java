package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logic.DatabaseHandler;
import logic.Manager;
import logic.Store;

public class ManagerScreen extends JFrame implements ActionListener{
	private Manager manager;
	private JLabel lblWelcome, lblUser, lblPassword, lblResult;
	private JButton btnLogout, btnProfile, btnViewStore;
	private ManagerScreen screen = this;
	private DatabaseHandler dbh;
	
	public ManagerScreen(Manager newManager) throws ClassNotFoundException, IOException {
		manager = newManager;
		dbh = DatabaseHandler.getInstance();
		setLayout(new FlowLayout());
		setTitle("SmartShoppers manager");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JMenuBar bar = new JMenuBar();
		setJMenuBar(bar);
		
		JMenu menu = new JMenu("menu");
		
		JMenuItem profile = new JMenuItem("Profile");
		profile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(manager == null) {
					JOptionPane.showMessageDialog(new JFrame(), "Please login to access profile");
				}else {
					try {
						new Profile(manager, screen);
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
		JButton viewStore = new JButton("View Store");
		viewStore.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(manager.getSavedStore() == null) {
					JOptionPane.showMessageDialog(new JFrame(), "You weren't assigned any store, ask administrator to assign you one");
					return;
				}
				
				try {
					new ViewStore(manager.getSavedStore(), manager, screen);
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		
		combo.add(lblWelcome);
		combo.add(btnLogout);
		combo.add(viewStore);
		
		add(combo);
		setSize(800, 500);
		setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		try {
			new StartupScreen();
		} catch (ClassNotFoundException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		dispose();
	}
}
