package Logic;

import UI.Arreglos;

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

    public void recibirSubList(List<Integer> subList) throws RemoteException {
        subLists.add(subList);
        mostrarSubList();
        resetSubLists();
    }

    // Getters y Setters
    public static List<String> getAddress() {
        return address;
    }

    public static void addSubList(List<Integer> subList) {
        // Ordenamos el arreglo del servidor
        ArrayList<Integer> sortedNums = MonteCarlo.concurrenteRemoto((ArrayList<Integer>) subList);
        subLists.add(sortedNums);
    }

    public void mostrarSubList() {
        // Flatten the subLists into a single list
        List<Integer> allNumbers = new ArrayList<>();
        for (List<Integer> subList : subLists) {
            allNumbers.addAll(subList);
        }

        // Convert the list to an ArrayList
        ArrayList<Integer> arrayList = new ArrayList<>(allNumbers);

        // Sort the ArrayList using quickSort
        MonteCarlo.quickSort(arrayList, 0, arrayList.size() - 1);

        // Update subLists with the sorted list
        subLists.clear();
        subLists.add(arrayList);

        // Display the sorted subLists
        for (List<Integer> subList : subLists) {
            Arreglos.sumarArreglo((ArrayList<Integer>) subList);
        }
    }

    public void resetSubLists() {
        subLists.clear();
    }
}
