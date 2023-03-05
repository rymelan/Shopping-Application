package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import logic.DatabaseHandler;
import logic.Item;
import logic.Search;

class SearchTests {

	@Test
	void testSearchByName() throws ClassNotFoundException, IOException {
		DatabaseHandler data = new DatabaseHandler();
		data.loadFromFile();
		data.getStores().get(0).addItem(new Item("cat", "apple", 1, 2, 3, 4, "hi", 4), new Point(0, 1));
		Search search = new Search(data.getStores().get(0));
		ArrayList<Item> result = search.searchByName("apple");
		assertTrue(result.size() > 0);
		
		
	}
	@Test
	void testSearchByCategory() throws ClassNotFoundException, IOException {
		DatabaseHandler data = new DatabaseHandler();
		data.loadFromFile();
		Item i = new Item("cat", "cat", 1, 2, 3, 4, "hi", 4);
		data.getStores().get(0).addItem(i, new Point(0, 1));
		Search search = new Search(data.getStores().get(0));
		ArrayList<Item> result = search.searchByCategory("cat");
		assertTrue(result.contains(i));
		
		
	}

}
