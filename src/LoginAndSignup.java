import java.io.*;

public class LoginAndSignup {
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static User loginORsignup() throws IOException{
		System.out.println("1. Login \n2. Sign Up");
		int check = Integer.parseInt(br.readLine());
		if(check == 1) {
			return login();
		}
		else if(check == 2) {
			return signUp();
		}
		else {
			System.out.println("Choose a valid option.");
			return loginORsignup();
		}
	}
	
	public static User login() throws IOException{
		System.out.println("\n1. Login as Customer \n2. Login as Manager");
		int check2 = Integer.parseInt(br.readLine());
		if(check2 != 2 && check2 != 1){
			System.out.println("Choose a valid option.");
			return login();
		}
		System.out.println("\nName: ");
		String name = br.readLine();
		System.out.println("Password: ");
		String password = br.readLine();
		if(check2 == 1) {
			for(int id: Database.customers.keySet()) {
				if(Database.customers.get(id).getName().equals(name) && Database.customers.get(id).getPassword().equals(password)) {
					return Database.customers.get(id);
				}
			}
		}
		else if(check2 == 2) {
			for(int id: Database.managers.keySet()) {
				if(Database.managers.get(id).getName().equals(name) && Database.managers.get(id).getPassword().equals(password)) {
					return Database.managers.get(id);
				}
			}
		}
		System.out.println("Credentials do not match. Try Again.");
		return login();
	}
	
	public static User signUp() throws IOException {
		System.out.println("\n1. Sign Up as Customer \n2. Sign Up as Manager");
		int check2 = Integer.parseInt(br.readLine());
		if(check2 != 2 && check2 != 1){
			System.out.println("Choose a valid option.");
			return signUp();
		}
		System.out.println("\nName: ");
		String name = br.readLine();
		System.out.println("Aadhar Number: ");
		String aadhar = br.readLine();
		System.out.println("Phone Number: ");
		String phone_num = br.readLine();
		System.out.println("Email ID: ");
		String email_id = br.readLine();
		System.out.println("Account Balance: ");
		int balance = Integer.parseInt(br.readLine());
		System.out.println("Password: ");
		String password = br.readLine();
		
		if(check2 == 1) {
			Customer customer = new Customer(name, aadhar, phone_num, email_id, balance, password);
			Database.addCustomer(customer, customer.getID());
			return customer;
		}
		else if(check2 == 2) {
			Manager manager = new Manager(name, aadhar, phone_num, email_id, balance, password);
			Database.addManager(manager, manager.getID());
			return manager;
		}
		return signUp();
	}
}
