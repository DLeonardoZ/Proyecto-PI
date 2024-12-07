import UIControles.ClienteServidor;
import UIControles.Concurrencia;
import Logic.Graphics.Figura;
import UI.*;
import UIControles.Paralela;

import javax.swing.JFrame;
import java.awt.Graphics;

public class Main extends JFrame {
    final int width = 1300, height = 800; // Ventana
    final static int rectW = 400, rectH = 400; // Figura

    Figura figura = new Figura(rectW, rectH);
    ClienteServidor clienteServidor = new ClienteServidor();
    Arreglos arreglos = new Arreglos();
    Resultados resultados = new Resultados();
    Formulas formulas = new Formulas();
    NumHilos numHilos = new NumHilos();
    Paralela paralela = new Paralela();
    Concurrencia concurrencia;

    public Main() {
        super("Simulación de monte carlo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(100, 15);
        setResizable(false);
        setLayout(null);
        setSize(width, height);

        figura.setLocation(20, 20);
        formulas.setLocation(450, 20);
        resultados.setLocation(450, 310);
        arreglos.setLocation(820, 20);
        numHilos.setLocation(20, 440);
        clienteServidor.setLocation(450, 600);
        paralela.setLocation(820, 600);

        add(figura);
        add(formulas);
        add(resultados);
        add(arreglos);
        add(numHilos);
        add(clienteServidor);
        add(paralela);
    }

    @Override
    public void paint(Graphics vtnMain) {
        super.paint(vtnMain);

        concurrencia = new Concurrencia(rectW, rectH, figura.getGraphics(), figura, resultados);
        concurrencia.setLocation(450, 150);
        add(concurrencia);
    }

    public static void main(String[] args) {
        Main vtn = new Main();
        vtn.setVisible(true);
    }
}