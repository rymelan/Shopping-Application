package logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Search implements Serializable{
	private Store store;
	
	public Search(Store myStore) {
		store = myStore;
	}
	
	public ArrayList<Item> searchByName(String name) {
		Pattern pattern = Pattern.compile(name, Pattern.CASE_INSENSITIVE);
		ArrayList<Item> items = store.getItems();
		ArrayList<Item> itemsReturn = new ArrayList<Item>();
		for(Item i : items) {
			if(pattern.matcher(i.getName()).find()) {
				itemsReturn.add(i);
			}
		}
		return itemsReturn;
	}
	
	public ArrayList<Item> searchByCategory(String category) {
		Pattern pattern = Pattern.compile(category, Pattern.CASE_INSENSITIVE);
		ArrayList<Item> items = store.getItems();
		ArrayList<Item> itemsReturn = new ArrayList<Item>();
		for(Item i : items) {
			if(pattern.matcher(i.getCategory()).find()) {
				itemsReturn.add(i);
			}
		}
		return itemsReturn;
	}
}
