package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import logic.Customer;
import logic.Item;
import logic.Store;

class CustomerTest {
	Customer user = new Customer("user", "pass");
		
	@Test
	void testAuthenticate() {
		
		assertTrue(user.authenticate("user", "pass"));
	}
	
	@Test
	void testsetPassword() {
		user.setPassword("pass", "pass1");
		
		assertTrue(user.authenticate("user", "pass1"));
	}
	
	@Test
	void testsetPasswordFail() {
		user.setPassword("pass12", "pass1");
		
		assertFalse(user.authenticate("user", "pass1"));
	}
	
	@Test
	void testSetUser() {
		user.setUsername("pass", "user1");
		
		assertTrue(user.authenticate("user1", "pass"));
	}
	@Test
	void testSetUserFail() {
		
		assertFalse(user.setUsername("pass1", "user1"));
	}
	
	@Test
	void testgetUsername() {
		
		assertEquals(user.getUsername(), "user");
	}
	
	@Test
	void testGetSetSavedStore() {
		Store store = new Store(null, null, "location");
		user.setSavedStore(store);
		assertEquals(user.getUsername(), "user");
	}
	
	@Test
	void testGetSecurity() {
		
		assertEquals((Integer)user.getSecurity(), 1);
	}
	
	@Test
	void testSetSecurity() {
		user.setSecurity(0);
		assertEquals((Integer)user.getSecurity(), 0);
	}
	
	@Test
	void testEquals() {
		Customer newcus = new Customer("user", "pass");
		assertEquals(user, newcus);
	}
	
	@Test
	void testAddToList() {
		Item i = new Item(null, "item", 0, 0, 0, 0, null, 0);
		user.addItemToList(i);
		assertTrue(user.getList().contains(i));
	}
	
	@Test
	void testAddDupe() {
		Item i = new Item(null, "item", 0, 0, 0, 0, null, 0);
		Item ii = new Item(null, "item", 0, 0, 0, 0, null, 0);
		user.addItemToList(i);
		
		assertFalse(user.addItemToList(ii));
	}
	
	@Test
	void removeFromList() {
		Item i = new Item(null, "item", 0, 0, 0, 0, null, 0);
		user.addItemToList(i);
		user.RemoveItemfromList(i);
		
		assertFalse(user.getList().contains(i));
	}
	
	
	
}
