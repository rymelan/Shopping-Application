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
import logic.Customer;
import logic.DatabaseHandler;
import logic.Item;
import logic.Manager;
import logic.Search;
import logic.Store;
import logic.User;

public class ViewStore extends JFrame 
			implements ActionListener{
	private JLabel lblItems, lblName, lblDesc, lblPrice, lblSize, lblLeft, lblManager;
	private JTextField txtName;
	private JButton btnViewItem, btnChangeManager, btnEditHours, btnEditLocation, btnSearch;
	private JButton btnEditName, btnEditDesc, btnEditPrice, btnEditSize, btnEditLeft, btnAddToList;
	private JPanel location, search, hoursOpen, result, itemName, itemDesc, itemPrice, itemSize, itemLeft;
	private Search searchStore;
	private JComboBox<String> searchCategory, searchType;
	private JComboBox<Item> itemBox;
	private Store myStore;
	private User myUser;
	private DatabaseHandler dbh;
	
	public ViewStore(Store store, User user, JFrame screen) throws ClassNotFoundException, IOException {
		dbh = DatabaseHandler.getInstance();
		myStore = store;
		myUser = user;
		searchStore = new Search(myStore);
		//System.out.println(myUser.getSavedStore() == myStore);
		//update();
		
		setLayout(new FlowLayout());
		setTitle("Store");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		location = new JPanel();
		JLabel lblLocation = new JLabel("Smartshoppers store on " + myStore);
		location.add(lblLocation);
		btnEditLocation = new JButton("Edit Location");
		btnEditLocation.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String location = JOptionPane.showInputDialog("Provide new location:" );
				if(location == null || location.equals(String.valueOf(JOptionPane.CANCEL_OPTION)) || location.equals(String.valueOf(JOptionPane.CLOSED_OPTION))) {
					return;
				}else {
					((Manager)myUser).setLocation(location);
					try {
						dbh.saveToFile();
						//update();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					lblLocation.setText("Smartshoppers store on " + myStore);
				}
			}
			
		});
		location.add(btnEditLocation);
		btnEditLocation.setVisible(false);
		
		if(myStore.getManager() == null) {
			lblManager = new JLabel("this store has no manager");
		}else {
			lblManager = new JLabel("Managed by: " + myStore.getManager().getUsername());
		}
		lblManager.setVisible(false);
		location.add(lblManager);
		btnChangeManager = new JButton("Change Manager");
		btnChangeManager.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String manager = JOptionPane.showInputDialog("Provide manager username:" );
				if(manager == null || manager.equals(String.valueOf(JOptionPane.CANCEL_OPTION)) || manager.equals(String.valueOf(JOptionPane.CLOSED_OPTION))) {
					return;
				}
				if(dbh.getManager(manager) == null && manager != null) {
					JOptionPane.showMessageDialog(new JFrame(), "Manager with that username does not exist");
				}else {
					((Admin)myUser).setStoreManager(dbh.getManager(manager));
					try {
						System.out.println(myStore.getManager());
						dbh.saveToFile();
						//update();
						((AdminScreen)screen).update();
						lblManager.setText(myStore.getManager().getUsername());
					} catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			
		});
		
		location.add(btnChangeManager);
		btnChangeManager.setVisible(false);
		
		hoursOpen = new JPanel();
		JLabel lblHours = new JLabel("Open from: " + myStore.getOpenHour() + " to " + myStore.getCloseHour());
		hoursOpen.add(lblHours);
		btnEditHours = new JButton("Edit hours");
		btnEditHours.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String open = JOptionPane.showInputDialog("Provide new opening time: " );
				String close = JOptionPane.showInputDialog("Provide new closing time: " );
				if(open == null || close == null) {
					return;
				}else {
					if(((Manager)myUser).setWorkingHours(open, close)) {
						try {
							dbh.saveToFile();
							//update();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						lblHours.setText("Open from: " + myStore.getOpenHour() + " to " + myStore.getCloseHour());
					}else {
						JOptionPane.showMessageDialog(new JFrame(), "Input has to be of the form XX:XX or X:XX with PM or AM after the time");
					}
				}
			}
			
		});
		hoursOpen.add(btnEditHours);
		btnEditHours.setVisible(false);
		
		
		
		search = new JPanel();
		search.add(new JLabel("Search type: "));
		String[] types = {"Name", "Category"};
		searchType = new JComboBox<String>(types);
		searchType.setSelectedIndex(0);
		searchType.addActionListener(this);
		search.add(searchType);
		
		txtName= new JTextField(20);
		search.add(txtName);
		
		String[] category = myStore.getCat().toArray(new String[0]);
		searchCategory = new JComboBox<String>(category);
		searchCategory.addItem("Items on sale");
		
		search.add(searchCategory);
		searchCategory.setVisible(false);
		
		btnSearch = new JButton("search");
		btnSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(searchType.getSelectedIndex() == 0) {
					itemsByName();
				}else{
					if(searchCategory.getSelectedItem().equals("Items on sale")) {
						itemsOnSale();
					}else {
						itemsByCategory();
					}
				}
			}
			
		});
		search.add(btnSearch);
		
		
		result = new JPanel();
		lblItems = new JLabel("Items matching your search: ");
		result.add(lblItems);
		itemBox = new JComboBox<Item>();
		result.add(itemBox);
		btnViewItem = new JButton("View Item");
		btnViewItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(itemBox.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(new JFrame(), "Pick an item before trying to view it");
				}else {
					lblName.setText("Item name: " + itemBox.getSelectedItem().toString());
					lblDesc.setText("Description: " + ((Item)itemBox.getSelectedItem()).getDescription());
					String price =  String.format("%.2f", ((Item)itemBox.getSelectedItem()).getPrice());
					lblPrice.setText("Item price: " + price);
					String size = String.valueOf(((Item)itemBox.getSelectedItem()).getHeight()) + " in X " +
							String.valueOf(((Item)itemBox.getSelectedItem()).getWidth()) + " in X " + 
							String.valueOf(((Item)itemBox.getSelectedItem()).getLength()) + " in";
					lblSize.setText("Item size (Length x width x height): " + size);
					lblLeft.setText("Number left in stock: " + ((Item)itemBox.getSelectedItem()).getNumLeft());
				}
				
			}
			
		});
		result.add(btnViewItem);
		btnAddToList = new JButton("add to list");
		btnAddToList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(itemBox.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(new JFrame(), "Pick an item before pressing this button");
				}else if(user == null) {
					JOptionPane.showMessageDialog(new JFrame(), "login to add to list");
				}else {
					((Customer) user).addItemToList((Item) itemBox.getSelectedItem());
					JOptionPane.showMessageDialog(new JFrame(), "Item Added!");
					try {
						DatabaseHandler.getInstance().saveToFile();
					} catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			
		});
		result.add(btnAddToList);
		btnAddToList.setVisible(false);
		
		
		
		itemName = new JPanel();
		itemDesc = new JPanel();
		itemPrice = new JPanel();
		itemSize = new JPanel();
		itemLeft = new JPanel();
		JPanel addToSale = new JPanel();
		JButton btnAddSale = new JButton("Add to sale items");
		btnAddSale.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(itemBox.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(new JFrame(), "Pick an item first!");
					return;
				}
				boolean worked = ((Manager)myUser).addSaleItem(((Item)itemBox.getSelectedItem()).getName());
				if(worked) {
					JOptionPane.showMessageDialog(new JFrame(), "Added!");
				}else {
					JOptionPane.showMessageDialog(new JFrame(), "already on the list!");
				}
				try {
					System.out.println(myStore.getSaleItems().size());
					dbh.saveToFile();
					//update();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		});
		addToSale.add(btnAddSale);
		
		JButton btnRemoveSale = new JButton("remove from sale items");
		btnRemoveSale.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(itemBox.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(new JFrame(), "Pick an item first!");
					return;
				}
				boolean worked = ((Manager)myUser).removeSaleItem(((Item)itemBox.getSelectedItem()).getName());
				System.out.println(((Item)itemBox.getSelectedItem()).getName());
				if(worked) {
					JOptionPane.showMessageDialog(new JFrame(), "removed!");
				}else {
					JOptionPane.showMessageDialog(new JFrame(), "not on sale!");
				}
				try {
					System.out.println(myStore.getSaleItems().size());
					dbh.saveToFile();
					//update();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		});
		addToSale.add(btnRemoveSale);
		addToSale.setVisible(false);
		
		lblName = new JLabel();
		itemName.add(lblName);
		
		
		lblDesc = new JLabel();
		itemDesc.add(lblDesc);
		btnEditDesc = new JButton("Edit description");
		btnEditDesc.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(itemBox.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(new JFrame(), "Pick an item first!");
					return;
				}
				String newDesc = JOptionPane.showInputDialog("Enter new description: ");
				if(newDesc == null) {
					return;
				}
				((Item)itemBox.getSelectedItem()).setDescription(newDesc);
				try {
					dbh.saveToFile();
					//update();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		});
		itemDesc.add(btnEditDesc);
		btnEditDesc.setVisible(false);
		
		lblPrice = new JLabel();
		itemPrice.add(lblPrice);
		btnEditPrice = new JButton("Edit price");
		btnEditPrice.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(itemBox.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(new JFrame(), "Pick an item first!");
					return;
				}
				String price = JOptionPane.showInputDialog("Enter new price: ");
				if(price == null) {
					return;
				}
				if(price.matches("(\\d+\\.\\d{1,2})")) {
					((Item)itemBox.getSelectedItem()).setPrice(Double.valueOf(price));
				}else {
					JOptionPane.showMessageDialog(new JFrame(), "Enter numbers only and include a decimal points");
				}
			}
			
		});
		itemPrice.add(btnEditPrice);
		btnEditPrice.setVisible(false);
		
		lblSize = new JLabel();
		itemSize.add(lblSize);
		btnEditSize = new JButton("Edit size");
		btnEditSize.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(itemBox.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(new JFrame(), "Pick an item first!");
					return;
				}
				String height = JOptionPane.showInputDialog("Enter new height: ");
				if(height == null) {
					return;
				}
				String width = JOptionPane.showInputDialog("Enter new width: ");
				if(width == null) {
					return;
				}
				String length = JOptionPane.showInputDialog("Enter new length: ");
				if(length == null) {
					return;
				}
				if(height.matches("^[0-9]+$") && width.matches("^[0-9]+$") && length.matches("^[0-9]+$")) {
					((Item)itemBox.getSelectedItem()).setDimensions(Integer.valueOf(height), Integer.valueOf(width), Integer.valueOf(length));
					try {
						dbh.saveToFile();
						//update();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}else {
					JOptionPane.showMessageDialog(new JFrame(), "enter numbers only");
				}
			}
			
		});
		itemSize.add(btnEditSize);
		btnEditSize.setVisible(false);
		
		lblLeft = new JLabel();
		itemLeft.add(lblLeft);
		btnEditLeft= new JButton("Edit availability");
		btnEditLeft.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(itemBox.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(new JFrame(), "Pick an item first!");
					return;
				}
				String numLeft = JOptionPane.showInputDialog("Enter number of items left: ");
				if(numLeft == null) {
					return;
				}
				if(numLeft.matches("^[0-9]+$")) {
					((Item)itemBox.getSelectedItem()).setNumLeft(Integer.valueOf(numLeft));
				}else {
					JOptionPane.showMessageDialog(new JFrame(), "Enter numbers only");
				}
			}
			
		});
		itemLeft.add(btnEditLeft);
		btnEditLeft.setVisible(false);
		JPanel remove = new JPanel();
		remove.setVisible(false);
		
		if(user == null || user.getClass() == Customer.class) {
			btnAddToList.setVisible(true);
		}else {
			if(user.getClass() == Admin.class) {
				lblManager.setVisible(true);
				btnChangeManager.setVisible(true);
			}
			btnEditHours.setVisible(true);
			btnEditLocation.setVisible(true);
			btnEditSize.setVisible(true);
			btnEditDesc.setVisible(true);
			btnEditPrice.setVisible(true);
			btnEditLeft.setVisible(true);
			addToSale.setVisible(true);
			remove.setVisible(true);
		}
		
		JButton btnRemove= new JButton("Remove Item");
		btnRemove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(itemBox.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(new JFrame(), "Pick an item first!");
					return;
				}
				myStore.removeItem(itemBox.getSelectedItem().toString());
				JOptionPane.showMessageDialog(new JFrame(), "Removed!");
				try {
					dbh.saveToFile();
					//update();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		});
		remove.add(btnRemove);
		
		add(location);
		add(hoursOpen);
		add(search);
		add(result);
		add(itemName);
		add(itemDesc);
		add(itemPrice);
		add(itemSize);
		add(itemLeft);
		add(addToSale);
		add(remove);
		setSize(488, 400);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(searchType.getSelectedIndex() == 0) {
			txtName.setVisible(true);
			searchCategory.setVisible(false);
		}else{
			txtName.setVisible(false);
			searchCategory.setVisible(true);
		}
	}
	
	private void itemsByCategory() {
		itemBox.removeAllItems();
		for(Item i : this.searchStore.searchByCategory(searchCategory.getSelectedItem().toString())) {
			itemBox.addItem(i);
		}
	}
	
	private void itemsByName() {
		itemBox.removeAllItems();
		for(Item i : this.searchStore.searchByName(txtName.getText())) {
			itemBox.addItem(i);
		}
	}
	
	private void itemsOnSale(){
		itemBox.removeAllItems();
		for(Item i : myStore.getSaleItems()) {
			itemBox.addItem(i);
		}
	}
	
	public void update() throws ClassNotFoundException, IOException {
		dbh.loadFromFile();
		myUser = dbh.getUser(myUser);
		myStore = dbh.getStore(myStore.getLocation());
		System.out.println(myUser.getSavedStore() == myStore);
		searchStore = new Search(myStore);
		
	}

}
