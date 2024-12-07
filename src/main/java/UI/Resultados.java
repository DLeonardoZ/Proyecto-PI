package UI;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Font;
import javax.swing.table.DefaultTableModel;

public class Resultados extends JPanel {
    DefaultTableModel model1 = new DefaultTableModel(new Object[]{
            "Valor de PI", "Tiempo"}, 0);

    DefaultTableModel model2 = new DefaultTableModel(new Object[]{
            "Valor de PI", "Tiempo"}, 0);

    public Resultados() {
        setSize(800, 270);
        setBackground(Color.white);
        setLayout(null);

        JLabel lblResultados = new JLabel("Resultado Secuencial / Concurrente");
        lblResultados.setHorizontalAlignment(JLabel.CENTER);
        lblResultados.setFont(lblResultados.getFont().deriveFont(16.0f));
        lblResultados.setSize(800, 30);
        lblResultados.setLocation(0, 10);

        JTable tablaSecuencial = new JTable(model1);
        tablaSecuencial.setFont(new Font("Arial", Font.PLAIN, 16));
        tablaSecuencial.setRowHeight(30);
        tablaSecuencial.setSize(400, 200);
        tablaSecuencial.setLocation(0, 50);

        JTable tablaConcurrente = new JTable(model2);
        tablaConcurrente.setFont(new Font("Arial", Font.PLAIN, 16));
        tablaConcurrente.setRowHeight(30);
        tablaConcurrente.setSize(400, 200);
        tablaConcurrente.setLocation(400, 50);

        JScrollPane scrollTablaSecuencial = new JScrollPane(tablaSecuencial);
        scrollTablaSecuencial.setFont(new Font("Arial", Font.PLAIN, 16));
        scrollTablaSecuencial.setSize(400, 200);
        scrollTablaSecuencial.setLocation(0, 50);

        JScrollPane scrollTablaConcurrente = new JScrollPane(tablaConcurrente);
        scrollTablaConcurrente.setSize(400, 200);
        scrollTablaConcurrente.setLocation(400, 50);

        add(lblResultados);
        add(scrollTablaSecuencial);
        add(scrollTablaConcurrente);
    }

    public void resultadoSecuencial(double valorPi, double tiempo) {
        model1.addRow(new Object[]{ valorPi, tiempo });
    }

    public void resultadoConcurrente(double valorPi, double tiempo) {
        model2.addRow(new Object[]{ valorPi, tiempo });
    }

    public void limpiarTabla() {
        model1.setRowCount(0);
        model2.setRowCount(0);
    }
}