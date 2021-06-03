import java.rmi.Remote;

public interface SafeEntryClientIntf extends Remote {
	public void callBack(String s) throws java.rmi.RemoteException;
}
