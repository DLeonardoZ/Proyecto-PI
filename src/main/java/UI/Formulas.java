package UI;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;

public class Formulas extends JPanel {

    public Formulas() {
        setSize(350, 130);
        setBackground(Color.white);
        setLayout(null);

        JLabel lblCirculo = new JLabel("Superficie del circulo = PI * R^2");
        lblCirculo.setHorizontalAlignment(JLabel.CENTER);
        lblCirculo.setFont(lblCirculo.getFont().deriveFont(16.0f));
        lblCirculo.setSize(350, 30);
        lblCirculo.setLocation(15, 10);

        JLabel lblCuadrado = new JLabel("Superficie del cuadrado = 4R^2");
        lblCuadrado.setHorizontalAlignment(JLabel.CENTER);
        lblCuadrado.setFont(lblCirculo.getFont().deriveFont(16.0f));
        lblCuadrado.setSize(350, 30);
        lblCuadrado.setLocation(15, 50);

        JLabel lblResultado = new JLabel("PI = 4 * (Pts circulo) / (Pts cuadrado)");
        lblResultado.setHorizontalAlignment(JLabel.CENTER);
        lblResultado.setFont(lblCirculo.getFont().deriveFont(16.0f));
        lblResultado.setSize(350, 30);
        lblResultado.setLocation(15, 90);

        add(lblCirculo);
        add(lblCuadrado);
        add(lblResultado);
    }
}