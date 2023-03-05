package logic;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SetDatabase {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		DatabaseHandler data = new DatabaseHandler();
		data.saveToFile();
		data.loadFromFile();
		Item item1 = new Item("electronics", "Iphone1", 1, 2, 3, 10.3, "good", 2);
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
		ArrayList<Item> items = new ArrayList<Item>(Arrays.asList(itemss));
		ArrayList<Store> stores = new ArrayList<Store>();
		
		Store store1 = new Store(new ArrayList<Item>(), new HashMap<Item, Point>(), "Jane Street 28");
		Store store2 = new Store(new ArrayList<Item>(), new HashMap<Item, Point>(), "Jacksonvile avenue 2");
		Store store3 = new Store(new ArrayList<Item>(), new HashMap<Item, Point>(), "Yonge Street West 45");
		
		for(Item i : items) {
			store1.addItem(i.getCopy(), new Point(((int) (Math.random() * 100)), ((int) (Math.random() * 100))));
		}
		stores.add(store1);
		for(Item i : items) {
			store2.addItem(i.getCopy(), new Point(((int) (Math.random() * 100)), ((int) (Math.random() * 100))));
		}
		stores.add(store2);
		for(Item i : items) {
			store3.addItem(i.getCopy(), new Point(((int) (Math.random() * 100)), ((int) (Math.random() * 100))));
		}
		stores.add(store3);
		data.setStores(stores);
		Admin admin = new Admin("adminuser", "adminpass");
		Manager manager = new Manager("manageruser", "managerpass");
		data.addUser(admin);
		data.addUser(manager);
		data.saveToFile();
		 
	}

}
