import java.util.*;

public class Booking {
	int property_id;
	public int num_days;
	public Date start_date;
	Property property;
	int booking_id;
	public static Database db = Database.getInstance();

	public Booking(Property property, int num_days, Date start_date) {
		this.num_days = num_days;
		this.start_date = start_date;
		this.property_id = property.getID();
		this.property = property;
		booking_id = db.total_bookings;
		db.total_bookings += 1;
	}
	
	public int getTotalPrice() {
		return property.price_per_night * num_days;
	}
	
	public int getID() {
		return booking_id;
	}
	
	public Boolean verifyTimings() {
		Date curr = new Date();
		long curr_day = curr.getTime();
		long next_day = curr_day;
		long day = 24*60*60*1000;
		for(int i=0; i<num_days; i++) {
			Date next_date = new Date(next_day);
			if(property.isAvailable(next_date) == false)
				return false;
			next_day += day;
		}
		
		return true;
	}
	
	public void book() {
		Date curr = new Date();
		long curr_day = curr.getTime();
		long next_day = curr_day;
		long day = 24*60*60*1000;
		for(int i=0; i<num_days; i++) {
			Date next_date = new Date(next_day);
			property.dates_booked.bookDate(next_date);
			next_day += day;
		}
	}
	public void cancel() {
		if(start_date.before(new Date()))
			System.out.println("Cannot Cancel");
		else {
			Date curr = new Date();
			long curr_day = curr.getTime();
			long next_day = curr_day;
			long day = 24*60*60*1000;
			for(int i=0; i<num_days; i++) {
				Date next_date = new Date(next_day);
				property.dates_booked.cancelDate(next_date);
				next_day += day;
			}
		}
	}
	
	public String toString() {
		return booking_id + "~" + num_days + "~" + start_date.getTime() + "~" + property_id;
	}
}
