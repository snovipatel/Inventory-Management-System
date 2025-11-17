import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InventoryManagementSystem {

	private ArrayList<ProductItem> inventory;
	private final String FILE = "inventory.txt";
	private Scanner scanner;
	
	public InventoryManagementSystem() {
		this.inventory = new ArrayList<>();
		this.scanner = new Scanner (System.in);
		loadInventoryFromFile();
	}
	
	private void loadInventoryFromFile() {
		System.out.println("loading contents from file: " + FILE + "...");
		
		try(BufferedReader reader = new BufferedReader(new FileReader (FILE))) 
		{
			String line;
			int lineNumber = 0;
			while ((line= reader.readLine()) != null) 
			{
				lineNumber++;
				if(line.trim().isEmpty())
				{
					continue;//skip empty line
				}
				
				try {
					String[] parts = line.split(",\\s*");
					String type = parts[0].trim();
					
					//validation check for required data for an item
					if (parts.length< 5)
					{
						System.out.println("Not enough data for itm. Skipping line: " + lineNumber + ": " + line);
						continue;
					}
					
					String itemID = parts[1].trim();
					String name = parts[2].trim();
					double price = Double.parseDouble(parts[3].trim());
					int quantityInStock = Integer.parseInt(parts[4].trim());
					
					ProductItem item = null;
					
					switch(type)
					{
						case "ElectronicDevice" : 
							if(parts.length == 7) 
							{
								String brand = parts[5].trim();
								int warrantyMonths = Integer.parseInt(parts[6].trim());
								item = new ElectronicDevice(itemID, name, price, quantityInStock, brand, warrantyMonths);
				
							}
							else
							{
								System.out.println("Unkown data in Electronic device item = Incorrect number of fields.");
							
							}
							break;
						
						case "Apparel" : 
							if(parts.length == 7) 
							{	
								String size = parts[5].trim();
								String material = parts[6].trim();
								item = new Apparel(itemID, name, price, quantityInStock, size, material);
				
							}
							else
							{
								System.out.println("Unkown data in Apparel item = Incorrect number of fields.");
							
							}
							break;
						
						default:
							System.out.println("Unknown Item type: Skipping line");
							break;
					}
					if(item!= null)
					{
						inventory.add(item);
					}
				}
				catch(NumberFormatException nfe) 
				{
					System.err.println("Insufficient data for item in this  line. Numeric parse error." + nfe.getMessage() + "in line: " +  line);
					
				}
				catch(ArrayIndexOutOfBoundsException aioobe)
				{
					System.err.println("Insufficient data for item in this  line. Missing data fields." + aioobe.getMessage() + "in line: " +  line);
					
				}
			}
			System.out.println("Inventory loaded successfully from file." + inventory.size() + " items found and added");
		}
		catch (IOException ioe)
		{
			System.err.println("Error reading inventory file: " + ioe.getMessage());
			System.err.println("Please ensure the file: " +  FILE + " exists in the same directory as the program.");
 		}
		catch (Exception e)
		{
			System.err.println("Unexpected error occured during file reading..." + e.getClass().getSimpleName() + "-" + e.getMessage());
		}
	}
	
	public void displayAllItems() {
		if(inventory.isEmpty())
		{
			System.out.println("\n Inventory is currently empty.");
		}
		else
		{
			System.out.println("\n --Current Inventory--");
			for(ProductItem item : inventory)
			{
				System.out.println(item.getItemDetails());
				System.out.println("-------------------------");
			}
		}
	}
	
	public ArrayList<ProductItem> searchItemByName(String searchTerm)
	{
		ArrayList<ProductItem> results = new ArrayList<>();
		String lowercase = searchTerm.toLowerCase();
		for(ProductItem item: inventory)
		{
			if(item.getName().toLowerCase().contains(lowercase))
				results.add(item);
		}
		return results;
	}
	
	public ProductItem findItemByID(String itemID)
	{
		for(ProductItem item: inventory)
		{
			if(item.getItemID().equalsIgnoreCase(itemID))
				return item;
		}
		return null; //item not found
	}
	
	public void sellItem(String itemID, int quantity)
	{
		ProductItem item = findItemByID(itemID);
		if(item == null) 
		{
			System.out.println("Item not found.");
		}
		else if (quantity <= 0 ) 
		{
			System.out.println("Quantity to sell has to be positive.");
		}
		else if (item.getQuantityInStock() >= quantity)
		{
			item.updateQuantity(-quantity);
			if(item instanceof Purchaseable)
			{
				Purchaseable purItem = (Purchaseable) item;
				System.out.printf("Successfully sold %d units of %s. Total Cost: $%.2f%n",  quantity, item.getName(), purItem.calculateCost(quantity));
				
			}
			else
			{
				System.out.println("Item not purchaseable.");
			}
		}
		else
		{
			System.out.println("Not enough stock for item");
		}
	}
	
	public void restockItem(String itemID, int quantity)
	{
		ProductItem item = findItemByID(itemID);
		if(item == null)
		{
			System.out.println("Item not found.");
		}
		else if (quantity <= 0)
		{
			System.out.println("Quantity cannot be negative");
			
		}
		else 
		{
			item.updateQuantity(quantity);
			System.out.printf("Successfully restocked %d units of %s. New Stock: %d%n",  quantity, item.getName(), item.getQuantityInStock());
			
		}
	}
	
	public void displayMenu()
	{
		System.out.println("\n --Inventory Management System--");
		System.out.println("1. Display all Items");
		System.out.println("2. Search Item by Name");
		System.out.println("3. Sell Items");
		System.out.println("4. Restock Items");
		System.out.println("5. Exit");
		System.out.print("Enter your choice: ");
	}
	
	public void run()
	{
		int choice = 0;
		while (choice != 5)
		{
			displayMenu();
			try 
			{
				choice = scanner.nextInt();
				scanner.nextLine();
				
				switch(choice)
				{
					case 1:
						displayAllItems();
						break;
						
					case 2:
						System.out.print("Enter Item name or phrase to search: ");
						String searchTerm = scanner.nextLine();
						ArrayList<ProductItem> searchResults = searchItemByName(searchTerm);
						if(searchResults.isEmpty())
						{
							System.out.println("No matching items found for search term.");
						}
						else 
						{
							System.out.println("\n --Search Results--");
							for(ProductItem item: searchResults)
							{
								System.out.println(item.getItemDetails());
								System.out.println("-----------------------------");
							}
						}
						break;
						
					case 3:
						System.out.print("Enter Item ID to sell: ");
						String sellID = scanner.nextLine();
						System.out.print("Enter Quantity to sell: ");
						int sellQuantity = scanner.nextInt();
						scanner.nextLine();
						sellItem(sellID, sellQuantity);
						break;
					case 4:
						System.out.print("Enter Item ID to restock: ");
						String restockID = scanner.nextLine();
						System.out.print("Enter Quantity to restock: ");
						int restockQuantity = scanner.nextInt();
						scanner.nextLine();
						restockItem(restockID, restockQuantity);
						break;
					case 5:
						System.out.println("Exiting.");
						break;
						
						default:
							System.out.println("Inavlid choice. Please enter a number between 1 and 5 ");
							break;
						
				}
			}
			catch (InputMismatchException ime)
			{
				System.out.println("Invalid Input. Please enter a number");
				scanner.nextLine();
				choice = 0;
			}
			catch (Exception e)
			{
				System.out.println("An unexpected error occured: " + e.getMessage());
				e.printStackTrace();
				choice = 0;
			}
		}
		scanner.close();
	}
	public static void main(String[] args) {
		InventoryManagementSystem ims = new InventoryManagementSystem();
		ims.run();
	}

}
