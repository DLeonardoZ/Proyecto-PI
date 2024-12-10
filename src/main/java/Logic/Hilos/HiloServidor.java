package Logic.Hilos;

import Logic.ClaseRServer;
import Logic.InterfaceRServer;
import UIControles.ClienteServidor;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class HiloServidor extends Thread {
    String ip;

    @Override
    public void run() {
        try {
            ClienteServidor.estado(2);
            ip = java.net.InetAddress.getLocalHost().getHostAddress();
            String url = "//" + ip  + ":1234/RMI";

            // Inicialización del servidor: OK
            InterfaceRServer objetoRemoto = new ClaseRServer();
            java.rmi.Naming.rebind(url, objetoRemoto);

            System.out.println("\nHost: " + url);
            System.out.println("Servidor RMI: OK");
            ClienteServidor.estado(1);

        } catch (Exception ex) {
            ClienteServidor.estado(3);
            System.out.println("Error al arrancar el servidor RMI. (Servidor)");
            System.out.println(ex.getMessage());
        }
    }

    public static void levantarPuerto() {
        try {
            Registry registry = LocateRegistry.createRegistry(1234);
            System.out.println(registry);
        } catch (Exception ex) {
            System.out.println("Error al abrir puerto. (Registry - Servidor)");
            System.out.println(ex.getMessage());
        }
    }

    public static void detenerRMI() {
        try {
            String ip = java.net.InetAddress.getLocalHost().getHostAddress();
            String url = "//" + ip + ":1234/RMI";
            java.rmi.Naming.unbind(url);
            System.out.println("\nUnbind: " + url);
            System.out.println("Servidor RMI: OFF");
            ClienteServidor.estado(0);
        } catch (Exception ex) {
            System.out.println("Error al detener el RMI. (Servidor)");
            System.out.println(ex.getMessage());
        }
    }
}
