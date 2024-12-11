package Logic;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ClaseRServer extends UnicastRemoteObject implements InterfaceRServer {
static List<String> address;
static List<List<Integer>> subLists;

    public ClaseRServer() throws RemoteException {
        // Constructor
        subLists = new ArrayList<>();
        address = new ArrayList<>();
    }

    // Métodos remotos

    public void addAddress(String ip) throws RemoteException {
        address.add(ip);
        System.out.println("Nuevo cliente: " + ip);
    }
    public void removeAddress(String ip) throws RemoteException {
        address.remove(ip);
        System.out.println("Cliente desconectado: " + ip);
    }

    // Getters y Setters
    public static List<String> getAddress() {
        return address;
    }

    public static void addSubList(List<Integer> subList) {
        subLists.add(subList);
        System.out.println(subList);
    }
}
