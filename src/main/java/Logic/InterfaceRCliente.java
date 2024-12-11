package Logic;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface InterfaceRCliente extends Remote {
    void addSubList(List<Integer> subList) throws RemoteException;
}
