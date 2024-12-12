package Logic;

import UI.Arreglos;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Comparator;
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
        quickSort(subLists, 0, subLists.size() - 1, new ListComparator());

        for (List<Integer> subList : subLists) {
            Arreglos.sumarArreglo((ArrayList<Integer>) subList);
        }
    }

    public static void quickSort(List<List<Integer>> arr, int low, int high, Comparator<List<Integer>> comparator) {
        if (low < high) {
            int pi = partition(arr, low, high, comparator);

            quickSort(arr, low, pi - 1, comparator);
            quickSort(arr, pi + 1, high, comparator);
        }
    }

    public static int partition(List<List<Integer>> arr, int low, int high, Comparator<List<Integer>> comparator) {
        List<Integer> pivot = arr.get(high);
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (comparator.compare(arr.get(j), pivot) < 0) {
                i++;
                List<Integer> temp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, temp);
            }
        }

        List<Integer> temp = arr.get(i + 1);
        arr.set(i + 1, arr.get(high));
        arr.set(high, temp);

        return i + 1;
    }

    static class ListComparator implements Comparator<List<Integer>> {
        @Override
        public int compare(List<Integer> o1, List<Integer> o2) {
            // Comparar por tamaño de la lista
            return Integer.compare(o1.size(), o2.size());
        }
    }

    public void resetSubLists() {
        subLists.clear();
    }
}
