package logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Database implements Serializable{
	private ArrayList<Store> stores = new ArrayList<Store>();
	private ArrayList<User> users = new ArrayList<User>();
	private ArrayList<Item> items = new ArrayList<Item>();
	
	
	
	public Database() {
	}
	
	public ArrayList<Store> getStores() {
		ArrayList<Store> storesCpy = (ArrayList<Store>) stores.clone();
		return storesCpy;
	}
	
	public Store getStore(String location) {
		for(Store s : stores) {
			if(s.getLocation().equals(location)) {
				return s;
			}
		}
		return null;
	}
	
	public void setStores(ArrayList<Store> newStores) {
		stores = newStores;
	}
	
	public boolean addUser(User newUser) {
		for (User u : users) {
			if(u.getUsername().equals(newUser.getUsername())) {
				return false;
			}
		}
		users.add(newUser);
		return true;
	}
	
	public boolean removeUser(String username) {
		for (User u : users) {
			if(u.getUsername().equals(username)) {
				users.remove(u);
				return true;
			}
		}
		return false;
	}
	
	
	public User getUser(String username, String password) {
		for(User u : users) { 
			if(u.authenticate(username, password)) {
				return u;
			}
		}
		return null;
	}
	
	public User getUser(User user) {
		for(User u : users) {
			if(u.equals(user)) {
				return user;
			}
		}
		return null;
	}
	
	public Manager getManager(String manager) {
		for(User u : users) {
			if(u.getUsername().equals(manager) && u.getClass() == Manager.class) {
				return (Manager) u;
			}
		}
		return null;
		
	}
	
	public boolean isUsernameTaken(String username) {
		for(User u : users) {
			if(u.getUsername().equals(username)) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Item> getItems(){
		ArrayList<Item> itemsCpy = (ArrayList<Item>) items.clone();
		return itemsCpy;
	}
	
	public void setItems(ArrayList<Item> newItems) {
		items = newItems;
	}
}


