package IU;

import entidades.Planta;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import managers.TileManager;
import static main.EstadoJuego.*;
import static main.EstadoJuego.SetEstadoJuego;

/**
 * Barra inferior de la pantalla de juego.
 * Muestra un botón por cada planta cargada desde la base de datos.
 */
public class Hotbar {
    private static final java.awt.Font FONT_COST = new java.awt.Font("Arial", java.awt.Font.PLAIN, 9);
    private static final Color COLOR_COST = new Color(80, 80, 80);

    private int x, y, width, height;
    private MyButton bMenu;
    private ArrayList<MyButton> plantButtons = new ArrayList<>();
    private int selectedPlantaId = 0; // 0 = ninguna seleccionada
    private List<Planta> plantas;

    public Hotbar(int x, int y, int width, int height, List<Planta> plantas, TileManager tileManager) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.plantas = plantas;
        initButtons(plantas, tileManager);
    }

    private void initButtons(List<Planta> plantas, TileManager tileManager) {
        bMenu = new MyButton("Menu", 2, y, 100, 30);

        int btnW = 50;
        int btnH = 50;
        int xStart = 110;
        int xOffset = (int)(btnW * 1.1f);

        int i = 0;
        for (Planta p : plantas) {
            plantButtons.add(new MyButton(
                p.getNombre(),
                p.getPlantaId(),
                tileManager.getSpriteByPlantaId(p.getPlantaId()),
                xStart + xOffset * i,
                y + (height - btnH) / 2,
                btnW,
                btnH
            ));
            i++;
        }
    }

    public void draw(Graphics g) {
        // Fondo
        g.setColor(Color.ORANGE);
        g.fillRect(x, y, width, height);

        // Resaltar botón seleccionado
        if (selectedPlantaId != 0) {
            for (MyButton b : plantButtons) {
                if (b.getId() == selectedPlantaId) {
                    g.setColor(Color.YELLOW);
                    g.fillRect(b.getBounds().x - 2, b.getBounds().y - 2,
                               b.getBounds().width + 4, b.getBounds().height + 4);
                }
            }
        }

        bMenu.draw(g);
        for (MyButton b : plantButtons) {
            b.draw(g);
        }

        // Cost labels
        g.setFont(FONT_COST);
        g.setColor(COLOR_COST);
        for (MyButton b : plantButtons) {
            for (Planta p : plantas) {
                if (p.getPlantaId() == b.getId()) {
                    String cost = String.valueOf(p.getCostoSol());
                    int cw = g.getFontMetrics().stringWidth(cost);
                    g.drawString(cost,
                        b.getBounds().x + (b.getBounds().width - cw) / 2,
                        b.getBounds().y + b.getBounds().height + 10);
                    break;
                }
            }
        }
    }

    public void mouseClicked(int x, int y) { }

    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        for (MyButton b : plantButtons) {
            b.setMouseOver(false);
        }

        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        } else {
            for (MyButton b : plantButtons) {
                if (b.getBounds().contains(x, y)) {
                    b.setMouseOver(true);
                    return;
                }
            }
        }
    }

    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMousePressed(true);
        } else {
            for (MyButton b : plantButtons) {
                if (b.getBounds().contains(x, y)) {
                    b.setMousePressed(true);
                    return;
                }
            }
        }
    }

    public void mouseReleased(int x, int y) {
        if (bMenu.isMousePressed() && bMenu.getBounds().contains(x, y))
            SetEstadoJuego(MENU);

        for (MyButton b : plantButtons) {
            if (b.isMousePressed() && b.getBounds().contains(x, y))
                selectedPlantaId = (selectedPlantaId == b.getId()) ? 0 : b.getId();
        }

        bMenu.resetBooleans();
        for (MyButton b : plantButtons) b.resetBooleans();
    }

    /** Devuelve el plantaId seleccionado actualmente (0 = ninguno). */
    public int getSelectedPlantaId() {
        return selectedPlantaId;
    }
}
