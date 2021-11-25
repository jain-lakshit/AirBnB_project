import java.util.*;
import java.text.*;

public class Customer extends User{

	public Customer(String line) {
		super(line.split("~"));
	}
	
	public Customer(String name, String aadhar, String phone, String email, int balance, String password) {
		super(name, aadhar, phone, email, balance, password);
	}
	
	
	public void bookProperty(Property property, Date start_date, int num_days) {
		Booking booking = new Booking(property, num_days, start_date);
		if(!booking.verifyTimings()) {
			System.out.println("Not available for these dates");
		}
		else if(!deductBalance(booking.getTotalPrice())){
			System.out.println("Not enough Balance");
		}
		else {
			property.getManager().setBalance(property.getManager().getBalance() + booking.getTotalPrice());
			db.bookings.put(booking.getID(), booking);
			all_bookings.add(booking);
			booking.book();
			System.out.println("Booking Confirmed");
		}
			
	}
	
	public void cancelBooking(Booking booking) {
		db.bookings.remove(booking.getID());
		all_bookings.remove(booking);
		account_balance += booking.getRefund();
		booking.cancel();
	}
	
	public void cancelBooking(int booking_id) {
		all_bookings.remove(db.bookings.get(booking_id));
		db.bookings.get(booking_id).cancel();
	}
	
	public List<Property> searchProperties(Date date) {
		Map<Integer, Property> properties = db.properties;
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
	
	public String getDetails() {
		return "Name: " + fullname + "\nAadhar: " + aadhar + "\nEmail ID: " + email_id + "\nPhone Number: " + phone_num + "\nCurrent Balance: Rs. " + account_balance;
	}
	
	public String toString() {
		return id + "~" + fullname + "~" + aadhar + "~" + email_id + "~" + phone_num + "~" + account_balance + "~" + password;
	}
	
	public String bookingIDs() {
		String booking_ids="";
		for(Booking b: all_bookings) {
			booking_ids += b.getID() + " ";
		}
		return booking_ids.trim();
	}
	
	public void addProperty(Property property) {
		System.out.println("Cannot add, not a Manager");
	}
	
	public Boolean showBookings() {
		if(all_bookings.size() == 0)
			return false;
		for(Booking b: all_bookings) {
			System.out.println("Booking ID: " + b.getID() + " Property Booked: " + b.getProperty().getName() +" \nDates Booked: " + DateFormat.getInstance().format(b.getStart_date()) + " to " + DateFormat.getInstance().format(new Date(b.getStart_date().getTime() + b.getNum_days()*24*60*60)));
		}
		return true;
	}
	
	public Boolean deductBalance(int deduct) {
		if(account_balance < deduct)
			return false;
		account_balance -= deduct;
		return true;
	}
}
