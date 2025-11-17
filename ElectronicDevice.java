
public class ElectronicDevice extends ProductItem implements Purchaseable{
	
	private String brand;

	private int warrantyMonths;
	
	//constructor
	public ElectronicDevice(String id, String name, double price, int quantity, String brand , int warranty) {
		
		super(id, name, price, quantity);
		this.brand = brand;
		this.warrantyMonths = warranty;
	}
	
	//getters
	public String getBrand() {
		return brand;
	}

	public int getWarrantyMonths() {
		return warrantyMonths;
	}

	/* 
	 * Implements the abstract method from parent class to provide specific details about this class
	 * @return Custom Formatted String representation of Electronic Devices details
	 */
	@Override
	public String getItemDetails() {
		
		return String.format("Electronic Device: %s (ID: %s)%n Price: $%.2f, Stock: %d%n Brand: %s, Warranty: %d months", 
								getName(), getItemID(), getPrice(), getQuantityInStock(), brand, warrantyMonths);
	}
	
	/* 
	 * Implements the required method from the parent Interface
	 * @param units - The number of units to calculate the cost of Electronic devices
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

	
	
}
