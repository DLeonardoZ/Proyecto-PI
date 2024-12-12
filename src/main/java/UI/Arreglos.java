package UI;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.util.ArrayList;

public class Arreglos extends JPanel {
    static JTextArea txtArreglo;

    public Arreglos() {
        setSize(430, 270);
        setBackground(Color.white);
        setLayout(null);

        txtArreglo = new JTextArea();
        txtArreglo.setEditable(false);
        txtArreglo.setFont(txtArreglo.getFont().deriveFont(16.0f));

        JScrollPane scrollPane = new JScrollPane(txtArreglo);
        scrollPane.setSize(430, 270);
        scrollPane.setLocation(0, 10);

        add(scrollPane);
    }

    public static void mostrarArreglo(ArrayList<Integer> sortedNumbers) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < sortedNumbers.size(); i++) {
            sb.append("[").append(i + 1).append("] = ").append(sortedNumbers.get(i)).append("\n");
        }
        txtArreglo.setText(sb.toString());
    }

    public static void sumarArreglo(ArrayList<Integer> sortedNumbers) {
        StringBuilder sb = new StringBuilder(txtArreglo.getText());

        for (int i = 0; i < sortedNumbers.size(); i++) {
            sb.append("[").append(i + 1).append("] = ").append(sortedNumbers.get(i)).append("\n");
        }
        txtArreglo.setText(sb.toString());
    }

    public static void limpiarTextArea() {
        txtArreglo.setText("");
    }
}