package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import logic.Item;
import logic.Store;

class StoreTest {
	Store store = new Store(null, null, "location");
	
	
	@Test
	void testGetItem() {
		Item item = new Item("electronic", "watch", 0, 0, 0, 0, null, 0);
		store.addItem(item, null);
		assertEquals(item, store.getItem("watch"));
	}
	
	@Test
	void testRemoveItem() {
		Item item = new Item("electronic", "watch", 0, 0, 0, 0, null, 0);
		store.addItem(item, null);
		store.removeItem("watch");
		assertEquals(null, store.getItem("watch"));
	}
	
	@Test
	void testAddSaleItem() {
		Item item = new Item("electronic", "watch", 0, 0, 0, 0, null, 0);
		store.addItem(item, null);
		store.addSaleItem("watch");
		assertTrue(store.getSaleItems().contains(item));
	}

}
