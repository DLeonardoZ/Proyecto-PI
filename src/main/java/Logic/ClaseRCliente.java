package Logic;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ClaseRCliente extends UnicastRemoteObject implements InterfaceRCliente {
    List<List<Integer>> subLists;

    public ClaseRCliente() throws RemoteException {
        // Constructor
        subLists = new ArrayList<>();
    }

    public void addSubList(List<Integer> subList) throws RemoteException {
        subLists.add(subList);
        System.out.println(subList);
    }
}
