import java.util.Date;
import java.util.List;

public abstract class User {
	protected String fullname;
	protected String aadhar;
	protected String phone_num;
	protected String email_id;
	protected int account_balance;
	protected int id;
	protected String password;
	public List<Booking> all_bookings;
	public static Database db = Database.getInstance();

	// getters
	public String getName() {
		return fullname;
	}
	public String getAadhar() {
		return aadhar;
	}
	public String getPhoneNum() {
		return phone_num;
	}
	public String getEmailID() {
		return email_id;
	}
	public int getBalance() {
		return account_balance;
	}
	public int getID() {
		return id;
	}
	public String getPassword() {
		return password;
	}
	
	abstract public void bookProperty(Property property, Date start_date, int num_days);
	abstract public void cancelBooking(Booking booking);
	abstract public void cancelBooking(int booking_id);
	abstract public List<Property> searchProperties(Date date);
	abstract public void databaseAddBooking(Booking booking);
	abstract public void addProperty(Property property);
	abstract public Boolean showBookings();
	abstract public Boolean deductBalance(int deduct);
	abstract public String getDetails();
}
