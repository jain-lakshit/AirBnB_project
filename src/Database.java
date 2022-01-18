import java.util.*;
import java.io.*;

public class Database {
	
	private static Database Instance;
	
	private Database() {
		properties = new HashMap<>();
		customers = new HashMap<>();
		bookings = new HashMap<>();
		managers = new HashMap<>();
	}
	
	public static Database getInstance() {
		if (Instance == null)
			 Instance = new Database();
		return Instance;
	}
	
	public Map<Integer, Customer> customers;
	public Map<Integer, Manager> managers;
	public Map<Integer, Property> properties;
	public Map<Integer, Booking> bookings;
	
	public int total_users = 0;
	public int total_properties = 0;
	public int total_bookings = 0;
	
	private File customers_file = new File("./customers.txt");
	private File managers_file = new File("./managers.txt");
	private File properties_file = new File("./properties.txt");
	private File booking_file = new File("./bookings.txt");
	private File cutomer_bookings_file = new File("./customer_bookings.txt");
	
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
	
	public void loadDatabase () throws FileNotFoundException, IOException, ClassNotFoundException {
		loadCustomers();
		
		loadManagers();
		
		loadProperties();
		
		loadBookings();
		
		loadUserBookings();
	}
	
	public void saveDatabase() throws FileNotFoundException, IOException {
		writeToFile(customers_file, customers);
		
		writeToFile(managers_file, managers);
		
		writeToFile(properties_file, properties);
		
		writeToFile(booking_file, bookings);

		writeUserBookingToFile();
	}
	
	private void writeUserBookingToFile() throws FileNotFoundException, IOException {
		Map<Integer, ArrayList<Integer>> customer_bookings = new HashMap<>();
		for(int id: customers.keySet()) {
			ArrayList<Integer> bookings = new ArrayList<>();
			for(Booking booking: customers.get(id).all_bookings){
				bookings.add(booking.getID());
			}
			
			customer_bookings.put(id, bookings);
		}
		FileOutputStream outStream = new FileOutputStream(cutomer_bookings_file);
		ObjectOutputStream objStream = new ObjectOutputStream(outStream);
		objStream.writeObject(customer_bookings);
		objStream.close();
		outStream.close();
	}
	
	private <T> void writeToFile(File file, Map<Integer, T> map) throws FileNotFoundException, IOException {
		PrintWriter writer = new PrintWriter(new FileWriter(file));
		for(int id: map.keySet()) {
			writer.println(map.get(id).toString());
		}
		writer.close();
	}
	
	private void loadProperties() throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(properties_file));
		total_properties = 0;
		String line;
		while((line = br.readLine()) != null) {
			Property property = new Property(line);
			addProperties(property, property.getID());
			total_properties++;
		}
		br.close();
	}
	
	private void loadBookings() throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(booking_file));
		total_bookings = 0;
		String line  = br.readLine();
		while((line) != null) {
			Booking b = Booking.createObj(line);
			
			if (b == null)
				continue;
			
			addBooking(b, b.getID());
			total_bookings++;
			line =  br.readLine();
		}
		br.close();
	}
	
	private void loadCustomers() throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(customers_file));
		total_users = 0;
		String line;
		while((line = br.readLine()) != null) {
			Customer customer = new Customer(line);
			addCustomer(customer, customer.getID());
			total_users++;
		}
		br.close();
	}
	
	private void loadManagers() throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(managers_file));
		String line;
		while((line = br.readLine()) != null) {
			Manager manager = new Manager(line);
			addManager(manager, manager.getID());
			total_users++;
		}
		br.close();
	}
	
	private void loadUserBookings() throws FileNotFoundException, IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream(cutomer_bookings_file);
		if (fis.available() == 0) {
			fis.close();
			return;
		}
		ObjectInputStream ois = new ObjectInputStream(fis);
		HashMap<Integer, ArrayList<Integer>> customer_bookings = (HashMap<Integer, ArrayList<Integer>>) ois.readObject();
		ois.close();
		fis.close();
		
		for(int id: customer_bookings.keySet()) {
			User user = null;
			if(customers.containsKey(id))
				user = customers.get(id);
			else if(managers.containsKey(id))
				user = managers.get(id);
			else return;
			for(int booking_id: customer_bookings.get(id)) {
				user.databaseAddBooking(bookings.get(booking_id));
			}
		}
	}
}
