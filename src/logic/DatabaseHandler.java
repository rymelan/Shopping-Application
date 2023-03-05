package logic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;


public class DatabaseHandler {
	/**
	 * 
	 */
	//private static final long serialVersionUID = 2411066338419611294L;
	private static final DatabaseHandler handler = new DatabaseHandler();
	private final static String DATA_PATH = "data.ser";
	private static Database database = new Database();
	
	public static DatabaseHandler getInstance() throws ClassNotFoundException, IOException{
		return handler;
	}
	
	public User getUser(String username, String password) {
		return database.getUser(username, password);
	}
	
	public User getUser(User user) {
		return database.getUser(user);
	}
	
	public Manager getManager(String manager) {
		return database.getManager(manager);
	}
	
	public boolean addUser(User newUser) {
		return database.addUser(newUser);
	}
	
	public boolean removeUser(String user) {
		return database.removeUser(user);
	}
	
	public ArrayList<Item> getItems(){
		return database.getItems();
	}
	 
	public boolean addItem(Item newItem) {
		ArrayList<Item> items = database.getItems();
		for(Item i : items) {
			if(i.getName().equals(newItem.getName())) {
				return false;
			}
		}
		items.add(newItem);
		database.setItems(items);
		return true;
	}
	
	public ArrayList<Store> getStores(){
		return database.getStores();
	}
	
	public Store getStore(String loc) {
		return database.getStore(loc);
	}
	public void setStores(ArrayList<Store> stores){
		database.setStores(stores);
	}
	
	public boolean isUsernameTaken(String username) {
		return database.isUsernameTaken(username);
	}
	
	public void saveToFile() throws IOException{
		
		 OutputStream file = new FileOutputStream(DATA_PATH);
	     OutputStream buffer = new BufferedOutputStream(file);
	     ObjectOutput output = new ObjectOutputStream(buffer);

	     
	     output.writeObject(database);
	     output.close();
	}
	
	public void loadFromFile() throws IOException, ClassNotFoundException {

        InputStream file = new FileInputStream(DATA_PATH);
        InputStream buffer = new BufferedInputStream(file);
        ObjectInput input = new ObjectInputStream(buffer);

        // serialize the Map
        database = (Database) input.readObject();
        input.close();
    }
	
}
