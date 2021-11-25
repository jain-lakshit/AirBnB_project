import java.util.*;

public class Calendar {
	private Set<Date> dates;

	public Set<Date> getDates() {return dates;}

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
		if(this.contains(date) == false)
			dates.add(date);
	}
	public void cancelDate(Date date) {
		dates.remove(date);
	}
	
	public Boolean contains(Date date) {
		for(Date d: dates){
			if(d.getDate() == date.getDate() && d.getMonth() == date.getMonth() && d.getYear() == date.getYear())
				return true;
		}
		return false;
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
