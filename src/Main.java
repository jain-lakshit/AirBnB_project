import java.io.*;
import java.util.*;

public class Main {
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws FileNotFoundException, IOException  {
		// TODO Auto-generated method stub
		Database.loadDatabase();
		
		User user = LoginAndSignup.loginORsignup();
		
		appFunctioning(user);
		
		Database.saveDatabase();
	}
	
	public static void appFunctioning(User user) throws IOException {
		Boolean isManager = false;
		if(user instanceof Manager) {
			isManager = true;
		}
		
		System.out.println("\n1. Search Properties \n2. Show/Cancel Your Bookings");
		if(isManager) {
			System.out.println("3. Add Property");
		}
		int check = Integer.parseInt(br.readLine());
		if(check == 1) {
			searching(user);
		}
		else if(check == 2) {
			user.showBookings();
			System.out.println("Booking you want to cancel (give id): ");
			int booking_id = Integer.parseInt(br.readLine());
			user.cancelBooking(booking_id);
		}
		else if(check == 3) {
			adding(user);
		}
		appFunctioning(user);
	}
	
	public static void searching(User user) throws IOException {
		System.out.println("Enter date for searching (dd-mm-yyyy): ");
		String line = br.readLine();
		String date[] = line.split("-");
		Date search_date = new Date(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
		if(search_date.before(new Date())) {
			System.out.println("Not a Valid Date");
		}
		List<Property> properties = user.searchProperties(search_date);
		System.out.println("Number of days you want to book for: ");
		int num_days = Integer.parseInt(br.readLine());
		System.out.println("Select the property ID you want to book");
		for(Property property: properties) {
			System.out.println("ID: " + property.getID() + " Name: " + property.name + " Location: " + property.address.toString());
		}
		
		int id = Integer.parseInt(br.readLine());
		
		user.bookProperty(Database.properties.get(id), search_date, num_days);
	}
	
	public static void adding(User user) throws IOException {
		System.out.println("Property Name: ");
		String name = br.readLine();
		System.out.println("Property Location (Format: country,state,city,locality,street,house number): ");
		String location = br.readLine();
		System.out.println("Price per night: ");
		int price = Integer.parseInt(br.readLine());
		System.out.println("Number of Rooms available: ");
		int rooms = Integer.parseInt(br.readLine());
		Property property = new Property(name, new Location(location), price, new Calendar(), user, rooms);
		user.addProperty(property);
		System.out.println("Property Added");
	}
}
