package Logic.Hilos;
import Logic.ClaseRCliente;
import Logic.InterfaceRCliente;
import UIControles.ClienteServidor;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class HiloCliente extends Thread {

    public void run() {
        try {
            ClienteServidor.estado(2);
            String ip = ClienteServidor.getIp();
            String url = "//" + ip + ":1234/RMI";

            // Inicialización del servidor: OK
            InterfaceRCliente objetoRemoto = new ClaseRCliente();
            java.rmi.Naming.rebind(url, objetoRemoto);

            System.out.println("\nCliente: " + url);
            System.out.println("Servidor RMI: OK");
            ClienteServidor.estado(1);

        } catch (Exception ex) {
            ClienteServidor.estado(3);
            System.out.println("Error al arrancar el servidor RMI. (Cliente)");
            System.out.println(ex.getMessage());
        }
    }

    public static void levantarPuerto() {
        try {
            Registry registry = LocateRegistry.createRegistry(1234);
            System.out.println(registry);
        } catch (Exception ex) {
            System.out.println("Error al abrir puerto. (Registry - Cliente)");
            System.out.println(ex.getMessage());
        }
    }

    public static void detenerRMI() {
        try {
            String ip = ClienteServidor.getIp();
            String url = "//" + ip + ":1234/RMI";
            java.rmi.Naming.unbind(url);
            System.out.println("\nUnbind Cliente: " + url);
            System.out.println("Servidor RMI: OFF");
            ClienteServidor.estado(0);
        } catch (Exception ex) {
            System.out.println("Error al detener el RMI. (Cliente)");
            System.out.println(ex.getMessage());
        }
    }
}
