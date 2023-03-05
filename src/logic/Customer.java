package logic;

import java.io.Serializable;
import java.util.ArrayList;

public class Customer extends User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2924105216944919745L;
	private ArrayList<Item> shoppingList = new ArrayList<Item>();
	
	public Customer(String username, String password) {
		super(username, password);
	}
	
	public boolean addItemToList(Item item) {
		for (Item i : shoppingList) {
			if (i.equals(item)) {
				return false;
			}
		}
		shoppingList.add(item);
		return true;
	}
	
	public boolean RemoveItemfromList(Item item) {
		for (Item i : shoppingList) {
			if (i.equals(item)) {
				shoppingList.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Item> getList(){
		return (ArrayList<Item>) shoppingList.clone();
	}
}
