package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import logic.Admin;
import logic.DatabaseHandler;
import logic.Manager;
import logic.Store;
import logic.User;

public class AddStore extends JFrame 
			implements ActionListener{
	
	private JTextField txtAddress, txtOpen, txtClose, txtManager;
	private JPanel location, manager, workingHours;
	private JComboBox<String> openAMPM, closeAMPM;
	private JButton submit;
	private DatabaseHandler dbh;
	private AdminScreen admin;
	private Admin user;
	
	public AddStore(AdminScreen main, Admin user) throws ClassNotFoundException, IOException {
		dbh = DatabaseHandler.getInstance();
		dbh.loadFromFile();
		admin = main;
		this.user = (Admin) dbh.getUser(user);
		
		setLayout(new FlowLayout());
		setTitle("Add Store");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		location = new JPanel();
		location.add(new JLabel("Store location: "));
		txtAddress = new JTextField(20);
		location.add(txtAddress);
		
		manager = new JPanel();
		manager.add(new JLabel("Manager username: "));
		txtManager = new JTextField(20);
		manager.add(txtManager);
		
		workingHours = new JPanel();
		workingHours.add(new JLabel("Store working hours: from "));
		String[] ampm = {"AM", "PM"};
		txtOpen = new JTextField(5);
		workingHours.add(txtOpen);
		openAMPM = new JComboBox<String>(ampm);
		workingHours.add(openAMPM);
		workingHours.add(new JLabel(" to "));
		txtClose = new JTextField(5);
		workingHours.add(txtClose);
		closeAMPM = new JComboBox<String>(ampm);
		workingHours.add(closeAMPM);
		
		submit = new JButton("Submit");
		submit.addActionListener(this);
		
		add(location);
		add(manager);
		add(workingHours);
		add(submit);
		setSize(800, 500);
		setVisible(true);
		System.out.println(this.user==null);
		
		
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println(user==null);
		if(txtAddress.getText().length() < 1) {
			JOptionPane.showMessageDialog(new JFrame(), "Enter location please");
		}else if(dbh.getManager(txtManager.getText()) == null) {
			JOptionPane.showMessageDialog(new JFrame(), "Manager username couldn't be found");
		}else{
			Store store = new Store(null, null, txtAddress.getText());
			if(store.setWorkingHours(txtOpen.getText().concat((String) openAMPM.getSelectedItem()), txtClose.getText().concat((String) closeAMPM.getSelectedItem()))) {
				
				user.setSavedStore(store);
				
				try {
					user.addRemoveStore(true);
					dbh.saveToFile();
				} catch (IOException | ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(new JFrame(), "Store added!");
				try {
					admin.update();
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();
			}else {
				JOptionPane.showMessageDialog(new JFrame(), "Working hours have to be of the form XX:XX or X:XX");
			}
			
		}
	}
	
	

}
