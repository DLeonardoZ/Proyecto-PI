package UI;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Font;

public class NumHilos extends JPanel {
    static JLabel lblEstado;
    static JLabel lblTabla;

    static DefaultTableModel model = new DefaultTableModel(new Object[]{
            "Hilo", "Estado"}, 0);

    public NumHilos() {
        setSize(400, 300);
        setBackground(Color.WHITE);
        setLayout(null);

        lblEstado = new JLabel("Estado : N/A");
        lblEstado.setHorizontalAlignment(JLabel.CENTER);
        lblEstado.setFont(lblEstado.getFont().deriveFont(16.0f));
        lblEstado.setSize(400, 30);
        lblEstado.setLocation(0, 10);

        lblTabla = new JLabel("Tabla de estado de los hilos");
        lblTabla.setHorizontalAlignment(JLabel.CENTER);
        lblTabla.setFont(lblTabla.getFont().deriveFont(16.0f));
        lblTabla.setSize(400, 30);
        lblTabla.setLocation(0, 40);

        JTable tablaHilos = new JTable(model);
        tablaHilos.setFont(new Font("Arial", Font.PLAIN, 16));
        tablaHilos.setRowHeight(30);
        tablaHilos.setSize(400, 200);
        tablaHilos.setLocation(0, 80);

        JScrollPane scrollPane = new JScrollPane(tablaHilos);
        scrollPane.setFont(lblEstado.getFont().deriveFont(16.0f));
        scrollPane.setSize(400, 200);
        scrollPane.setLocation(0, 80);

        add(lblEstado);
        add(lblTabla);
        add(scrollPane); // tablaHilos con scroll
    }

    public static void addTablaHilo(int id, String estado) {
        model.addRow(new Object[]{ id, estado });
    }

    public static void updateTablaHilo(int id, String estado) {
        for (int i = 0; i < model.getRowCount(); i++) {
            if ((int) model.getValueAt(i, 0) == id) {
                model.setValueAt(estado, i, 1);
                break;
            }
        }
    }

    public static void limpiarTabla() {
        model.setRowCount(0);
    }

    public static void setEstado(String estado) {
        lblEstado.setForeground(Color.ORANGE);
        lblEstado.setText("Estado : " + estado);
    }

    public static void setListo(String estado) {
        lblEstado.setForeground(Color.GREEN);
        lblEstado.setText("Estado : " + estado);
    }

    public static void estadosReset() {
        lblEstado.setForeground(Color.BLACK);
        lblTabla.setText("Tabla de estado de los hilos");
        lblEstado.setText("Estado : N/A");
    }

    public static void setHilos(int hilos) {
        lblTabla.setText("Hilos : " + hilos);
    }
}