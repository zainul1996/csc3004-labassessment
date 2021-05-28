import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.Naming; //Import naming classes to bind to rmiregistry

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class safeentryserver {
	static int port = 1099;

	public safeentryserver() {

		try {
			safeentry c = new safeentryimpl();
			Naming.bind("rmi://localhost/SafeEntryService", c);
		} catch (Exception e) {
			System.out.println("Server Error: " + e);
		}
	}

	public static void main(String args[]) {
		if (args.length == 1)
			port = Integer.parseInt(args[0]);
		new safeentryserver();

	}
}
