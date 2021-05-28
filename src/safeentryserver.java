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

	private String getuserip(String nric) {

		return null;
	}

	public static void writetojsonfile(String data) {
		// Write JSON file
		try (FileWriter file = new FileWriter("user.json")) {
			// We can write any JSONArray or JSONObject instance to the file
			file.write(data);
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static Object readfromjsonfile() {
		// JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();

		try (FileReader reader = new FileReader("user.json")) {
			// Read JSON file
			Object obj = jsonParser.parse(reader);

			return obj;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static void main(String args[]) {
		if (args.length == 1)
			port = Integer.parseInt(args[0]);
		new safeentryserver();

	}
}
