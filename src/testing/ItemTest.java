package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import logic.Item;

class ItemTest {
	
	Item item = new Item("food", "apple", 2, 4, 5, 6, "good", 3);
	@Test
	void testSetName() {
		item.setName("lemon");
		assertEquals(item.getName(), "lemon");
	}
	
	@Test
	void testSetDimensions() {
		item.setDimensions(0, 10, 20);
		assertEquals(item.getHeight(), 0);
		assertEquals(item.getWidth(), 10);
		assertEquals(item.getLength(), 20);
	}
	
	@Test
	void testSetPrice() {
		item.setPrice(2);
		assertEquals(item.getPrice(), 2);
	}
	
	@Test
	void testSetDescription() {
		item.setDescription("tasty");
		assertEquals(item.getDescription(), "tasty");
		
	}
	@Test
	void testSetNumLeft() {
		item.setNumLeft(14);
		assertEquals(item.getNumLeft(), 14);
		
	}
	
	@Test
	void testSetCategory() {
		item.setCategory("fruit");
		assertEquals(item.getCategory(), "fruit");
		
	}
	

}
