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
	public Property(String name, Location addr, int price, Calendar dates, User manager, int num_rooms) {
		this.name = name;
		this.address = addr;
		this.price_per_night = price;
		this.dates_booked = dates;
		this.manager = manager;
		this.num_rooms = num_rooms;
		id = Database.total_properties;
		Database.total_properties += 1;
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
}
