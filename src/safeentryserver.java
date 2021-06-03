import java.rmi.Naming;

public class SafeEntryServer {
	static int port = 1099;

	public SafeEntryServer() {
		try {
			SafeEntryServerIntf c = new SafeEntryImpl();
			Naming.bind("rmi://localhost/SafeEntryService", c);
		} catch (Exception e) {
			System.out.println("Server Error: " + e);
		}
	}

	public static void main(String args[]) {
		if (args.length == 1)
			port = Integer.parseInt(args[0]);
		new SafeEntryServer();
	}
}
