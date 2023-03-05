package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logic.Admin;
import logic.Customer;
import logic.DatabaseHandler;
import logic.Item;
import logic.Manager;
import logic.Store;

class AdminTest {
	
	Admin admin;
	DatabaseHandler dbh = new DatabaseHandler();
	
	@BeforeEach
	void setUp() throws ClassNotFoundException, IOException {
		admin = new Admin("ad", "min");
		ArrayList<Store> stores = new ArrayList<Store>();
		stores.add(new Store(null, null, "location"));
		dbh.setStores(stores);
	}
	@Test
	void addStore() throws ClassNotFoundException, IOException {
		admin.setSavedStore(new Store(null, null, "hi"));
		admin.addRemoveStore(true);
		assertEquals(dbh.getStores().get(dbh.getStores().size()-1).getLocation(), "hi");
	}
	
	@Test
	void removeStore() throws ClassNotFoundException, IOException {
		Store store = new Store(null, null, "hi");
		admin.setSavedStore(store);
		admin.addRemoveStore(false);
		assertFalse(dbh.getStores().contains(store));
	}
	
	@Test
	void addManager() throws ClassNotFoundException, IOException {
		
		Manager newManager = new Manager("11", "22");
		admin.addManager(newManager);
		assertEquals(dbh.getUser("11", "22"), newManager);
	}
	@Test
	void removeManager() throws ClassNotFoundException, IOException {
		admin.removeManager("11");
		assertEquals(dbh.getUser("11", "22"), null);
	}
	@Test
	void setStoreManager() {
		admin.setSavedStore(dbh.getStores().get(0));
		Manager newManager = new Manager("11", "22");
		admin.setStoreManager(newManager);
		assertEquals(dbh.getStores().get(0).getManager(), newManager);
		
	}

}
