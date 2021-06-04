import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@SuppressWarnings("serial")
public class SafeEntryImpl extends java.rmi.server.UnicastRemoteObject implements SafeEntryServerIntf {
	HashMap<String, SafeEntryClientIntf> clientmap = new HashMap<String, SafeEntryClientIntf>();

	protected SafeEntryImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

//	public String checkUserCOVID(String nric) {
////		locationPOJO[] covid = new locationPOJO[1];
////		locationPOJO loc1 = new locationPOJO();
////		loc1.setLocation("Marina Bay Sands");
////		loc1.setTimestamp((long) new Date().getTime());
////		covid[0] = loc1;
//		// FileReader for locationhistory.json
//		JSONParser jsonParser = new JSONParser();
//		FileReader covidReader;
//		try {
//			covidReader = new FileReader("covid.json");
//
//			// Construct a JSONObject out of existing checkin
//			Object covidObj = jsonParser.parse(covidReader);
//			JSONArray covidJsonArr = (JSONArray) covidObj;
//
//			for (int i = 0; i < covidJsonArr.size(); i++) {
//				JSONObject covidJsonObj = (JSONObject) covidJsonArr.get(i);
//				Date end = new Date((Long) covidJsonObj.get("timestamp"));
//				Date start = new Date((Long) covidJsonObj.get("timestamp") - 14 * 24 * 60 * 60 * 1000);
//				start.setHours(8);
//				start.setMinutes(0);
//				start.setSeconds(0);
//
//				// FileReader for locationhistory.json
//				FileReader locationHistoryReader = new FileReader("locationhistory.json");
//
//				// Construct a JSONObject out of existing checkin
//				Object locationHistoryObj = jsonParser.parse(locationHistoryReader);
//				JSONObject locationHistoryJsonObj = (JSONObject) locationHistoryObj;
//				JSONArray locationJsonArr = (JSONArray) locationHistoryJsonObj.get(covidJsonObj.get("location"));
//				if (locationJsonArr != null) {
//					for (int n = 0; n < locationJsonArr.size(); n++) {
//						JSONObject locationObj = (JSONObject) locationJsonArr.get(n);
//						if (locationObj.get("nric").equals(nric) && (Boolean) locationObj.get("alerted") == false) {
//							long checkintime = (long) locationObj.get("intimestamp");
//							if (checkintime >= start.getTime() && checkintime <= end.getTime()) {
//								System.out.println("INFECTED");
//								locationObj.replace("alerted", true);
//								locationJsonArr.remove(n);
//								locationJsonArr.add(locationObj);
//								locationHistoryJsonObj.replace(covidJsonObj.get("location"), locationJsonArr);
//								FileWriter locationHistoryWriter = new FileWriter("locationhistory.json");
//								// Write it into the file
//								locationHistoryWriter.write(locationHistoryJsonObj.toJSONString());
//								locationHistoryWriter.flush();
//								locationHistoryWriter.close();
//								System.out.println(start.getTime());
//								System.out.println(end.getTime());
//								System.out.println(checkintime);
//								return covidJsonObj.get("location").toString();
//							}
//						}
//					}
//				}
//
//				// FileReader for locationhistory.json
//				FileReader checkinReader = new FileReader("checkin.json");
//
//				// Construct a JSONObject out of existing checkin
//				Object allCheckinObj = jsonParser.parse(checkinReader);
//				JSONObject allCheckinJsonObj = (JSONObject) allCheckinObj;
//				JSONArray checkinJsonArr = (JSONArray) allCheckinJsonObj.get(nric);
//				if (checkinJsonArr != null) {
//					for (int n = 0; n < checkinJsonArr.size(); n++) {
//						JSONObject checkinObj = (JSONObject) checkinJsonArr.get(n);
//						if (checkinObj.get("location").equals(covidJsonObj.get("location"))
//								&& (Boolean) checkinObj.get("alerted") == false) {
//							long checkintime = (long) checkinObj.get("intimestamp");
//							if (checkintime >= start.getTime() && checkintime <= end.getTime()) {
//								System.out.println("INFECTED");
//								checkinObj.replace("alerted", true);
//								checkinJsonArr.remove(n);
//								checkinJsonArr.add(checkinObj);
//								allCheckinJsonObj.replace(nric, checkinJsonArr);
//								FileWriter checkinWriter = new FileWriter("checkin.json");
//								// Write it into the file
//								checkinWriter.write(allCheckinJsonObj.toJSONString());
//								checkinWriter.flush();
//								checkinWriter.close();
//
//								System.out.println(start.getTime());
//								System.out.println(end.getTime());
//								System.out.println(checkintime);
//								return covidJsonObj.get("location").toString();
//							}
//						}
//					}
//				}
//
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println("CLEAN");
//		return "";
//	}
//
//	@Override
//	public synchronized int login(safeentryclientIntf client, String nric, String password) throws RemoteException {
//		JSONParser jsonParser = new JSONParser();
//
//		try {
//			// Read user.json
//			FileReader reader = new FileReader("user.json");
//
//			// Get the object that matches the nric
//			Object obj = jsonParser.parse(reader);
//			JSONObject usersObj = (JSONObject) obj;
//			if (usersObj.containsKey(nric)) {
//				JSONObject userObj = (JSONObject) usersObj.get(nric);
//				if (userObj.get("password").equals(password)) {
//					Thread thread = new Thread(new Runnable() {
//						@Override
//						public void run() {
//							try {
//								while (true) {
//									Thread.sleep(5000);
//									String result = checkUserCOVID(nric);
//									if (result.length() > 0) {
//										client.callBack(result);
//									}
//									;
//								}
//							} catch (java.rmi.RemoteException e) {
//								e.printStackTrace();
//							} catch (InterruptedException ee) {
//							}
//						}
//					});
//					thread.start();
//					if((Boolean)userObj.get("isMOH") == false) {
//						return 1;
//					}else {
//						return 2;
//					}
//				}
//
//			} else {
//				return 0;
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			return 0;
//		}
//		return 0;
//	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized Boolean declarecase(String location, long timestamp) throws RemoteException {
		try {
			JSONParser jsonParser = new JSONParser();
			Date end = new Date(timestamp);
			Date start = new Date(timestamp - 14 * 24 * 60 * 60 * 1000);
			start.setHours(8);
			start.setMinutes(0);
			start.setSeconds(0);

			ArrayList<String> alerted = new ArrayList<>();

			// FileReader for locationhistory.json
			FileReader locationHistoryReader = new FileReader("locationhistory.json");

			// Construct a JSONObject out of existing checkin
			Object locationHistoryObj = jsonParser.parse(locationHistoryReader);
			JSONObject locationHistoryJsonObj = (JSONObject) locationHistoryObj;
			JSONArray locationJsonArr = (JSONArray) locationHistoryJsonObj.get(location);
			if (locationJsonArr != null) {
				for (int n = 0; n < locationJsonArr.size(); n++) {
					JSONObject locationObj = (JSONObject) locationJsonArr.get(n);
					long checkintime = (long) locationObj.get("intimestamp");
					if (checkintime >= start.getTime() && checkintime <= end.getTime()) {
						if (clientmap.containsKey(locationObj.get("nric"))
								& !alerted.contains(locationObj.get("nric"))) {
							SafeEntryClientIntf clientobj = clientmap.get(locationObj.get("nric"));
							alerted.add((String) locationObj.get("nric"));
							System.out.println("Alerted: " + locationObj.get("nric"));
							clientobj.callBack(location);
						} else {
							JSONObject alertLocationObj = new JSONObject();
							alertLocationObj.put("location", location);
							try {
								// FileReader for alert.json
								FileReader reader = new FileReader("alert.json");

								// Construct a JSONObject out of existing checkin
								Object obj;

								obj = jsonParser.parse(reader);

								JSONObject checkInObj = (JSONObject) obj;
								if (checkInObj.containsKey(locationObj.get("nric").toString())) {
									checkInObj.remove(locationObj.get("nric").toString());
								}
								checkInObj.put(locationObj.get("nric").toString(), alertLocationObj);

								// FileWriter for alert.json
								FileWriter file = new FileWriter("alert.json");
								// Write it into the file
								file.write(checkInObj.toJSONString());
								file.flush();

							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							System.out.println(locationObj.get("nric") + " not online");
						}
					}
				}
			}

			// FileReader for locationhistory.json
			FileReader checkinReader = new FileReader("checkin.json");

			// Construct a JSONObject out of existing checkin
			Object allCheckinObj = jsonParser.parse(checkinReader);
			JSONObject allCheckinJsonObj = (JSONObject) allCheckinObj;
			allCheckinJsonObj.keySet().forEach(keyStr -> {
				JSONArray keyvalue = (JSONArray) allCheckinJsonObj.get(keyStr);
				System.out.println(keyStr.toString());
				System.out.println(keyvalue.toJSONString());
				for (Object checkInArr : keyvalue) {
					JSONObject checkinObj = (JSONObject) checkInArr;
					long checkintime = (long) checkinObj.get("intimestamp");
					if (checkintime >= start.getTime() && checkintime <= end.getTime()
							&& checkinObj.get("location").equals(location)) {
						if (clientmap.containsKey(keyStr.toString()) & !alerted.contains(keyStr.toString())) {
							SafeEntryClientIntf clientobj = clientmap.get(keyStr.toString());
							System.out.println("Alerted: " + keyStr.toString());
							alerted.add(keyStr.toString());
							try {
								clientobj.callBack(location);
							} catch (RemoteException e) {
								e.printStackTrace();
							}

						} else {
							JSONObject alertLocationObj = new JSONObject();
							alertLocationObj.put("location", location);
							try {
								// FileReader for alert.json
								FileReader reader = new FileReader("alert.json");

								// Construct a JSONObject out of existing checkin
								Object obj;

								obj = jsonParser.parse(reader);

								JSONObject checkInObj = (JSONObject) obj;
								if (checkInObj.containsKey(keyStr.toString())) {
									checkInObj.remove(keyStr.toString());
								}
								checkInObj.put(keyStr.toString(), alertLocationObj);

								// FileWriter for alert.json
								FileWriter file = new FileWriter("alert.json");
								// Write it into the file
								file.write(checkInObj.toJSONString());
								file.flush();

							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							System.out.println(keyStr.toString() + " not online");
						}
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public synchronized int login(SafeEntryClientIntf client, String nric, String password) throws RemoteException {
		JSONParser jsonParser = new JSONParser();
		try {
			// Read user.json
			FileReader reader = new FileReader("user.json");

			// Get the object that matches the nric
			Object obj = jsonParser.parse(reader);
			JSONObject usersObj = (JSONObject) obj;
			if (usersObj.containsKey(nric)) {
				JSONObject userObj = (JSONObject) usersObj.get(nric);
				if (userObj.get("password").equals(password)) {
					Thread thread = new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								// FileReader for alert.json
								FileReader reader = new FileReader("alert.json");

								// Construct a JSONObject out of existing checkin
								Object obj;

								obj = jsonParser.parse(reader);

								JSONObject checkInObj = (JSONObject) obj;
								if (checkInObj.containsKey(nric)) {
									JSONObject userCheckInObj = (JSONObject) checkInObj.get(nric);
									String location = (String) userCheckInObj.get("location");
									checkInObj.remove(nric);
									// FileWriter for alert.json
									FileWriter file = new FileWriter("alert.json");
									// Write it into the file
									file.write(checkInObj.toJSONString());
									file.flush();
									client.callBack(location);
								}

							} catch (java.rmi.RemoteException e) {
								e.printStackTrace();
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
					thread.start();
					clientmap.put(nric, client);
					System.out.println(clientmap.size());
					if ((Boolean) userObj.get("isMOH") == false) {
						return 1;
					} else {
						return 2;
					}
				}

			} else {
				return 0;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 0;
	}

	@Override
	public synchronized Boolean logout(String nric) throws RemoteException {
		if (clientmap.containsKey(nric)) {
			clientmap.remove(nric);
			System.out.println(clientmap.size());

		}
		return true;
	}

	@SuppressWarnings({ "unchecked", "resource" })
	@Override
	public Boolean checkin(String nric, String location, long timestamp) throws RemoteException {
		// Create check in object
		JSONObject checkInObject = new JSONObject();
		checkInObject.put("location", location);
		checkInObject.put("intimestamp", timestamp);
		checkInObject.put("alerted", false);

		try {
			JSONParser jsonParser = new JSONParser();

			// FileReader for checkIn.json
			FileReader reader = new FileReader("checkIn.json");

			// Construct a JSONObject out of existing checkin
			Object obj = jsonParser.parse(reader);
			JSONObject checkInObj = (JSONObject) obj;
			if (checkInObj.containsKey(nric)) {
				// Get the list of user check in's and add the new check in to it
				JSONArray userCheckInList = (JSONArray) checkInObj.get(nric);
				userCheckInList.add(checkInObject);

				// Replace the old check in list with new check in list in object
				checkInObj.replace(nric, userCheckInList);
			} else {
				// Get the list of user check in's and add the new check in to it
				JSONArray userCheckInList = new JSONArray();
				userCheckInList.add(checkInObject);

				// Replace the old check in list with new check in list in object
				checkInObj.put(nric, userCheckInList);

			}

			// FileWriter for checkIn.json
			FileWriter file = new FileWriter("checkIn.json");
			// Write it into the file
			file.write(checkInObj.toJSONString());
			file.flush();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings({ "unchecked", "resource" })
	@Override
	public synchronized Boolean checkout(String nric, String checkLocation, long outtimestamp) throws RemoteException {
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
			JSONArray newuserCheckInList = new JSONArray();
			JSONArray removedLocationList = new JSONArray();
			for (int i = 0; i < userCheckInList.size(); i++) {
				if (!((JSONObject) userCheckInList.get(i)).get("location").equals(checkLocation)) {
					newuserCheckInList.add((userCheckInList.get(i)));
				} else {
					removedLocationList.add((userCheckInList.get(i)));
				}
			}

//			JSONObject removedLocation = (JSONObject) userCheckInList.remove(userCheckInList.lastIndexOf(checkLocation));
			// Replace the old check in list with new check in list in object
			checkInObj.replace(nric, newuserCheckInList);

			// Write it into the file
			reader.close();
			file.write(checkInObj.toJSONString());
			file.flush();

			// FileReader for locationhistory.json
			FileReader locationHistoryReader = new FileReader("locationhistory.json");

			// Construct a JSONObject out of existing checkin
			Object locationHistoryObj = jsonParser.parse(locationHistoryReader);
			JSONObject locationHistoryJsonObj = (JSONObject) locationHistoryObj;

			// FileWriter for locationhistory.json
			FileWriter locationHistoryWriter = new FileWriter("locationhistory.json");

			for (Object removedLocationObj : removedLocationList) {
				JSONObject removedLocation = (JSONObject) removedLocationObj;

				// Get the list of checkin/checkout history
				if (locationHistoryJsonObj.containsKey(removedLocation.get("location"))) {
					JSONArray historyJsonObj = (JSONArray) locationHistoryJsonObj.get(removedLocation.get("location"));
					// Create the new checkin/checkout object and add into the history
					JSONObject checkInObject = new JSONObject();
					checkInObject.put("nric", nric);
					checkInObject.put("intimestamp", removedLocation.get("intimestamp"));
					checkInObject.put("outtimestamp", outtimestamp);
					checkInObject.put("alerted", false);
					historyJsonObj.add(checkInObject);

					// Replace the old checkin/checkout history with new checkin/checkout history
					locationHistoryJsonObj.replace(removedLocation.get("location"), historyJsonObj);
				} else {
					JSONArray historyJsonObj = new JSONArray();
					// Create the new checkin/checkout object and add into the history
					JSONObject checkInObject = new JSONObject();
					checkInObject.put("nric", nric);
					checkInObject.put("intimestamp", removedLocation.get("intimestamp"));
					checkInObject.put("outtimestamp", outtimestamp);
					checkInObject.put("alerted", false);
					historyJsonObj.add(checkInObject);

					// Replace the old checkin/checkout history with new checkin/checkout history
					locationHistoryJsonObj.put(removedLocation.get("location"), historyJsonObj);

				}

				// Write it into the file
				locationHistoryWriter.write(locationHistoryJsonObj.toJSONString());
				locationHistoryWriter.flush();
			}
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public synchronized Boolean groupcheckin(String[] nrics, String location, long timestamp) throws RemoteException {
		Boolean[] result = new Boolean[nrics.length];
		for (int i = 0; i < nrics.length; i++) {
			result[i] = checkin(nrics[i], location, timestamp);
		}

		for (int i = 0; i < result.length; i++) {
			if (!result[i]) {
				System.out.println("Something went wrong with nric: " + nrics[i]);
				return false;
			}
		}
		return true;
	}

	@Override
	public synchronized Boolean groupcheckout(String[] nrics, String location, long timestamp) throws RemoteException {
		Boolean[] result = new Boolean[nrics.length];
		for (int i = 0; i < nrics.length; i++) {
			result[i] = checkout(nrics[i], location, timestamp);
		}

		for (int i = 0; i < result.length; i++) {
			if (!result[i]) {
				System.out.println("Something went wrong with nric: " + nrics[i]);
				return false;
			}
		}
		return true;
	}

	@Override
	public synchronized ArrayList<Location> grouphistory(String nrics[]) throws RemoteException {
		ArrayList<Location> similarLocationList = new ArrayList<Location>();

		for (int i = 0; i < nrics.length; i++) {
			ArrayList<Location> locationArrList = myhistory(nrics[i]);
			if (locationArrList == null) {
				return null;
			} else if (locationArrList.size() == 0) {
				return null;
			} else {
				if (i == 0) {
					similarLocationList = locationArrList;
				} else {
					similarLocationList.retainAll(locationArrList);
				}
			}
		}
		return similarLocationList;
	}

	@Override
	public synchronized ArrayList<Location> myhistory(String nric) throws RemoteException {
		try {
			JSONParser jsonParser = new JSONParser();
			Location location;

			// FileReader for checkIn.json
			FileReader reader = new FileReader("checkIn.json");

			// Construct a JSONObject out of existing checkin
			Object obj = jsonParser.parse(reader);
			JSONObject checkInObj = (JSONObject) obj;

			if (checkInObj.containsKey(nric)) {
				// Get list of currently checkin places
				JSONArray userCheckInList = (JSONArray) checkInObj.get(nric);

				ArrayList<Location> locationArrList = new ArrayList<Location>();
				for (Object object : userCheckInList) {
					JSONObject locationObj = (JSONObject) object;
					System.out.println(locationObj.get("location") + " : " + locationObj.get("intimestamp"));
					location = new Location((String) locationObj.get("location"), (Long) locationObj.get("intimestamp"));
					locationArrList.add(location);
				}

				return locationArrList;
			}
			return null;

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

}
