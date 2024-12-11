package UIControles;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import Logic.Graphics.Figura;
import Logic.MonteCarlo;
import UI.Arreglos;
import UI.NumHilos;
import UI.Resultados;

public class Concurrencia extends JPanel {
    JTextField pixelField;
    int rectW, rectH;
    Graphics figura;

    public Concurrencia(int rectW, int rectH, Graphics figura, Figura figuraObj, Resultados resultados) {
        this.rectW = rectW;
        this.rectH = rectH;
        this.figura = figura;

        setSize(350, 140);
        setBackground(Color.white);
        setLayout(null);

        pixelField = new JTextField();
        pixelField.setHorizontalAlignment(JTextField.CENTER);
        pixelField.setFont(pixelField.getFont().deriveFont(16.0f));
        pixelField.setSize(150, 30);
        pixelField.setLocation(20, 10);

        JButton btnSecuencial = new JButton("Secuencial");
        btnSecuencial.setFont(btnSecuencial.getFont().deriveFont(16.0f));
        btnSecuencial.setSize(150, 30);
        btnSecuencial.setLocation(20, 50);

        JButton btnConcurrente = new JButton("Concurrente");
        btnConcurrente.setFont(btnConcurrente.getFont().deriveFont(16.0f));
        btnConcurrente.setSize(150, 30);
        btnConcurrente.setLocation(20, 90);

        JButton resetButton = new JButton("Reset");
        resetButton.setFont(resetButton.getFont().deriveFont(16.0f));
        resetButton.setSize(150, 30);
        resetButton.setLocation(180, 10);

        JButton limpiarTabla = new JButton("Limpiar");
        limpiarTabla.setFont(limpiarTabla.getFont().deriveFont(16.0f));
        limpiarTabla.setSize(150, 30);
        limpiarTabla.setLocation(180, 50);

        JLabel lblRes = new JLabel("Circulo / " + " Cuadrado");
        lblRes.setHorizontalAlignment(JLabel.CENTER);
        lblRes.setFont(lblRes.getFont().deriveFont(16.0f));
        lblRes.setSize(150, 30);
        lblRes.setLocation(180, 90);

        add(btnSecuencial);
        add(resetButton);
        add(btnConcurrente);
        add(limpiarTabla);
        add(lblRes);
        add(pixelField);

        MonteCarlo monteCarlo = new MonteCarlo(rectW, rectH, figura);

        btnSecuencial.addActionListener(e -> {
            if (pixelField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Ingrese un número de pixeles", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                btnSecuencial.setEnabled(false);
                btnConcurrente.setEnabled(false);
                NumHilos.setEstado("Procesando ...");

                int iteraciones = Integer.parseInt(pixelField.getText());

                // Hilo separado del EDT
                new Thread(() -> {
                    long startTime = System.currentTimeMillis(); // Tiempo de inicio
                    ArrayList<Integer> sortedNumbers = monteCarlo.dibujarSecuencial(iteraciones);
                    long endTime = System.currentTimeMillis(); // Tiempo de finalización

                    long tiempo = endTime - startTime;
                    int n = MonteCarlo.getContador();
                    double pi = 4 * ((double) n / iteraciones);
                    double tiempoSeg = tiempo / 1000.0;

                    Arreglos.mostrarArreglo(sortedNumbers);
                    resultados.resultadoSecuencial(pi, tiempoSeg);
                    lblRes.setText(n + " / " + iteraciones);
                    monteCarlo.setContador();
                    NumHilos.setListo("Listo");
                }).start();
            }
        });

        btnConcurrente.addActionListener(e -> {
            if (pixelField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Ingrese un número de pixeles", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                btnSecuencial.setEnabled(false);
                btnConcurrente.setEnabled(false);
                NumHilos.setEstado("Procesando ...");

                int iteraciones = Integer.parseInt(pixelField.getText());

                // Hilo separado del EDT
                new Thread(() -> {
                    long startTime = System.currentTimeMillis(); // Tiempo de inicio
                    ArrayList<Integer> sortedNums = MonteCarlo.dibujarConcurrente(iteraciones);
                    long endTime = System.currentTimeMillis(); // Tiempo de finalización

                    long tiempo = endTime - startTime;
                    int n = MonteCarlo.getContador();
                    double pi = 4 * ((double) n / iteraciones);
                    double tiempoSeg = tiempo / 1000.0;

                    Arreglos.mostrarArreglo(sortedNums);
                    NumHilos.setHilos(MonteCarlo.getCores());
                    resultados.resultadoConcurrente(pi, tiempoSeg);
                    lblRes.setText(n + " / " + iteraciones);
                    monteCarlo.setContador();
                    NumHilos.setListo("Listo");
                }).start();
            }
        });

        resetButton.addActionListener(e -> {
            Arreglos.limpiarTextArea();
            NumHilos.estadosReset();
            NumHilos.limpiarTabla();
            figuraObj.pintar();
            btnSecuencial.setEnabled(true);
            btnConcurrente.setEnabled(true);
            lblRes.setText("Circulo / " + " Cuadrado");
        });

        limpiarTabla.addActionListener(e -> resultados.limpiarTabla());
    }
}