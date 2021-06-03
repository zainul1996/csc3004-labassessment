import java.rmi.RemoteException;
import java.util.ArrayList;

public interface SafeEntryServerIntf extends java.rmi.Remote {

	public int login(SafeEntryClientIntf client, String nric, String password) throws java.rmi.RemoteException;

	public Boolean checkin(String nric, String location, long timestamp) throws java.rmi.RemoteException;

	public Boolean checkout(String nric, String checkLocation, long timestamp) throws java.rmi.RemoteException;

	public Boolean groupcheckin(String[] nrics, String location, long timestamp) throws java.rmi.RemoteException;

	public Boolean groupcheckout(String[] nrics, String location, long timestamp) throws java.rmi.RemoteException;

	public ArrayList<Location> grouphistory(String nrics[]) throws java.rmi.RemoteException;

	public ArrayList<Location> myhistory(String nric) throws java.rmi.RemoteException;

	public String[] listlocations() throws java.rmi.RemoteException;

	public Boolean declarecase(String location, long timestamp) throws java.rmi.RemoteException;

	public Boolean logout(String nric) throws RemoteException;

}
