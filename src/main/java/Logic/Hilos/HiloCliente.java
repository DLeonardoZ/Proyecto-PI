package Logic.Hilos;
import Logic.ClaseRCliente;
import Logic.InterfaceRCliente;
import Logic.InterfaceRServer;
import UIControles.ClienteServidor;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class HiloCliente extends Thread {

    public void run() {
        try {
            ClienteServidor.estado(2);
            String ip = java.net.InetAddress.getLocalHost().getHostAddress();
            String url = "//" + ip + ":1234/RMI";

            InterfaceRCliente objetoCliente = new ClaseRCliente();
            java.rmi.Naming.rebind(url, objetoCliente);

            // Añadimos la conexion al servidor
            try {
                String server = ClienteServidor.getServerIp();
                InterfaceRServer objetoRemoto = (InterfaceRServer) java.rmi.Naming.lookup("//" + server + ":1234/RMI");
                objetoRemoto.addAddress(ip);

                // Inicialización del cliente: OK
                System.out.println("\nModo Cliente: " + url);
                System.out.println("Servidor RMI: OK");
                ClienteServidor.estado(1);

            } catch (Exception ex) {
                ClienteServidor.estado(3);
                System.out.println("Error al añadir la conexion al servidor. (Cliente)");
                System.out.println(ex.getMessage());
            }

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
            System.out.println("Error al abrir puerto. (Registry - Servidor)");
            System.out.println(ex.getMessage());
        }
    }

    public static void detenerRMI() {
        try {
            String server = ClienteServidor.getServerIp();
            String ip = java.net.InetAddress.getLocalHost().getHostAddress();

            try {
                InterfaceRServer objetoRemoto = (InterfaceRServer) java.rmi.Naming.lookup("//" + server + ":1234/RMI");
                objetoRemoto.removeAddress(ip);
            } catch (Exception ex) {
                System.out.println("Error al remover la conexion del servidor. (Cliente)");
                System.out.println(ex.getMessage());
            }

            String url = "//" + ip + ":1234/RMI";
            // java.rmi.Naming.unbind(url);

            System.out.println("\nUnbind Cliente: " + url);
            System.out.println("Servidor RMI: OFF");
            ClienteServidor.estado(0);

        } catch (Exception ex) {
            ClienteServidor.estado(4);
            System.out.println("Error al detener el RMI. (Cliente)");
            System.out.println(ex.getMessage());
        }
    }
}
