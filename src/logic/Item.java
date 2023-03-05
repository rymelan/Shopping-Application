package logic;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Item implements Serializable{
	private String category;// item category is the category the item falls into
	//current categories are food, electronics and furniture
	String name;
	int height;
	int width;
	int length;
	double price;
	String description;
	int numLeft = 0;
	
	public Item(String cat, String name, int height, int width, int length, double price, String description, int numLeft){
		this.category = cat;
		this.name = name;
		this.height = height;
		this.width = width;
		this.length = length;
		this.price = price;
		this.description = description;
		this.numLeft = numLeft;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setDimensions(int height, int width, int length) {
		this.height = height;
		this.width = width;
		this.length = length;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getLength() {
		return length;
	}
	
	public void setPrice(double price) {
		Double truncatedDouble = BigDecimal.valueOf(price)
			    .setScale(2, RoundingMode.HALF_UP)
			    .doubleValue();
		this.price = price;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setNumLeft(int numLeft) {
		this.numLeft = numLeft;
	}
	
	public int getNumLeft() {
		return numLeft;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String cat) {
		category = cat;
	}
	
	public Item getCopy() {
		Item i = new Item(category, name, height, width, length, price, description, numLeft);
		return i;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null) {
			return false;
		}
		if(this.name.equals(((Item) o).getName())) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return this.getName();
	}

}
