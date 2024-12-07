package Logic.Graphics;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

public class Figura extends JPanel {
    private final int rectW, rectH;

    public Figura(int rectW, int rectH) {
        setSize(rectW + 1,  rectH + 1);

        this.rectW = rectW;
        this.rectH = rectH;
    }

    @Override
    public void paint(Graphics g) {
        int rectX = 0;
        int rectY = 0;
        g.setColor(Color.WHITE);
        g.fillRect(rectX, rectY, rectW, rectH);
        g.setColor(Color.BLACK);
        g.drawRect(rectX, rectY, rectW, rectH);
        g.drawOval(rectX, rectY, rectW, rectH);
    }

    public void pintar() {
        repaint();
    }
}
