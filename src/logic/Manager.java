package logic;

import java.awt.Point;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Manager extends User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Search search = new Search(this.getSavedStore());
	public Manager(String username, String password) {
		super(username, password);
	}
	
	public ArrayList<Item> getItem(String name) {
		return search.searchByName(name);
	}
	
	public boolean addItem(Item newItem, Point itemLoc) throws ClassNotFoundException, IOException {
		if(DatabaseHandler.getInstance().getItems().contains(newItem)) {
			return this.getSavedStore().addItem(newItem, itemLoc);
		}else {
			return false;
		}
		
	}
	
	public boolean removeItem(String name) {
		return this.getSavedStore().removeItem(name);
	}
	
	public boolean addSaleItem(String name) {
		return this.getSavedStore().addSaleItem(name);
	}
	
	public boolean removeSaleItem(String name) {
		return this.getSavedStore().removeSaleItem(name);
	}
	
	public void setLocation(String newLoc) {
		this.getSavedStore().setLocation(newLoc);
	}
	
	public boolean setWorkingHours(String open, String close) {
		return this.getSavedStore().setWorkingHours(open, close);
	}
	
	public boolean editMap(Item item, Point newPoint) {
		return this.getSavedStore().editMap(item, newPoint);
	}
	
}

