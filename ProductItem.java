
public abstract class ProductItem {
	private String name;
	private String itemID;
	private double price;
	private int quantityInStock;
	
	//constructor
	public ProductItem(String id, String name, double price, int quantity)
	{
		this.itemID = id;
		this.name = name;
		this.price = price;
		this.quantityInStock = quantity;
		
	}
	
	//getters 
	public String getItemID() {
		return itemID;
	}
	
	public String getName() {
		return name;
		
	}
	
	public double getPrice() {
		return price;
	}
	
	public int getQuantityInStock() {
		return quantityInStock;
	}
	
	//setters for quantity
	/*
	 * Updates the quantity in stock
	 * @param change the amount to change the stock quantity by - positive for adding quantity and negative for reducing quantity
	 * @return true if quantity was updated successfully, false otherwise
	 */
	public boolean updateQuantity(int change) {
		
		if(this.quantityInStock + change >= 0) 
		{
			this.quantityInStock += change;
			return true;
		}
		else 
		{
			System.out.println("Invalid. Item quantity cannot be negative");
			return false;
		}
	}
	/*
	 * Abstract metod to be implemented by subclasses to provide specific details 
	 * @return a formatted String value containing item details 
	 */
	public abstract String getItemDetails();
	
	//override toString for custom representation
	@Override
	public String toString() {
		return "ID: " + itemID + 
				", Name: " + name +
				", Price : $" + String.format("%.2f", price) + 
				", Stock: " + quantityInStock;
	}
}
