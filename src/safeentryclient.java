import java.net.MalformedURLException; //Import the MalformedURLException class so you can catch it
import java.rmi.Naming; //Import the rmi naming - so you can lookup remote object
import java.rmi.NotBoundException; //Import the NotBoundException class so you can catch it
import java.rmi.RemoteException; //Import the RemoteException class so you can catch it
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class SafeEntryClient extends java.rmi.server.UnicastRemoteObject implements SafeEntryClientIntf {
	protected SafeEntryClient() throws RemoteException {
		super();
	}

	static String username = "";
	static int role = 0;

	@Override
	public void callBack(String s) throws java.rmi.RemoteException {

		System.out.println("You've been to " + s
				+ " in the past 14 days which have been found to be infected. Please visit the nearest clinic to get checked.");
	}

	public void printCommands() {
		System.out.println("Available commands:\n" + "/checkin\n" + "/checkout\n" + "/groupcheckin\n"
				+ "/groupcheckout\n" + "/myhistory\n" + "/logout\n\n" + "What would you like to do ?");

	}

	public void printMOHCommands() {
		System.out
				.println("Available commands:\n" + "/checkin\n" + "/checkout\n" + "/groupcheckin\n" + "/groupcheckout\n"
						+ "/myhistory\n" + "/declarecase\n\n" + "/logout\n\n" + "What would you like to do ?");

	}

	public long getTimeStamp() {
		Date date = new Date();
		long timeMilli = date.getTime();
		return timeMilli;
	}

	public Boolean checkIn(SafeEntryServerIntf se, Scanner scanner) throws RemoteException {
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
				System.out.println("Checked In Successfully !!\n");
				checkedin = true;
			} else {
				System.out.println("Incorrect entry try again!");
			}

		}
		return checkedin;

	}

	public Boolean checkOut(SafeEntryServerIntf se, Scanner scanner) {
		try {
			ArrayList<Location> locationArrList = se.myhistory(username);
			if (locationArrList == null) {
				System.out.println("You've no active checked in places.\n");
				return true;
			} else if (locationArrList.size() == 0) {
				System.out.println("You've no active checked in places.\n");
				return true;
			} else {
				System.out.println("These are the list of checked in places.");
			for (int i = 0; i < locationArrList.size(); i++) {
				Location locationPOJO = locationArrList.get(i);
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
				String date = sdf.format(new Date(locationPOJO.getTimestamp()));
				System.out.println(i + ". " + locationPOJO.getLocation() + ", checked in on: " + date);

			}
			Boolean checkedout = false;
			while (!checkedout) {
				System.out.println("Enter the number that represents the location that you're checking out of.");
				int locationId = scanner.nextInt();
				if (locationArrList.size() > locationId) {
					checkedout = se.checkout(username, locationArrList.get(locationId).getLocation(), getTimeStamp());
					System.out.println("Checked Out Successfully !!\n");
					return true;
				} else {
					System.out.println("Incorrect entry try again!");
				}

			}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Boolean groupcheckin(SafeEntryServerIntf se, Scanner scanner) throws RemoteException {
		ArrayList<String> nricList = new ArrayList<String>();
		nricList.add(username);
		System.out.println("Enter all NRIC(excluding yours) separated by a comma(,)");
		System.out.println("When you're done press [enter]");
		String nricInput = scanner.nextLine();
		String[] nricArr = nricInput.replaceAll("\\s", "").split(",");
		List<String> list1 = new ArrayList<String>();
		list1.add(username);
		Collections.addAll(list1, nricArr);
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
				checkedin = se.groupcheckin(list1.toArray(new String[0]), locationlist[locationId], getTimeStamp());
				System.out.println("Checked In Successfully !!\n");
				return true;
			} else {
				System.out.println("Incorrect entry try again!");
			}

		}
		return true;
	}

	public Boolean groupcheckout(SafeEntryServerIntf se, Scanner scanner) {
		try {
			ArrayList<String> nricList = new ArrayList<String>();
			nricList.add(username);
			System.out.println("Enter all NRIC(excluding yours) separated by a comma(,)");
			System.out.println("When you're done press [enter]");
			String nricInput = scanner.nextLine();
			String[] nricArr = nricInput.replaceAll("\\s", "").split(",");
			List<String> list1 = new ArrayList<String>();
			list1.add(username);
			Collections.addAll(list1, nricArr);
			ArrayList<Location> locationArrList = se.grouphistory(nricArr);
			if (locationArrList == null) {
				System.out.println("You've no active checked in places.\n");
				return false;
			} else if (locationArrList.size() == 0) {
				System.out.println("You've no active checked in places.\n");
				return false;
			} else {
				System.out.println("You're currently checked in these places:");
				for (int i = 0; i < locationArrList.size(); i++) {
					Location locationPOJO = locationArrList.get(i);
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
					String date = sdf.format(new Date(locationPOJO.getTimestamp()));
					System.out.println(i + ". " + locationPOJO.getLocation() + ", checked in on: " + date);
				}
				Boolean checkedout = false;
				while (!checkedout) {
					System.out.println("Enter the number that represents the location that you're checking out of.");
					int locationId = scanner.nextInt();
					if (locationArrList.size() > locationId) {
						checkedout = se.groupcheckout(list1.toArray(new String[0]),
								locationArrList.get(locationId).getLocation(), getTimeStamp());
						System.out.println("Checked Out Successfully !!\n");
						return true;
					} else {
						System.out.println("Incorrect entry try again!");
						return false;
					}

				}
				return false;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean myhistory(SafeEntryServerIntf se, Scanner scanner) {
		try {
			ArrayList<Location> locationArrList = se.myhistory(username);
			if (locationArrList == null) {
				System.out.println("You've no active checked in places.");
			} else if (locationArrList.size() == 0) {
				System.out.println("You've no active checked in places.");
			} else {
				System.out.println("You're currently checked in these places:");
				for (int i = 0; i < locationArrList.size(); i++) {
					Location locationPOJO = locationArrList.get(i);
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
					String date = sdf.format(new Date(locationPOJO.getTimestamp()));
					System.out.println(i + ". " + locationPOJO.getLocation() + ", checked in on: " + date);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("");
		return true;

	}

	private boolean declarecase(SafeEntryServerIntf se, Scanner scanner) {
		try {
			System.out.println("These are the list of available locations.");
			String[] locationlist;

			locationlist = se.listlocations();

			for (int i = 0; i < locationlist.length; i++) {
				System.out.println(i + ". " + locationlist[i]);
			}
			Boolean checkedin = false;
			while (!checkedin) {
				System.out.println("Enter the number that represents the location");
				int locationId = Integer.parseInt(scanner.nextLine());
				if (locationlist.length > locationId && locationlist[locationId] != null) {
					System.out.println("Enter the date and time of the case [dd/MM/yyyy hh:mm]");
					String sDate = scanner.nextLine();
					Date date = new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(sDate);
					System.out.println(date);
					System.out.println(locationlist[locationId]);
					checkedin = se.declarecase(locationlist[locationId], date.getTime());

					System.out.println("Done !!\n");
				} else {
					System.out.println("Incorrect entry try again!");
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;

	}

	public void logout(SafeEntryServerIntf se) throws RemoteException {
		if (se.logout(username)) {
			System.out.println("Logged Out Successfully !");
			System.exit(0);
		}

	}

	@SuppressWarnings("unused")
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

		try {
			SafeEntryClient client = new SafeEntryClient();

			Scanner scanner = new Scanner(System.in); // Create a Scanner object
			System.out.println("Welcome to SafeEntry");

			System.out.println("\nEnter NRIC");
			String nric = scanner.nextLine(); // Read user nric

			System.out.println("\nEnter password");
			String password = scanner.nextLine(); // Read user password

			// Create the reference to the remote object through the remiregistry
			SafeEntryServerIntf se = (SafeEntryServerIntf) Naming.lookup("rmi://localhost/SafeEntryService");
			int response = se.login(client, nric, password);

			if (response == 1) {
				System.out.println("\nLogged In !\n");
				username = nric;
				client.printCommands();
				role = 1;

			} else if (response == 2) {
				System.out.println("\nLogged In !\n");
				username = nric;
				client.printMOHCommands();
				role = 2;
			} else {
				System.out.println("Incorrect username/password");
				System.exit(0);
			}

			while (!username.equals("")) {
				String command = scanner.nextLine();
				Boolean commandexecuted = false;
				switch (command) {
				case "/logout":
					client.logout(se);
					break;
				case "/checkin":
					commandexecuted = client.checkIn(se, scanner);
					break;
				case "/checkout":
					commandexecuted = client.checkOut(se, scanner);
					break;
				case "/groupcheckin":
					commandexecuted = client.groupcheckin(se, scanner);
					break;
				case "/groupcheckout":
					commandexecuted = client.groupcheckout(se, scanner);
					break;
				case "/myhistory":
					commandexecuted = client.myhistory(se, scanner);
					break;
				case "/declarecase":
					if (role != 2) {
						System.out.println("Unauthorized command");
					} else {
						commandexecuted = client.declarecase(se, scanner);
					}
					break;
				case "":
					break;
					
				}
				if (commandexecuted && command.length() > 0) {
					if (role == 1) {
						client.printCommands();

					} else if (role == 2) {
						client.printMOHCommands();
					}
				}
				else if(!commandexecuted && command.length() > 0)
					System.out.println("Command not recognised. Try again.");
				
				

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
