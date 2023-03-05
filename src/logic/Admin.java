package logic;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Admin extends Manager implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Admin(String username, String password) throws ClassNotFoundException, IOException {
		super(username, password); 
		// TODO Auto-generated constructor stub
	}
	
	public boolean addItemToDatabase(Item newItem) throws ClassNotFoundException, IOException {
		return DatabaseHandler.getInstance().addItem(newItem);
	}
	
	public void addRemoveStore(boolean add) throws ClassNotFoundException, IOException {
		ArrayList<Store> stores = DatabaseHandler.getInstance().getStores();
		if(add) {
			stores.add(getSavedStore());
		}else {
			if(getSavedStore().getManager() != null) {
				getSavedStore().getManager().setSavedStore(null);
			}
			stores.remove(getSavedStore());
		}
		DatabaseHandler.getInstance().setStores(stores);
	}
	
	public void setStoreManager(Manager newManager) {
		if(this.getSavedStore().getManager() != null) {
			this.getSavedStore().getManager().setSavedStore(null);
		}
		this.getSavedStore().setManager(newManager);
		newManager.setSavedStore(this.getSavedStore());
	}
	
	public boolean addManager(Manager newManager) throws ClassNotFoundException, IOException {
		return DatabaseHandler.getInstance().addUser(newManager);
	}
	
	public boolean removeManager(String manager) throws ClassNotFoundException, IOException { 
		return DatabaseHandler.getInstance().removeUser(manager);
	}
}
