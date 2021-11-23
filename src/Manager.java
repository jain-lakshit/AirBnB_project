
public class Manager extends Customer {
	public Manager(String name, String aadhar, String phone, String email, int balance, String password) {
		super(name, aadhar, phone, email, balance, password);
	}
	public void addProperty(Property property) {		
		Database.addProperties(property, property.getID());
	}
}
