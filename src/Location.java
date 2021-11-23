
public class Location {
	public String country;
	public String state;
	public String city;
	public String locality;
	public String street;
	public String house;
	
	public Location(String country, String state, String city, String locality, String street, String house) {
		this.country = country;
		this.state = state;
		this.city = city;
		this.locality = locality;
		this.street = street;
		this.house = house;
	}
	
	public Location(String details) {
		String all[] = details.split(",");
		this.country = all[0];
		this.state = all[1];
		this.city = all[2];
		this.locality = all[3];
		this.street = all[4];
		this.house = all[5];
	}
	
	public String toString() {
		return country + "," + state + "," + city + "," + locality + "," + street + "," + house;
	}
}
