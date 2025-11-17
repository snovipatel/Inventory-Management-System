
public class Apparel extends ProductItem implements Purchaseable{

	private String size;
	
	private String material;
	
	public Apparel(String id, String name, double price, int quantity, String size, String material) {
		super(id, name, price, quantity);
		this.size = size;
		this.material = material;
		
	}
	
	//getters
	public String getSize() {
		return size;
	}

	public String getMaterial() {
		return material;
	}

	/* 
	 * Implements the required method from the parent Interface
	 * @param units - The number of units to calculate the cost of Apparel
	 * @return The total cost of devices for the given number of units
	 */
	@Override
	public double calculateCost(int units)
	{
		if(units>0)
		{
			return getPrice() * units;
		}
		return 0; //invalid units value
	}

	/* 
	 * Implements the abstract method from parent class to provide specific details about this class
	 * @return Custom Formatted String representation of Apparel details
	 */

	@Override
	public String getItemDetails() {
		
		return String.format("Apparel: %s (ID: %s)%n Price: $%.2f, Stock: %d%n Size: %s, Material: %s", 
				getName(), getItemID(), getPrice(), getQuantityInStock(), size, material);
	}
	
	

	
}
