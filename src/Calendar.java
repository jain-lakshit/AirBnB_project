import java.util.*;

public class Calendar {
	public Set<Date> dates;
	
	public Calendar() {
		this.dates = new HashSet<Date>();
	}
	public Calendar(String dates) {
		this();
		String all[] = dates.split(" ");
		for(String s: all) {
			this.dates.add(new Date(Long.parseLong(s)));
		}
	}
	public void bookDate(Date date) {
		if(dates.contains(date) == false)
			dates.add(date);
	}
	public void cancelDate(Date date) {
		dates.remove(date);
	}
	
	public Boolean contains(Date date) {
		return dates.contains(date);
	}
	
	public String toString() {
		String s="";
		for(Date date: dates) {
			s += date.getTime() + " ";
		}
		s = s.trim();
		return s;
	}
}
