package escenas;

import IU.Hotbar;
import entidades.Planta;
import helpers.EditorNivel;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import main.Juego;
import managers.TileManager;

/**
 * Escena principal del juego.
 * Maneja el grid de tiles, la hotbar de plantas y la colocación de plantas.
 */
public class Jugando extends EscenaJuego implements MetodosEscena {

    // Grid: 0 = vacío, valor > 0 = plantaId de la planta colocada
    private int[][] lvl;
    private TileManager tileManager;
    private Hotbar hotbar;
    // Constantes del grid
    private static final int GRID_X      = 78;
    private static final int GRID_Y      = 19;
    private static final int CELL_WIDTH  = 29;
    private static final int CELL_HEIGHT = 31;
    private static final int GRID_COLS   = 8;
    private static final int GRID_ROWS   = 4;

    public Jugando(Juego juego) {
        super(juego);
        lvl = EditorNivel.getLevelData();
        tileManager = new TileManager();

        List<Planta> plantas = juego.getPlantaDAO().findAll();
        hotbar = new Hotbar(0, 360, 640, 100, plantas, tileManager);
    }

    @Override
    public void render(Graphics g) {
        // Fondo y pasto
        g.drawImage(tileManager.getSprite(2), 0, 0, null);
        g.drawImage(tileManager.getSprite(3), 76, 17, null);

        for (int row = 0; row < lvl.length; row++) {
            for (int col = 0; col < lvl[row].length; col++) {
                int plantaId = lvl[row][col];
                if (plantaId != 0) {
                    g.drawImage(
                        tileManager.getSpriteByPlantaId(plantaId),
                        GRID_X + col * CELL_WIDTH,
                        GRID_Y + row * CELL_HEIGHT,
                        null
                    );
                }
            }
        }
        // Hotbar
        hotbar.draw(g);
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (y >= 360) {
            hotbar.mouseClicked(x, y);
        } else {
            // Intentar colocar planta en el grid
            int sel = hotbar.getSelectedPlantaId();
            if (sel != 0) {
                int col = (x - GRID_X) / CELL_WIDTH;
                int row = (y - GRID_Y) / CELL_HEIGHT;
                if (col >= 0 && col < GRID_COLS && row >= 0 && row < GRID_ROWS
                        && lvl[row][col] == 0) {
                    lvl[row][col] = sel;
                }
            }
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        mouseX = x;
        mouseY = y;
        if (y >= 360) {
            hotbar.mouseMoved(x, y);
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if (y >= 360) {
            hotbar.mousePressed(x, y);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        hotbar.mouseReleased(x, y);
    }
}
