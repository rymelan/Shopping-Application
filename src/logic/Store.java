package logic;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Store implements Serializable{
	private Manager manager;
	private String open = "12:00AM";
	private String close = "11:59PM";
	private ArrayList<Item> items = new ArrayList<Item>();
	private ArrayList<Item> saleItems = new ArrayList<Item>();
	public HashMap<Item, Point> storeMap = new HashMap<Item, Point>();
	private String location;
	
	public Store(ArrayList<Item> items, HashMap<Item, Point> map, String adress) {
		if(items != null) {
			this.items = items; 
		}
		
		if(map != null) {
			storeMap = map;
		}
		
		location = adress;
	}

	public boolean addItem(Item newItem, Point itemLoc) {
		if(items != null) {
			for(Item i : items) {
				if(i.getName().equals(newItem.getName())) {
					return false;
				}
			}
		}
		storeMap.put(newItem, itemLoc);
		items.add(newItem);
		return true;
	}
	
	public Item getItem(String name) {
		for(Item i: items) {
			if(i.getName().equals(name)) {	
				return i;
			}
		}
		return null;
	}
	
	public ArrayList<Item> getItems(){
		ArrayList<Item> allItems = (ArrayList<Item>) items.clone();
		return allItems;
	}
	
	public boolean removeItem(String name) {
		Item toRemove = null;
		for(Item i : items) {
			if(i.getName().equals(name)) {
				toRemove = i;
				break;
			}
		}
		if(toRemove != null) {
			items.remove(toRemove);
			saleItems.remove(toRemove);
			storeMap.remove(toRemove);
			return true;
		}
		return false;
	}
	
	public boolean addSaleItem(String name) {
		for(Item i : items) {
			if(i.getName().equals(name) && !saleItems.contains(i)) {
				saleItems.add(i);
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Item> getSaleItems() {
		return saleItems;
	}
	
	public boolean removeSaleItem(String name) {
		for(Item i : items) {
			if(i.getName().equals(name) && saleItems.contains(i)) {
				saleItems.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String newLoc) {
		location = newLoc;
	}
	
	public void setManager(Manager newManager) {
		manager = newManager;
	}
	
	public Manager getManager() {
		return manager;
	} 
	
	public boolean setWorkingHours(String open, String close) {
		if(open.length() > 7 || close.length() > 7 || open.length() < 6 || close.length() < 6) {
			return false;
		}
		if(open.matches("(([1]?[0-2])|([1-9])):[0-5][0-9]((AM)|(PM))") && close.matches("(([1]?[0-2])|([1-9])):[0-5][0-9]((AM)|(PM))")) {
			this.open = open;
			this.close = close;
			return true;
		}
		return false;
		
	}
	
	public String getOpenHour() {
		return open;
	}
	
	public String getCloseHour() {
		return close;
	}
	
	public boolean editMap(Item item, Point newPoint) {
		for(Entry<Item, Point> entry : storeMap.entrySet()) {
			if(item.equals(entry.getKey())) {
				entry.setValue(newPoint);
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<String> getCat() {
		ArrayList<String> categories = new ArrayList<String>();
		for(Item i : items) {
			if(!categories.contains(i.getCategory())) {
				categories.add(i.getCategory());
			}
		}
		return categories;
	}
	
	@Override
	public String toString() {
		return this.location;
	}

}
