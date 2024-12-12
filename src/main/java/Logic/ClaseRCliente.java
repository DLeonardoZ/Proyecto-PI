package Logic;

import UIControles.ClienteServidor;

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

        String server = ClienteServidor.getServerIp();

        ArrayList<Integer> sortedNums = MonteCarlo.concurrenteRemoto((ArrayList<Integer>) subList);
        // Enviamos el arreglo otra vez al servidor
        try {
            InterfaceRServer objetoRemoto = (InterfaceRServer) java.rmi.Naming.lookup("//" + server + ":1234/RMI");
            objetoRemoto.recibirSubList(sortedNums);
        } catch (Exception ex) {
            System.out.println("Error al reenviar el arreglo al servidor. (Cliente)");
            System.out.println(ex.getMessage());
        }
    }
}
