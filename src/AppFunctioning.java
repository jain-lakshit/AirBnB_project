import java.io.*;
import java.util.*;

public class AppFunctioning {
	private static AppFunctioning Instance = new AppFunctioning();	
	
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static Database db = Database.getInstance();

	private enum UserOptions
	{
		SEARCH,
		SHOW_CANCEL,
		ADD,
		MY_DETAILS,
		EXIT,
		INVALID
	}
	
	private Map<Integer, UserOptions> user_options;
	
	private AppFunctioning() {
		user_options = new HashMap<>();
		user_options.put(1, UserOptions.SEARCH);
		user_options.put(2, UserOptions.SHOW_CANCEL);
		user_options.put(3, UserOptions.ADD);
		user_options.put(4, UserOptions.MY_DETAILS);
		user_options.put(5, UserOptions.EXIT);
	}
	
	public static AppFunctioning getInstance() {
		return Instance;
	}
	

	private UserOptions getUserInput(User user) throws IOException {
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
		options.add("My Details");
		options.add("Logout");
		System.out.println("");
		for(int i = 1; i <= options.size(); i++) {
			System.out.println(i + ". " + options.get(i - 1));
		}
		
		int check = Integer.parseInt(br.readLine());
		
		if (check < 0 || check > 5 || (!isManager && check == 5))
			return UserOptions.INVALID;
		
		if ((check == 3 || check == 4) && !isManager)
			check++;
		
		return user_options.get(check);
	}
	
	public Boolean appFunctioning(User user) throws IOException {
		UserOptions check = getUserInput(user);
		
		if(check == UserOptions.SEARCH) {
			searching(user);
		}
		else if(check == UserOptions.SHOW_CANCEL) {
			if(!user.showBookings()) {
				System.out.println("No Bookings to show.\n");
				return true;
			}
			
			System.out.println("Booking you want to cancel (give id) or -1 to return: ");
			int booking_id = Integer.parseInt(br.readLine());
			if(booking_id == -1)
				return true;
			user.cancelBooking(db.bookings.get(booking_id));
			System.out.println("Booking Canceled\n");
		}
		else if(check == UserOptions.ADD) {
			user.addProperty(createProperty(user));
			System.out.println("Property Added\n");
		}
		else if (check == UserOptions.MY_DETAILS) {
			System.out.println(user.getDetails());
		}
		else if (check == UserOptions.EXIT)
			return false;
		else if (check == UserOptions.INVALID) 
			System.out.println("Not a valid option!\n");
		
		return true;
	}
	
	private void searching(User user) throws IOException {
		Date search_date = createStartDate();
		if(!search_date.after(new Date())) {
			System.out.println("Not a Valid Date\n");
			searching(user);
			return;
		}
		// TODO: 	MOVE TO DATABASE
		List<Property> properties = user.searchProperties(search_date);
		if(properties.size() != 0) {
			System.out.println("Number of days you want to book for: ");
			int num_days = Integer.parseInt(br.readLine());
			
			System.out.println("Select the property ID you want to book or -1 to return:");
			for(Property property: properties) {
				System.out.println("ID: " + property.getID() + " | Name: " + property.getName() + " | Location: " + property.getAddress().toString() + " | Price per Night: " + property.getPrice_per_night());
			}
			int id = Integer.parseInt(br.readLine());
			if(id == -1)
				return;

			// TODO: Error handling for out of bound id
			user.bookProperty(db.properties.get(id), search_date, num_days);
		}
		else {
			System.out.println("No properties Available\n");
		}
	}
	
	private Date createStartDate() throws IOException {
		System.out.println("Enter date for searching (dd-mm-yyyy): ");
		String line = br.readLine();
		String date[] = line.split("-");
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.set(Integer.parseInt(date[2]), Integer.parseInt(date[1])-1, Integer.parseInt(date[0]), 0, 0, 0);
		return cal.getTime();
	}
	
	private Property createProperty(User user) throws IOException {
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
