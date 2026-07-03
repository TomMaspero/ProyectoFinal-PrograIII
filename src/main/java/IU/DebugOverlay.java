package IU;

import entidades.Enemigo;
import entidades.Proyectil;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * Overlay de depuracion: boton de encendido/apagado, grilla con IDs, cajas de
 * colision de enemigos/proyectiles y la linea de muerte. No conoce a Jugando —
 * recibe todo lo que necesita dibujar como parametros.
 */
public class DebugOverlay {

    private static final Rectangle DEBUG_BTN = new Rectangle(565, 344, 70, 14);
    private static final Font FONT_DEBUG_BTN  = new Font("Arial", Font.PLAIN, 9);
    private static final Font FONT_DEBUG_GRID = new Font("Arial", Font.BOLD, 7);
    private static final AlphaComposite ALPHA_DEBUG = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.18f);
    private static final AlphaComposite ALPHA_FULL  = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);

    private final int gridX, gridY, cellWidth, cellHeight, gridCols, gridRows, deathLineX, gridBottom;
    private boolean activo = false;

    public DebugOverlay(int gridX, int gridY, int cellWidth, int cellHeight,
                         int gridCols, int gridRows, int deathLineX, int gridBottom) {
        this.gridX = gridX;
        this.gridY = gridY;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
        this.gridCols = gridCols;
        this.gridRows = gridRows;
        this.deathLineX = deathLineX;
        this.gridBottom = gridBottom;
    }

    public boolean isActivo() {
        return activo;
    }

    /** Si el click cae en el boton, alterna el overlay y devuelve true (click consumido). */
    public boolean toggle(int x, int y) {
        if (DEBUG_BTN.contains(x, y)) {
            activo = !activo;
            return true;
        }
        return false;
    }

    public void render(Graphics g, int[][] lvl, ArrayList<Enemigo> enemigos, ArrayList<Proyectil> proyectiles) {
        if (activo) {
            drawGrid(g, lvl, enemigos, proyectiles);
        }
        drawToggleButton(g);
    }

    private void drawToggleButton(Graphics g) {
        g.setColor(activo ? new Color(200, 0, 0) : new Color(60, 60, 60));
        g.fillRect(DEBUG_BTN.x, DEBUG_BTN.y, DEBUG_BTN.width, DEBUG_BTN.height);
        g.setColor(Color.WHITE);
        g.setFont(FONT_DEBUG_BTN);
        String label = activo ? "Debug: ON" : "Debug: OFF";
        int lw = g.getFontMetrics().stringWidth(label);
        g.drawString(label, DEBUG_BTN.x + (DEBUG_BTN.width - lw) / 2,
                            DEBUG_BTN.y + DEBUG_BTN.height - 3);
    }

    private void drawGrid(Graphics g, int[][] lvl, ArrayList<Enemigo> enemigos, ArrayList<Proyectil> proyectiles) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(FONT_DEBUG_GRID);

        for (int row = 0; row < gridRows; row++) {
            for (int col = 0; col < gridCols; col++) {
                int cx = gridX + col * cellWidth;
                int cy = gridY + row * cellHeight;

                g2d.setComposite(ALPHA_DEBUG);
                g2d.setColor(Color.RED);
                g2d.fillRect(cx, cy, cellWidth, cellHeight);

                g2d.setComposite(ALPHA_FULL);
                g2d.setColor(Color.RED);
                g2d.drawRect(cx, cy, cellWidth, cellHeight);

                g2d.setColor(Color.YELLOW);
                String pos = col + "," + row;
                int pw = g2d.getFontMetrics().stringWidth(pos);
                g2d.drawString(pos, cx + (cellWidth - pw) / 2, cy + 8);

                if (lvl != null && row < lvl.length && col < lvl[row].length) {
                    String val = "id:" + lvl[row][col];
                    int vw = g2d.getFontMetrics().stringWidth(val);
                    g2d.setColor(Color.WHITE);
                    g2d.drawString(val, cx + (cellWidth - vw) / 2, cy + cellHeight - 2);
                }
            }
        }

        g2d.setColor(Color.CYAN);
        g2d.fillOval(gridX - 3, gridY - 3, 6, 6);
        g2d.drawString("(" + gridX + "," + gridY + ")", gridX + 5, gridY - 2);

        g2d.setComposite(ALPHA_FULL);
        g2d.setColor(Color.BLUE);
        g2d.drawLine(deathLineX, gridY, deathLineX, gridBottom);

        g2d.setColor(Color.GREEN);
        for (Enemigo e : enemigos) {
            Rectangle c = e.getColision();
            g2d.drawRect(c.x, c.y, c.width, c.height);
        }

        g2d.setColor(Color.MAGENTA);
        for (Proyectil p : proyectiles) {
            Rectangle c = p.getColision();
            g2d.drawRect(c.x, c.y, c.width, c.height);
        }
    }
}
