package UIControles;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Font;
import javax.swing.table.DefaultTableModel;

public class Paralela extends JPanel {
    DefaultTableModel model = new DefaultTableModel(new Object[]{
            "Valor de PI", "Tiempo"}, 0);

    public Paralela() {
        setSize(430, 140);
        setBackground(Color.white);
        setLayout(null);

        JTable tablaSecuencial = new JTable(model);
        tablaSecuencial.setFont(new Font("Arial", Font.PLAIN, 16));
        tablaSecuencial.setRowHeight(30);
        tablaSecuencial.setSize(430, 130);
        tablaSecuencial.setLocation(0, 10);

        JScrollPane scrollTablaSecuencial = new JScrollPane(tablaSecuencial);
        scrollTablaSecuencial.setFont(new Font("Arial", Font.PLAIN, 16));
        scrollTablaSecuencial.setSize(430, 130);
        scrollTablaSecuencial.setLocation(0, 10);

        add(scrollTablaSecuencial);
    }

    public void resultadoSecuencial(double valorPi, double tiempo) {
        model.addRow(new Object[]{ valorPi, tiempo });
    }

    public void limpiarTabla() {
        model.setRowCount(0);
    }
}