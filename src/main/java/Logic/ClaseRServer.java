package Logic;

import UI.Arreglos;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ClaseRServer extends UnicastRemoteObject implements InterfaceRServer {
static List<String> address;
static List<List<Integer>> subLists;
static ArrayList<Integer> sortedNums;

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

    public void recibirSubList(List<Integer> subList) throws RemoteException {
        subLists.add(subList);

        ArrayList<Integer> allNumbers = new ArrayList<>();
        for (List<Integer> list : subLists) {
            allNumbers.addAll(list);
        }
        MonteCarlo.quickSort(allNumbers, 0, allNumbers.size() - 1);
        subLists.clear();
        subLists.add(allNumbers);
        mostrarSubList();
        resetSubLists();
    }

    // Getters y Setters
    public static List<String> getAddress() {
        return address;
    }

    public static void addSubList(List<Integer> subList) {
        subLists.add(subList);
        sortedNums = MonteCarlo.concurrenteRemoto((ArrayList<Integer>) subList);
    }

    public void mostrarSubList() {
        for (List<Integer> subList : subLists) {
            Arreglos.sumarArreglo((ArrayList<Integer>) subList);
        }
    }

    public void resetSubLists() {
        subLists.clear();
    }
}
