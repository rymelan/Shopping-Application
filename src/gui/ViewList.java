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

import logic.Customer;
import logic.DatabaseHandler;
import logic.Item;
import logic.User;

public class ViewList extends JFrame 
		implements ActionListener{
	private JComboBox<Item> items;
	private JLabel lblName, lblDesc, lblPrice, lblSize, lblLeft;
	private Customer user;
	ViewList(Customer myUser, StartupScreen screen){
		user = myUser;
		setLayout(new FlowLayout());
		setTitle("Shopping List");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
		JPanel itemName = new JPanel();
		JPanel itemDesc = new JPanel();
		JPanel itemPrice = new JPanel();
		JPanel itemSize = new JPanel();
		JPanel itemLeft = new JPanel();
		items = new JComboBox<Item>();
		for(Item i : user.getList()) {
			items.addItem(i);
		}
		items.addActionListener(this);
		
		
		lblName = new JLabel("Item name: " + items.getSelectedItem().toString());
		itemName.add(lblName);
		
		lblDesc = new JLabel("Description: " + ((Item)items.getSelectedItem()).getDescription());
		itemDesc.add(lblDesc);
		
		
		lblPrice = new JLabel("Item price: " + ((Item)items.getSelectedItem()).getPrice());
		itemPrice.add(lblPrice);
		
		String size = String.valueOf(((Item)items.getSelectedItem()).getHeight()) + " in X " +
				String.valueOf(((Item)items.getSelectedItem()).getWidth()) + " in X " + 
				String.valueOf(((Item)items.getSelectedItem()).getLength()) + " in";
		lblSize = new JLabel("Item size (Length x width x height): " + size);
		itemSize.add(lblSize);
		
		lblLeft = new JLabel("Number left in stock: " + ((Item)items.getSelectedItem()).getNumLeft());
		itemLeft.add(lblLeft);
		add(new JLabel("Items in list: "));
		JButton btnRemove = new JButton("Remove item from list");
		btnRemove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				user.RemoveItemfromList((Item) items.getSelectedItem());
				JOptionPane.showMessageDialog(new JFrame(), "Item removed!");
				items.removeAllItems();
				for(Item i : user.getList()) {
					items.addItem(i);
				}
				
			}
			
		});
		
		JButton viewItem = new JButton("View Item");
		viewItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(items.getSelectedItem() == null) {
					
				}else {
					lblName.setText("Item name: " + items.getSelectedItem().toString());
					lblDesc.setText("Description: " + ((Item)items.getSelectedItem()).getDescription());
					String price =  String.format("%.2f", ((Item)items.getSelectedItem()).getPrice());
					lblPrice.setText("Item price: " + price);
					String size = String.valueOf(((Item)items.getSelectedItem()).getHeight()) + " in X " +
						String.valueOf(((Item)items.getSelectedItem()).getWidth()) + " in X " + 
						String.valueOf(((Item)items.getSelectedItem()).getLength()) + " in";
					lblSize.setText("Item size (Length x width x height): " + size);
					lblLeft.setText("Number left in stock: " + ((Item)items.getSelectedItem()).getNumLeft());	
			
					}
				}
		});
		
		
		add(items);
		add(btnRemove);
		add(viewItem);
		add(itemName);
		add(itemDesc);
		add(itemPrice);
		add(itemSize);
		add(itemLeft);
		setSize(488, 400);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
