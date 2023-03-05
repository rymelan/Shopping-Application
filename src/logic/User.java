package logic;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2411066338419611294L;
	private String username;
	private String password;
	private Store savedStore;
	private int security = 1;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password; 
	}
	
	public boolean authenticate(String user, String pass) {
		return user.equals(username) && pass.equals(password);
	}
	
	public boolean setPassword(String oldPassword, String newPassword) {
		if (oldPassword.equals(password)) {
			password = newPassword;
			return true;
		}
		return false;
	}
	
	public boolean setUsername(String myPassword, String newUsername) {
		if (myPassword.equals(password)) {
			username = newUsername;
			return true;
		}
		return false;
	}
	
	public String getUsername() {
		return username;
	}
	
	public Store getSavedStore() {
		return savedStore;
	}
	
	public void setSavedStore(Store newStore) {
		savedStore = newStore;
	}		
	
	public int getSecurity() {
		return security;
	}
	
	public void setSecurity(int sec) {
		security = sec;
	}
	
	@Override 
	public boolean equals(Object o) {
		if(o==null) {
			return false;
		}
		if(((User)o).authenticate(username, password)) {
			return true;
		}
		return false;
	}
}
