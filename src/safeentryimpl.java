import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/*
	Code: CalculatorImpl remote object	calculatorimpl.java

	Contains the arithmetic methods that can be remotely invoked
*/

// The implementation Class must implement the rmi interface (calculator)
// and be set as a Remote object on a server
public class safeentryimpl extends java.rmi.server.UnicastRemoteObject implements safeentry {

	protected safeentryimpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized Boolean login(String nric, String password) throws RemoteException {
		JSONParser jsonParser = new JSONParser();

		try{
			// Read user.json
			FileReader reader = new FileReader("user.json");
			
			// Get the object that matches the nric
			Object obj = jsonParser.parse(reader);
			JSONObject usersObj = (JSONObject) obj;
			JSONObject userObj = (JSONObject) usersObj.get(nric);
			if (userObj.get("password").equals(password))
				return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized Boolean checkin(String nric, String location, long timestamp) throws RemoteException {
		// Create check in object
		JSONObject checkInObject = new JSONObject();
		checkInObject.put("location", location);
		checkInObject.put("intimestamp", timestamp);

		try {
			JSONParser jsonParser = new JSONParser();

			// FileReader for checkIn.json
			FileReader reader = new FileReader("checkIn.json");

			// Construct a JSONObject out of existing checkin
			Object obj = jsonParser.parse(reader);
			JSONObject checkInObj = (JSONObject) obj;

			// FileWriter for checkIn.json
			FileWriter file = new FileWriter("checkIn.json");

			// Get the list of user check in's and add the new check in to it
			JSONArray userCheckInList = (JSONArray) checkInObj.get(nric);
			userCheckInList.add(checkInObject);

			// Replace the old check in list with new check in list in object
			checkInObj.replace(nric, userCheckInList);

			// Write it into the file
			file.write(checkInObj.toJSONString());
			file.flush();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized Boolean checkout(String nric, int checkid, long outtimestamp) throws RemoteException {
		try {
			JSONParser jsonParser = new JSONParser();

			// FileReader for checkIn.json
			FileReader reader = new FileReader("checkIn.json");

			// Construct a JSONObject out of existing checkin
			Object obj = jsonParser.parse(reader);
			JSONObject checkInObj = (JSONObject) obj;

			// FileWriter for checkIn.json
			FileWriter file = new FileWriter("checkIn.json");

			// Get the list of user check in's and add the new check in to it
			JSONArray userCheckInList = (JSONArray) checkInObj.get(nric);
			JSONObject removedLocation = (JSONObject) userCheckInList.remove(checkid);

			// Replace the old check in list with new check in list in object
			checkInObj.replace(nric, userCheckInList);

			// Write it into the file
			file.write(checkInObj.toJSONString());
			file.flush();

			// FileReader for locationhistory.json
			FileReader locationHistoryReader = new FileReader("locationhistory.json");

			// Construct a JSONObject out of existing checkin
			Object locationHistoryObj = jsonParser.parse(locationHistoryReader);
			JSONObject locationHistoryJsonObj = (JSONObject) locationHistoryObj;

			// FileWriter for locationhistory.json
			FileWriter locationHistoryWriter = new FileWriter("locationhistory.json");

			
			// Get the list of checkin/checkout history
			if(locationHistoryJsonObj.containsKey(removedLocation.get("location"))) {
				JSONArray historyJsonObj = (JSONArray) locationHistoryJsonObj.get(removedLocation.get("location"));
				// Create the new checkin/checkout object and add into the history
				JSONObject checkInObject = new JSONObject();
				checkInObject.put("nric", nric);
				checkInObject.put("intimestamp", removedLocation.get("intimestamp"));
				checkInObject.put("outtimestamp", outtimestamp);
				historyJsonObj.add(checkInObject);
				
				// Replace the old checkin/checkout history with new checkin/checkout history
				locationHistoryJsonObj.replace(removedLocation.get("location"), historyJsonObj);
			}else {
				JSONArray historyJsonObj = new JSONArray();
				// Create the new checkin/checkout object and add into the history
				JSONObject checkInObject = new JSONObject();
				checkInObject.put("nric", nric);
				checkInObject.put("intimestamp", removedLocation.get("intimestamp"));
				checkInObject.put("outtimestamp", outtimestamp);
				historyJsonObj.add(checkInObject);
				
				// Replace the old checkin/checkout history with new checkin/checkout history
				locationHistoryJsonObj.put(removedLocation.get("location"), historyJsonObj);
			
			}
			
			// Write it into the file
			locationHistoryWriter.write(locationHistoryJsonObj.toJSONString());
			locationHistoryWriter.flush();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized Boolean groupcheckin(String[] nrics, String location, long timestamp) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized Boolean groupcheckout(String[] nrics, String location, long timestamp) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized String[][] locationhistory(int locationid) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized ArrayList<locationPOJO> myhistory(String nric) throws RemoteException {
		try {
			JSONParser jsonParser = new JSONParser();
			locationPOJO location;

			// FileReader for checkIn.json
			FileReader reader = new FileReader("checkIn.json");

			// Construct a JSONObject out of existing checkin
			Object obj = jsonParser.parse(reader);
			JSONObject checkInObj = (JSONObject) obj;

			// Get list of currently checkin places
			JSONArray userCheckInList = (JSONArray) checkInObj.get(nric);

			ArrayList<locationPOJO> locationArrList = new ArrayList<locationPOJO>();
			for (Object object : userCheckInList) {
				JSONObject locationObj = (JSONObject) object;
				System.out.println(locationObj.get("location") + " : " + locationObj.get("intimestamp"));
				location = new locationPOJO();
				location.setTimestamp((Long) locationObj.get("intimestamp"));
				location.setLocation((String) locationObj.get("location"));
				locationArrList.add(location);
			}

			return locationArrList;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized String[] listlocations() throws RemoteException {
		JSONParser jsonParser = new JSONParser();

		try (FileReader reader = new FileReader("location.json")) {
			// Read JSON file
			Object obj = jsonParser.parse(reader);
			JSONArray locationArr = (JSONArray) obj;
			return (String[]) locationArr.stream().toArray(String[]::new);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized Boolean declarecase(String nric, int locationid, long casedatetime, long timestamp)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
