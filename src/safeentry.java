import java.util.ArrayList;

public interface safeentry extends java.rmi.Remote {

	public Boolean login(String nric, String password) throws java.rmi.RemoteException;

	public Boolean checkin(String nric, String location, long timestamp) throws java.rmi.RemoteException;

	public Boolean checkout(String nric, int checkid, long timestamp) throws java.rmi.RemoteException;

	public Boolean groupcheckin(String[] nrics, String location, long timestamp) throws java.rmi.RemoteException;

	public Boolean groupcheckout(String[] nrics, String location, long timestamp) throws java.rmi.RemoteException;

	public ArrayList<locationPOJO> locationhistory(int locationid) throws java.rmi.RemoteException;

	public ArrayList<locationPOJO> myhistory(String nric) throws java.rmi.RemoteException;

	public String[] listlocations() throws java.rmi.RemoteException;

	public Boolean declarecase(String nric, int locationid, long casedatetime, long timestamp)
			throws java.rmi.RemoteException;

}
