import java.util.*;
import java.io.*;

public class Database {
	
	private static Database Instance = new Database();	
	
	private Database() {
		
	}
	
	public static Database getInstance() {
		return Instance;
	}
	
	public Map<Integer, Customer> customers;
	public Map<Integer, Manager> managers;
	public Map<Integer, Property> properties;
	public Map<Integer, Booking> bookings;
	
	public int total_users = 0;
	public int total_properties = 0;
	public int total_bookings = 0;
	
	public File customers_file = new File("./customers.txt");
	public File managers_file = new File("./managers.txt");
	public File properties_file = new File("./properties.txt");
	public File booking_file = new File("./bookings.txt");
	
	public void addCustomer(Customer customer, int id) {
		customers.put(id, customer);
	}
	public void addManager(Manager manager, int id) {
		managers.put(id, manager);
	}
	public void addProperties(Property property, int id) {
		properties.put(id, property);
	}
	public void addBooking(Booking booking, int id) {
		bookings.put(id, booking);
	}
	
	public void loadDatabase () throws FileNotFoundException, IOException {
		
		properties = new HashMap<>();
		BufferedReader br = new BufferedReader(new FileReader(properties_file));
		total_properties = Integer.parseInt(br.readLine());
		String line = br.readLine();
		while(line != null) {
			String[] details = line.split("~");
			int id = Integer.parseInt(details[0]);
			String name = details[1];
			String location = details[2];
			Location address = new Location(location);
			int price = Integer.parseInt(details[3]);
			int num_rooms = Integer.parseInt(details[4]);
			int manager_id = Integer.parseInt(details[5]);
			Calendar dates = new Calendar(details[6]);
			Manager manager = managers.get(manager_id);
			
			addProperties(new Property(name, address, price, dates, manager, num_rooms), id);
			line = br.readLine();
		}
		br.close();
		
		bookings = new HashMap<>();
		br = new BufferedReader(new FileReader(booking_file));
		total_bookings = Integer.parseInt(br.readLine());
		line = br.readLine();
		while(line != null) {
			String[] details = line.split("~");
			int id = Integer.parseInt(details[0]);
			int num_days = Integer.parseInt(details[1]);
			long date = Long.parseLong(details[2]);
			Date start_date = new Date(date);
			int property_id = Integer.parseInt(details[3]);
			Property property = properties.get(property_id);
			
			addBooking(new Booking(property, num_days, start_date), id);
		}
		br.close();
		
		customers = new HashMap<>();
		br = new BufferedReader(new FileReader(customers_file));
		total_users = Integer.parseInt(br.readLine());
		line = br.readLine();
		while(line != null) {
			String[] details = line.split("~");
			int id = Integer.parseInt(details[0]);
			String name = details[1];
			String aadhar = details[2];
			String email = details[3];
			String phone_num = details[4];
			int balance = Integer.parseInt(details[5]);
			String password = details[6];
			Customer customer = new Customer(name, aadhar, phone_num, email, balance, password);
			addCustomer(customer, id);
			if(details.length != 7) {
				String all[] = details[7].split(" ");
				for(String s : all) {
					int booking_id = Integer.parseInt(s);
					customer.databaseAddBooking(bookings.get(booking_id));
				}
			}
			line = br.readLine();
		}
		br.close();
		
		managers = new HashMap<>();
		br = new BufferedReader(new FileReader(managers_file));
		total_users = Integer.parseInt(br.readLine());
		line = br.readLine();
		while(line != null) {
			String[] details = line.split("~");
			int id = Integer.parseInt(details[0]);
			String name = details[1];
			String aadhar = details[2];
			String email = details[3];
			String phone_num = details[4];
			int balance = Integer.parseInt(details[5]);
			String password = details[6];
			Manager manager = new Manager(name, aadhar, phone_num, email, balance, password);
			addManager(manager, id);
			if(details.length != 7) {
				String all[] = details[7].split(" ");
				for(String s : all) {
					int booking_id = Integer.parseInt(s);
					manager.databaseAddBooking(bookings.get(booking_id));
				}
			}
			System.out.println(name + " " + password);
			line = br.readLine();
		}
		br.close();
		
	}
	
	public void saveDatabase() throws FileNotFoundException, IOException {
		PrintWriter writer = new PrintWriter(new FileWriter(customers_file));
		writer.println(total_users);
		for(int id: customers.keySet()) {
			writer.println(customers.get(id).toString());
		}
		writer.close();
		
		writer = new PrintWriter(new FileWriter(managers_file));
		writer.println(total_users);
		for(int id: managers.keySet()) {
			writer.println(managers.get(id).toString());
		}
		writer.close();
		
		writer = new PrintWriter(new FileWriter(properties_file));
		writer.println(total_properties);
		for(int id: properties.keySet()) {
			writer.println(properties.get(id).toString());
		}
		writer.close();
		
		writer = new PrintWriter(new FileWriter(booking_file));
		writer.println(total_bookings);
		for(int id: bookings.keySet()) {
			writer.println(bookings.get(id).toString());
		}
		writer.close();
	}
}
