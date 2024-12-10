package Logic;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ClaseRServer extends UnicastRemoteObject implements InterfaceRServer {
List<String> address;

    public ClaseRServer() throws RemoteException {
        // Constructor
        address = new ArrayList<>();
    }

    public void addAddress(String ip) throws RemoteException {
        address.add(ip);
        System.out.println("Nuevo cliente: " + ip);
        System.out.println(address);
    }
}
