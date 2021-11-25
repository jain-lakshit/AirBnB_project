import java.util.*;

public class Booking {
	private int property_id;
	private int num_days;
	private Date start_date;
	private Property property;
	private int booking_id;
	private static Database db = Database.getInstance();

	public static Booking createObj(String line)
	{
		Booking b = new Booking();

		String[] details = line.split("~");
		b.booking_id = Integer.parseInt(details[0]);
		b.num_days = Integer.parseInt(details[1]);
		b.start_date = new Date(Long.parseLong(details[2]));

		if (!b.start_date.after(new Date()))
			return null;

		b.property_id = Integer.parseInt(details[3]);
		b.property = db.properties.get(b.property_id);
		
		return b;
	}
	
	private Booking() {
		
	}
	
	public Booking(Property property, int num_days, Date start_date) {
		this.num_days = num_days;
		this.start_date = start_date;
		this.property_id = property.getID();
		this.property = property;
		booking_id = db.total_bookings;
		db.total_bookings += 1;
	}
	
	public int getTotalPrice() {
		return property.getPrice_per_night() * num_days;
	}

	public int getID() {return booking_id;}
	public int getNum_days() {return num_days;}
	public int getProperty_id() {return property_id;}
	public Date getStart_date() {return start_date;}
	public Property getProperty() {return property;}

	
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
	
	public int getRefund() {
		return property.refund(getTotalPrice());
	}
}
