import java.util.*;

public class Customer extends User{
	
	
	public Customer(String name, String aadhar, String phone, String email, int balance, String password) {
		this.fullname = name;
		this.aadhar = aadhar;
		this.account_balance = balance;
		this.email_id = email;
		this.phone_num = phone;
		this.all_bookings = new ArrayList<Booking>();
		this.id = Database.total_users;
		Database.total_users += 1;
		this.password = password;
	}
	
	public void bookProperty(Property property, Date start_date, int num_days) {
		Booking booking = new Booking(property, num_days, start_date);
		if(booking.verifyTimings()) {
			all_bookings.add(booking);
			booking.book();
		}
		else
			System.out.println("Not available for these dates");
	}
	
	public void cancelBooking(Booking booking) {
		all_bookings.remove(booking);
		booking.cancel();
	}
	
	public void cancelBooking(int booking_id) {
		all_bookings.remove(Database.bookings.get(booking_id));
		Database.bookings.get(booking_id).cancel();
	}
	
	public List<Property> searchProperties(Date date) {
		Map<Integer, Property> properties = Database.properties;
		List<Property> output = new ArrayList<Property>();
		for(int id : properties.keySet()) {
			Property property = properties.get(id);
			if(property.isAvailable(date)) {
				output.add(property);
			}
		}
		return output;
	}
	
	public void databaseAddBooking(Booking booking) {
		all_bookings.add(booking);
	}
	
	public String toString() {
		String booking_ids="";
		for(Booking b: all_bookings) {
			booking_ids += b.getID() + " ";
		}
		booking_ids = booking_ids.trim();
		return id + "~" + fullname + "~" + aadhar + "~" + email_id + "~" + phone_num + "~" + account_balance + "~" + password + "~" + booking_ids;
	}
	
	public void addProperty(Property property) {
		System.out.println("Cannot add, not a Manager");
	}
	
	public void showBookings() {
		for(Booking b: all_bookings) {
			System.out.println("Booking ID: " + b.getID() + " Property Booked: " + b.property.name +" \nDates Booked: " + b.start_date.toLocaleString() + " to " + new Date(b.start_date.getTime() + b.num_days*24*60*60).toLocaleString());
		}
	}
}
