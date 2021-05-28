import java.rmi.Naming; //Import the rmi naming - so you can lookup remote object
import java.rmi.RemoteException; //Import the RemoteException class so you can catch it
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.net.MalformedURLException; //Import the MalformedURLException class so you can catch it
import java.rmi.NotBoundException; //Import the NotBoundException class so you can catch it

public class safeentryclient {
	static String username = "";

	public void printCommands() {
		System.out.println("Available commands:\n" + "/checkin\n" + "/checkout\n" + "/groupcheckin\n"
				+ "/groupcheckout\n" + "/myhistory\n" + "/logout\n\n" + "What would you like to do ?");

	}

	public long getTimeStamp() {
		Date date = new Date();
		long timeMilli = date.getTime();
		return timeMilli;
	}

	public Boolean checkIn(safeentry se, Scanner scanner) throws RemoteException {
		System.out.println("These are the list of available locations.");
		String[] locationlist = se.listlocations();

		for (int i = 0; i < locationlist.length; i++) {
			System.out.println(i + ". " + locationlist[i]);
		}
		Boolean checkedin = false;
		while (!checkedin) {
			System.out.println("Enter the number that represents the location that you're checking into.");
			int locationId = scanner.nextInt();
			if (locationlist.length > locationId && locationlist[locationId] != null) {
				checkedin = se.checkin(username, locationlist[locationId], getTimeStamp());
				System.out.println("Checked In Successfully !!");
				return true;
			} else {
				System.out.println("Incorrect entry try again!");
			}

		}
		return false;

	}

	public Boolean checkOut(safeentry se, Scanner scanner) {
		System.out.println("These are the list of checked in places.");
		try {
			ArrayList<locationPOJO> locationArrList = se.myhistory(username);
			for (int i = 0; i < locationArrList.size(); i++) {
				locationPOJO locationPOJO = locationArrList.get(i);
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
				String date = sdf.format(new Date(locationPOJO.getTimestamp()));
				System.out.println(i + ". " + locationPOJO.getLocation() + ", checked in on: " + date);

			}
			Boolean checkedout = false;
			while (!checkedout) {
				System.out.println("Enter the number that represents the location that you're checking out of.");
				int locationId = scanner.nextInt();
				if (locationArrList.size() > locationId) {
					checkedout = se.checkout(username, locationId, getTimeStamp());
					System.out.println("Checked Out Successfully !!");
					return true;
				} else {
					System.out.println("Incorrect entry try again!");
				}

			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	

	public Boolean groupcheckin(safeentry se, Scanner scanner) throws RemoteException {
		System.out.println("These are the list of available locations.");
		String[] locationlist = se.listlocations();
		
		System.out.println("Enter number of total check-ins.");
		int grpSize = scanner.nextInt();
		String[] nricList = new String[grpSize];
		
		for (int i = 0; i < grpSize; i++) {
			System.out.println("Enter NRIC of user"+i+1);
			nricList[i] = scanner.next();
		}
		
		for (int i = 0; i < locationlist.length; i++) {
			System.out.println(i + ". " + locationlist[i]);
		}
		
		Boolean checkedin = false;
		while (!checkedin) {
			System.out.println("Enter the number that represents the location that you're checking into.");
			int locationId = scanner.nextInt();
			if (locationlist.length > locationId && locationlist[locationId] != null) {
				checkedin = se.checkin(username, locationlist[locationId], getTimeStamp());
				System.out.println("Checked In Successfully !!");
				return true;
			} else {
				System.out.println("Incorrect entry try again!");
			}

		}
		return false;
	}

	public void groupcheckout() {

	}

	public void myhistory(safeentry se, Scanner scanner) {
		try {
			ArrayList<locationPOJO> locationArrList = se.myhistory(username);
			if(locationArrList.size() == 0) {
				System.out.println("You've no active checked in places.");
			}
			else {
				System.out.println("You're currently checked in these places:");
				for (int i = 0; i < locationArrList.size(); i++) {
					locationPOJO locationPOJO = locationArrList.get(i);
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
					String date = sdf.format(new Date(locationPOJO.getTimestamp()));
					System.out.println(i + ". " + locationPOJO.getLocation() + ", checked in on: " + date);
				}
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}

	public void logout() {
		System.out.println("Logged Out Successfully !");
		System.exit(0);
	}

	public static void main(String[] args) {

		// use localhost if running the server locally or use IP address of the server
		String reg_host = "localhost";
		int reg_port = 1099;

		if (args.length == 1) {
			reg_port = Integer.parseInt(args[0]);
		} else if (args.length == 2) {
			reg_host = args[0];
			reg_port = Integer.parseInt(args[1]);
		}

		safeentryclient client = new safeentryclient();

		Scanner scanner = new Scanner(System.in); // Create a Scanner object
		System.out.println("Welcome to SafeEntry");

		System.out.println("\nEnter NRIC");
		String nric = scanner.nextLine(); // Read user nric

		System.out.println("\nEnter password");
		String password = scanner.nextLine(); // Read user password

		try {

			// Create the reference to the remote object through the remiregistry
			safeentry se = (safeentry) Naming.lookup("rmi://localhost/SafeEntryService");
			Boolean response = se.login(nric, password);

			if (response) {
				System.out.println("\nLogged In !\n");
				username = nric;
			} else {
				System.out.println("Incorrect username/password");
				System.exit(0);
			}

			client.printCommands();

			while (!username.equals("")) {
				String command = scanner.nextLine();
				Boolean commandexecuted = false;
				switch (command) {
				case "/logout":
					client.logout();
					break;
				case "/checkin":
					commandexecuted = client.checkIn(se, scanner);
					break;
				case "/checkout":
					commandexecuted = client.checkOut(se, scanner);
					break;
				case "/groupcheckin":
					System.out.println("Group Checkin selected");
					client.groupcheckin(se, scanner);
					break;
				case "/groupcheckout":
					client.groupcheckout();
					break;
				case "/myhistory":
					client.myhistory(se, scanner);
					break;
				default:
					if (commandexecuted)
						System.out.println("What else would you like to do ?");
					else
						System.out.println("Command not recognised. Try again.");
					client.printCommands();
				}

			}

		}
		// Catch the exceptions that may occur - rubbish URL, Remote exception
		// Not bound exception or the arithmetic exception that may occur in
		// one of the methods creates an arithmetic error (e.g. divide by zero)
		catch (MalformedURLException murle) {
			System.out.println();
			System.out.println("MalformedURLException");
			System.out.println(murle);
		} catch (RemoteException re) {
			System.out.println();
			System.out.println("RemoteException");
			System.out.println(re);
		} catch (NotBoundException nbe) {
			System.out.println();
			System.out.println("NotBoundException");
			System.out.println(nbe);
		} catch (java.lang.ArithmeticException ae) {
			System.out.println();
			System.out.println("java.lang.ArithmeticException");
			System.out.println(ae);
		}
	}
}
