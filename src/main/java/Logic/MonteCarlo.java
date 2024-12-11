package Logic;

import Logic.Graphics.Pixel;
import UIControles.Concurrencia;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Graphics;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

public class MonteCarlo {
    static ExecutorService executor;
    static Graphics figura;
    static int contador;
    static int cores;
    static int numThreads;
    static int rectW;
    static int rectH;

    public MonteCarlo(int rectW, int rectH, Graphics figura) {
        executor = null;
        MonteCarlo.rectW = rectW;
        MonteCarlo.rectH = rectH;
        MonteCarlo.figura = figura;
    }

    public ArrayList<Integer> dibujarSecuencial(int n) {
        ArrayList<Integer> randomNumbers = new ArrayList<>();
        Random rand = new Random();
        Pixel pixel = new Pixel();
        int r = rectW / 2;

        BufferedImage buffer = new BufferedImage(rectW, rectH, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < n; i++) {
            int randomNumber = rand.nextInt(600001) - 300000;
            randomNumbers.add(randomNumber);
            int x = rand.nextInt(rectW);
            int y = rand.nextInt(rectH);

            // Comprobar si el punto está dentro del círculo
            if (Math.pow(x - r, 2) + Math.pow(y - r, 2) <= Math.pow(r, 2)) {
                pixel.createPixel(x, y, Color.BLUE, buffer.getGraphics());
                contador++;
            } else {
                pixel.createPixel(x, y, Color.RED, buffer.getGraphics());
            }
        }
        figura.drawImage(buffer, 0, 0, null);

        Burbuja(randomNumbers);
        return randomNumbers;
    }

    public static ArrayList<Integer> dibujarConcurrente(int n) {
        ArrayList<Integer> randomNumbers = new ArrayList<>();
        Random rand = new Random();
        Pixel pixel = new Pixel();
        int r = rectW / 2;

        BufferedImage buffer = new BufferedImage(rectW, rectH, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < n; i++) {
            int randomNumber = rand.nextInt(600001) - 300000;
            randomNumbers.add(randomNumber);
            int x = rand.nextInt(rectW);
            int y = rand.nextInt(rectH);

            // Comprobar si el punto está dentro del círculo
            if (Math.pow(x - r, 2) + Math.pow(y - r, 2) <= Math.pow(r, 2)) {
                pixel.createPixel(x, y, Color.BLUE, buffer.getGraphics());
                contador++;
            } else {
                pixel.createPixel(x, y, Color.RED, buffer.getGraphics());
            }
        }
        figura.drawImage(buffer, 0, 0, null);

        // Número de núcleos disponibles
        cores = Runtime.getRuntime().availableProcessors();
        // Calcular el número de hilos basado en el valor de n
        numThreads = cores;
        while (n / numThreads > 700) {
            numThreads += cores / 3;
        }
        numThreads = Math.min(numThreads, cores * 8); // Limitar el número de hilos a 3 veces el número de núcleos

        // Número fijo de hilos por núcleo
        try (ExecutorService executor = Executors.newFixedThreadPool(cores)) {

            // Lista para almacenar las tareas Future
            List<Future<ArrayList<Integer>>> futures = new ArrayList<>();

            // Tamaño de cada subarreglo
            int size = randomNumbers.size() / numThreads;

            // Enviar cada subarreglo a un hilo separado para ser ordenado
            for (int i = 0; i < numThreads; i++) {
                int start = i * size;
                int end = (i + 1) * size;
                if (i == numThreads - 1) {
                    end = randomNumbers.size();
                }
                ArrayList<Integer> subList = new ArrayList<>(randomNumbers.subList(start, end));
                futures.add(executor.submit(() -> {
                    Burbuja(subList);
                    return subList;
                }));
            }

            // Recoger los subarreglos ordenados y combinarlos en un solo arreglo
            randomNumbers.clear();
            for (Future<ArrayList<Integer>> future : futures) {
                try {
                    randomNumbers.addAll(future.get());
                } catch (InterruptedException | ExecutionException e) {
                    System.out.println(e.getMessage());
                }
            }
            // Cerrar el ExecutorService
            executor.shutdown();
        }

        // Ordenar el arreglo utilizando QuickSort
        quickSort(randomNumbers, 0, randomNumbers.size() - 1);
        return randomNumbers;
    }

    public static ArrayList<Integer> concurrenteRemoto(ArrayList<Integer> randomNumbers) {
        int n = randomNumbers.size();
        cores = Runtime.getRuntime().availableProcessors(); // Número de núcleos disponibles

        numThreads = cores; // Calcular el número de hilos basado en el valor de n
        while (n / numThreads > 700) {
            numThreads += cores / 3;
        }
        numThreads = Math.min(numThreads, cores * 8); // Limitar el número de hilos a 3 veces el número de núcleos

        // Número fijo de hilos por núcleo
        try (ExecutorService executor = Executors.newFixedThreadPool(cores)) {
            // Lista para almacenar las tareas Future
            List<Future<ArrayList<Integer>>> futures = new ArrayList<>();
            // Tamaño de cada subarreglo
            int size = randomNumbers.size() / numThreads;

            // Enviar cada subarreglo a un hilo separado para ser ordenado
            for (int i = 0; i < numThreads; i++) {
                int start = i * size;
                int end = (i + 1) * size;
                if (i == numThreads - 1) {
                    end = randomNumbers.size();
                }
                ArrayList<Integer> subList = new ArrayList<>(randomNumbers.subList(start, end));
                futures.add(executor.submit(() -> {
                    Burbuja(subList);
                    return subList;
                }));
            }

            // Recoger los subarreglos ordenados y combinarlos en un solo arreglo
            randomNumbers.clear();
            for (Future<ArrayList<Integer>> future : futures) {
                try {
                    randomNumbers.addAll(future.get());
                } catch (InterruptedException | ExecutionException e) {
                    System.out.println(e.getMessage());
                }
            }
            // Cerrar el ExecutorService
            executor.shutdown();
        }
        // Ordenar el arreglo utilizando QuickSort
        quickSort(randomNumbers, 0, randomNumbers.size() - 1);
        return randomNumbers;
    }

    public static ArrayList<Integer> dibujarParalelo(int n) {
        ArrayList<Integer> randomNumbers = new ArrayList<>();
        Random rand = new Random();
        Pixel pixel = new Pixel();
        int r = rectW / 2;

        BufferedImage buffer = new BufferedImage(rectW, rectH, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < n; i++) {
            int randomNumber = rand.nextInt(600001) - 300000;
            randomNumbers.add(randomNumber);
            int x = rand.nextInt(rectW);
            int y = rand.nextInt(rectH);

            // Comprobar si el punto está dentro del círculo
            if (Math.pow(x - r, 2) + Math.pow(y - r, 2) <= Math.pow(r, 2)) {
                pixel.createPixel(x, y, Color.BLUE, buffer.getGraphics());
                contador++;
            } else {
                pixel.createPixel(x, y, Color.RED, buffer.getGraphics());
            }
        }
        figura.drawImage(buffer, 0, 0, null);

        // Diovidr el aregglo en subarreglos para ser enviados a los clientes
        int numClientes = ClaseRServer.getAddress().size() + 1;
        int size = Concurrencia.getIteraciones() / numClientes;
        AtomicReference<List<ArrayList<Integer>>> subLists = new AtomicReference<>(new ArrayList<>());

        for (int i = 0; i < numClientes; i++) {
            int start = i * size;
            int end = (i + 1) * size;
            if (i == numClientes - 1) {
                end = randomNumbers.size();
            }
            ArrayList<Integer> subList = new ArrayList<>(randomNumbers.subList(start, end));
            subLists.get().add(subList);

            // Si es la primera iteracion
            if (i == 0) {
                ClaseRServer.addSubList(subList);
            } else {
                // Enviar subList a un cliente
                try {
                    String ipCliente = ClaseRServer.getAddress().get(i - 1);
                    InterfaceRCliente objetoCliente = (InterfaceRCliente) java.rmi.Naming.lookup("//" +
                            ipCliente + ":1234/RMI");
                    objetoCliente.addSubList(subList);

                } catch (Exception ex) {
                    System.out.println("Error al enviar subList al cliente. (Servidor)");
                    System.out.println(ex.getMessage());
                }
            }
        }

        // Ordenar el arreglo utilizando QuickSort
        quickSort(randomNumbers, 0, randomNumbers.size() - 1);
        return randomNumbers;
    }

    // ------------------- Métodos de ordenamiento -------------------

    public static void Burbuja(ArrayList<Integer> arr) {
        int n = arr.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr.get(j) > arr.get(j + 1)) {
                    // Intercambiar arr[j+1] y arr[j]
                    int temp = arr.get(j);
                    arr.set(j, arr.get(j + 1));
                    arr.set(j + 1, temp);
                }
            }
        }
    }

    public static void quickSort(ArrayList<Integer> arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);

            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    public static int partition(ArrayList<Integer> arr, int low, int high) {
        int pivot = arr.get(high);
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (arr.get(j) < pivot) {
                i++;

                // Intercambiar arr[i] y arr[j]
                int temp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, temp);
            }
        }

        // Intercambiar arr[i+1] y arr[high] (o pivot)
        int temp = arr.get(i + 1);
        arr.set(i + 1, arr.get(high));
        arr.set(high, temp);

        return i + 1;
    }

    // ------------------- Getters y Setters -------------------

    public static int getContador() {
        return contador;
    }

    public static void setContador() {
        contador = 0;
    }

    public static int getCores() {
        return numThreads;
    }
}