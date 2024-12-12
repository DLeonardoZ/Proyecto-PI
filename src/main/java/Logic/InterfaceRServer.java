package Logic;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface InterfaceRServer extends Remote {
    void addAddress(String ip) throws RemoteException;
    void removeAddress(String ip) throws RemoteException;
    void recibirSubList(List<Integer> subList) throws RemoteException;
}
