package arboles;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.BasicStroke;
import java.awt.RenderingHints;
import java.awt.GradientPaint;

public class ArbolGrafico extends JFrame {
    private final Arbol arbol;
    private static final int NODE_RADIUS = 26;
    private static final int LEVEL_GAP = 90;
    private static final int MARGIN = 40;

    public ArbolGrafico(Arbol arbol) {
        super("Árbol binario gráfico");
        this.arbol = arbol;
        setSize(820, 620);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(new Lienzo());
        setVisible(true);
    }

    private class Lienzo extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();

            // Fondo degradado sutil
            GradientPaint fondo = new GradientPaint(
                    0, 0, new Color(245, 248, 252),
                    0, getHeight(), new Color(225, 234, 244));
            g2.setPaint(fondo);
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            if (arbol == null || arbol.getRaiz() == null) {
                g2.setColor(new Color(90, 90, 90));
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 18));
                g2.drawString("(árbol vacío)", MARGIN, 90);
            } else {
                drawTree(g2, arbol.getRaiz(), MARGIN, getWidth() - MARGIN, 80);
            }
            g2.dispose();
        }

        private int drawTree(Graphics2D g2, Nodo nodo, int x0, int x1, int y) {
            if (nodo == null) {
                return (x0 + x1) / 2;
            }

            int centroX = (x0 + x1) / 2;

            // Conexiones antes de dibujar el nodo para que queden “debajo”
            if (nodo.getLigaI() != null) {
                int hijoX = drawTree(g2, nodo.getLigaI(), x0, centroX, y + LEVEL_GAP);
                drawConnection(g2, centroX, y, hijoX, y + LEVEL_GAP);
            }
            if (nodo.getLigaD() != null) {
                int hijoX = drawTree(g2, nodo.getLigaD(), centroX, x1, y + LEVEL_GAP);
                drawConnection(g2, centroX, y, hijoX, y + LEVEL_GAP);
            }

            drawNode(g2, centroX, y, nodo.getDato());
            return centroX;
        }

        private void drawConnection(Graphics2D g2, int fromX, int fromY, int toX, int toY) {
            double dx = toX - fromX;
            double dy = toY - fromY;
            double distancia = Math.hypot(dx, dy);

            if (distancia < 1e-3) return;

            double offsetX = dx * NODE_RADIUS / distancia;
            double offsetY = dy * NODE_RADIUS / distancia;

            int inicioX = (int) Math.round(fromX + offsetX);
            int inicioY = (int) Math.round(fromY + offsetY);
            int finX = (int) Math.round(toX - offsetX);
            int finY = (int) Math.round(toY - offsetY);

            g2.setColor(new Color(160, 174, 191));
            g2.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.drawLine(inicioX, inicioY, finX, finY);
        }

        private void drawNode(Graphics2D g2, int x, int y, char dato) {
            // Sombra
            g2.setColor(new Color(0, 0, 0, 40));
            g2.fillOval(x - NODE_RADIUS + 3, y - NODE_RADIUS + 3, NODE_RADIUS * 2, NODE_RADIUS * 2);

            // Círculo principal
            GradientPaint nodoPaint = new GradientPaint(
                    x, y - NODE_RADIUS, new Color(78, 140, 255),
                    x, y + NODE_RADIUS, new Color(52, 101, 209)
            );
            g2.setPaint(nodoPaint);
            g2.fillOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);

            // Borde sutil
            g2.setColor(new Color(255, 255, 255, 80));
            g2.setStroke(new BasicStroke(1.6f));
            g2.drawOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);

            // Texto centrado
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 18));
            String texto = String.valueOf(dato);
            int anchoTexto = g2.getFontMetrics().stringWidth(texto);
            int altoTexto = g2.getFontMetrics().getAscent();
            g2.drawString(texto, x - anchoTexto / 2, y + altoTexto / 3);
        }
    }
}