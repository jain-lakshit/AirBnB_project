import java.io.*;

public class Main {
	private static Database db = Database.getInstance();
	private static LoginAndSignup auth = LoginAndSignup.getInstance();
	private static AppFunctioning app = AppFunctioning.getInstance();

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		db.loadDatabase();
		
		User user = auth.loginORsignup();
		
		while (app.appFunctioning(user));
		
		db.saveDatabase();
	}
}
