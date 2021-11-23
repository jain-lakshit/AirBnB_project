import java.io.*;
import java.util.*;

public class Main {
	public static Database db = Database.getInstance();
	private enum UserOptions
	{
		SEARCH,
		SHOW_CANCEL,
		ADD,
		EXIT,
		INVALID
	}
	
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws FileNotFoundException, IOException  {
		// TODO Auto-generated method stub
		db.loadDatabase();
		
		User user = LoginAndSignup.loginORsignup();
		
		while (appFunctioning(user));
		
		db.saveDatabase();
	}
	
	private static UserOptions getUserInput(User user) throws IOException {
		Boolean isManager = false;
		if(user instanceof Manager) {
			isManager = true;
		}
		
		List<String> options = new ArrayList<>();
		options.add("Search Properties");
		options.add("Show/Cancel Your Bookings");
		if(isManager) {
			options.add("Add Property");
		}
		options.add("Exit");
		
		for(int i = 1; i <= options.size(); i++) {
			System.out.println(i + ". " + options.get(i - 1));
		}
		
		int check = Integer.parseInt(br.readLine());
		
		if (check < 0 || check > 4 || (!isManager && check == 4))
			return UserOptions.INVALID;
		
		if (check == 3 && !isManager)
			check++;
		
		if (check == 1)
			return UserOptions.SEARCH;
		else if (check == 2)
			return UserOptions.SHOW_CANCEL;
		else if (check == 3)
			return UserOptions.ADD;
		
		return UserOptions.EXIT;
	}
	
	public static Boolean appFunctioning(User user) throws IOException {
		UserOptions check = getUserInput(user);
		
		if(check == UserOptions.SEARCH) {
			searching(user);
		}
		else if(check == UserOptions.SHOW_CANCEL) {
			user.showBookings();
			System.out.println("Booking you want to cancel (give id): ");
			int booking_id = Integer.parseInt(br.readLine());
			user.cancelBooking(booking_id);
		}
		else if(check == UserOptions.ADD) {
			user.addProperty(createProperty(user));
			System.out.println("Property Added");
		}
		else if (check == UserOptions.EXIT)
			return false;
		
		assert check == UserOptions.INVALID : "Not a valid option! Contact the programmer";
		return true;
	}
	
	public static void searching(User user) throws IOException {
		System.out.println("Enter date for searching (dd-mm-yyyy): ");
		String line = br.readLine();
		String date[] = line.split("-");
		Date search_date = new Date(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
		if(search_date.before(new Date())) {
			System.out.println("Not a Valid Date");
			searching(user);
			return;
		}
		List<Property> properties = user.searchProperties(search_date);
		System.out.println("Number of days you want to book for: ");
		int num_days = Integer.parseInt(br.readLine());
		System.out.println("Select the property ID you want to book");
		for(Property property: properties) {
			System.out.println("ID: " + property.getID() + " Name: " + property.name + " Location: " + property.address.toString());
		}
		
		int id = Integer.parseInt(br.readLine());
		
		user.bookProperty(db.properties.get(id), search_date, num_days);
	}
	
	public static Property createProperty(User user) throws IOException {
		System.out.println("Property Name: ");
		String name = br.readLine();
		System.out.println("Property Location (Format: country,state,city,locality,street,house number): ");
		String location = br.readLine();
		System.out.println("Price per night: ");
		int price = Integer.parseInt(br.readLine());
		System.out.println("Number of Rooms available: ");
		int rooms = Integer.parseInt(br.readLine());
		return new Property(name, new Location(location), price, new Calendar(), user, rooms);
		
	}
}
