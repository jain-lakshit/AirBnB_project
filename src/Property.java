import java.util.*;

public class Property {
	public String name;
	public Location address;
	public int price_per_night;
	public Calendar dates_booked;
	public User manager;
	public int num_rooms;
	public int id;
	public int manager_id;
	private static Database db = Database.getInstance();

	public Property(String line) {
		String[] details = line.split("~");
		id = Integer.parseInt(details[0]);
		name = details[1];
		address = new Location(details[2]);
		price_per_night = Integer.parseInt(details[3]);
		num_rooms = Integer.parseInt(details[4]);
		manager_id = Integer.parseInt(details[5]);
		dates_booked = new Calendar();
		if (details.length == 7)
			dates_booked = new Calendar(details[6]);
		manager = db.managers.get(manager_id);
	}
	
	public Property(String name, Location addr, int price, Calendar dates, User manager, int num_rooms) {
		this.name = name;
		this.address = addr;
		this.price_per_night = price;
		this.dates_booked = dates;
		this.manager = manager;
		this.num_rooms = num_rooms;
		id = db.total_properties;
		db.total_properties += 1;
		manager_id = manager.getID();
	}
	
	public Boolean isAvailable(Date date) {
		return !dates_booked.contains(date);
	}
	
	public int getID() {
		return id;
	}
	
	public String toString() {
		return id + "~" + name + "~" + address.toString() + "~" + price_per_night + "~" + num_rooms + "~" + manager_id + "~" + dates_booked.toString();
	}
	
	public void addBalance(int price) {
		manager.account_balance += price;
	}
	
	public int refund(int original) {
		manager.account_balance -= original/2;
		return original - (original/2);
	}
}
