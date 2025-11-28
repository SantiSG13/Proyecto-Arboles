package arboles;

import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class ArbolGrafico extends JFrame {
    private Arbol arbol;

    // Constructor que recibe el árbol a mostrar
    public ArbolGrafico(Arbol arbol) {
        super("Arbol binario grafico");
        this.arbol = arbol;
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    // Dibuja el árbol usando el Nodo del proyecto
    public int drawTree(Graphics g, Nodo x, int x0, int x1, int y) {
        if (x == null)
            return (x0 + x1) / 2;

        int m = (x0 + x1) / 2;
        g.setColor(Color.BLUE);
        g.fillOval(m, y, 50, 40);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        String t = String.valueOf(x.getDato());
        g.drawString(t, m + 18, y + 27);

        if (x.getLigaI() != null) {
            int x2 = drawTree(g, x.getLigaI(), x0, m, y + 80);
            g.setColor(Color.BLACK);
            g.drawLine(m + 25, y + 40, x2 + 25, y + 80);
        }
        if (x.getLigaD() != null) {
            int x2 = drawTree(g, x.getLigaD(), m, x1, y + 80);
            g.setColor(Color.BLACK);
            g.drawLine(m + 25, y + 40, x2 + 25, y + 80);
        }
        return m;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (arbol == null || arbol.getRaiz() == null) {
            g.setColor(Color.BLACK);
            g.drawString("(árbol vacío)", 20, 100);
            return;
        }
        drawTree(g, arbol.getRaiz(), 20, this.getWidth() - 45, 100);
    }
}
