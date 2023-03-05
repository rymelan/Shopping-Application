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

import logic.Customer;
import logic.DatabaseHandler;
import logic.Item;
import logic.Store;
import logic.User;

class DatabaseTests {

	DatabaseHandler data = new DatabaseHandler();
	ArrayList<Item> items;
	ArrayList<Store> stores;
	
	
	@Before
	void makeEmpty() throws IOException {
		data.saveToFile();
	}
	@BeforeEach
	void setup() throws IOException, ClassNotFoundException {
		data.loadFromFile();
		Item item1 = new Item("Electronics", "Iphone1", 1, 2, 3, 10.3, "good", 2);
		Item item2 = new Item("electronics", "Iphone2", 4, 5, 7, 10.3, "great", 2);
		Item item3 = new Item("food", "apple", 8, 2, 3, 10.3, "tasty", 2);
		Item item4 = new Item("food", "orange", 10, 5, 4, 10.3, "yummy", 2);
		Item item5 = new Item("food", "red apple", 1, 2, 6, 10.3, "good", 2);
		Item item6 = new Item("electronics", "Galaxy", 3, 2, 4, 10.3, "good", 2);
		Item item7 = new Item("furniture", "Table", 1, 2, 4, 10.3, "good", 2);
		Item item8 = new Item("furniture", "Chair", 3, 1, 2, 10.3, "good", 2);
		Item item9 = new Item("furniture", "Wooden Chair", 4, 1, 2, 10.3, "good", 2);
		Item item10 = new Item("furniture", "Office Chair", 1, 5, 2, 10.3, "good", 2);
		Item [] itemss= {item1, item2, item3, item4, item5, item6, item7, item8, item9, item10};
		items = new ArrayList<Item>(Arrays.asList(itemss));
		stores = new ArrayList<Store>();
		
		Store store1 = new Store(new ArrayList<Item>(), new HashMap<Item, Point>(), "Jane Street 28");
		Store store2 = new Store(new ArrayList<Item>(), new HashMap<Item, Point>(), "Jacksonvile avenue 2");
		Store store3 = new Store(new ArrayList<Item>(), new HashMap<Item, Point>(), "Yonge Street West 45");
		
		for(Item i : items) {
			store1.addItem(i, new Point(((int) (Math.random() * 100)), ((int) (Math.random() * 100))));
			data.addItem(i);
		}
		stores.add(store1);
		for(Item i : items) {
			store2.addItem(i, new Point(((int) (Math.random() * 100)), ((int) (Math.random() * 100))));
		}
		stores.add(store2);
		for(Item i : items) {
			store3.addItem(i, new Point(((int) (Math.random() * 100)), ((int) (Math.random() * 100))));
		}
		stores.add(store3);
		
		data.setStores(stores);
		
		Customer user = new Customer("hi", "bye");
		data.addUser(user);
		data.saveToFile();
	}
	
	@Test
	void getUserTest() throws ClassNotFoundException, IOException {
		
		assertNotEquals(data.getUser("hi", "bye"), null);
		
	}
	
	@Test
	void addUserTest() throws ClassNotFoundException, IOException {
		
		User u = new Customer("user", "pass");
		data.addUser(u);
		
		assertEquals(data.getUser("user", "pass"), u);
		
	}
	
	@Test
	void removeUser() throws ClassNotFoundException, IOException {
		data.removeUser("hi");
		
		
		assertEquals(data.getUser("hi", "bye"), null);
		
	}
	@Test
	void getItems() throws ClassNotFoundException, IOException {
		Item i = new Item("category", "item", 1, 2, 3, 4.4, "somthing", 0);
		items.add(i);
		
		for(Item item : data.getItems()) {
			assertTrue(items.contains(item));
		}
		
		
	}
	@Test
	void addItem() throws ClassNotFoundException, IOException {
		Item i = new Item("category", "item", 1, 2, 3, 4.4, "somthing", 0);
		items.add(i);
		data.addItem(i);
		
		assertEquals(data.getItems().get(data.getItems().size()-1).getName(), i.getName());
	}
	@Test
	void getStores() throws ClassNotFoundException, IOException {
		
		assertEquals(data.getStores(), stores);
		
	}
	@Test
	void setStores() throws ClassNotFoundException, IOException {
		
		ArrayList<Store> newStores = new ArrayList<Store>();
		Store store = new Store(null, null, "here");
		newStores.add(store);
		data.setStores(newStores);
		assertEquals(data.getStores().contains(store), true);
		data.setStores(stores);
		
	}
	@Test
	void isUsernameTaken() throws ClassNotFoundException, IOException {
		data.loadFromFile();
		assertEquals(data.isUsernameTaken("hi"), true);
		
	}
	@Test
	void saveTofile() throws ClassNotFoundException, IOException {
		data.loadFromFile();
		
		data.removeUser("hi");
		
		data.saveToFile();
		data.loadFromFile();
		assertEquals(data.getUser("hi", "bye"), null);
		
	}
}
