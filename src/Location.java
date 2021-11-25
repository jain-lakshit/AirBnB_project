
public class Location {
	private String country;
	private String state;
	private String city;
	private String locality;
	private String street;
	private String house;
	
	public Location(String country, String state, String city, String locality, String street, String house) {
		this.country = country;
		this.state = state;
		this.city = city;
		this.locality = locality;
		this.street = street;
		this.house = house;
	}
	
	public Location(String details) {
		this(details.split(","));
	}
	private Location(String []all){
		this(all[0], all[1], all[2], all[3], all[4], all[5]);
	}
	
	public String toString() {
		return country + "," + state + "," + city + "," + locality + "," + street + "," + house;
	}
}
