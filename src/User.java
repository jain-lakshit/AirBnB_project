import java.util.ArrayList;
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
	protected List<Booking> all_bookings;
	protected static Database db = Database.getInstance();

	public User(String[] details) {
		this(details[1], details[2], details[4], details[3], Integer.parseInt(details[5]), details[6]);
		id = Integer.parseInt(details[0]);
	}

	public User(String name, String aadhar, String phone, String email, int balance, String password) {
		this.fullname = name;
		this.aadhar = aadhar;
		this.account_balance = balance;
		this.email_id = email;
		this.phone_num = phone;
		this.all_bookings = new ArrayList<Booking>();
		this.id = db.total_users;
		db.total_users += 1;
		this.password = password;
	}

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
	public List<Booking> getAll_bookings(){return all_bookings;}

	public void setBalance(int balance){account_balance = balance;}

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
