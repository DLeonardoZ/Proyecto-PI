package Logic;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceRServer extends Remote {
    void addAddress(String ip) throws RemoteException;
    void removeAddress(String ip) throws RemoteException;
}
