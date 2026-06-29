package escenas;

import IU.Hotbar;
import entidades.Planta;
import helpers.CargaGuarda;
import helpers.EditorNivel;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import main.Juego;
import managers.EnemyManager;
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
    private EnemyManager enemyManager;
    private int mouseX, mouseY;

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
        
        enemyManager = new EnemyManager(this);
        //CargaGuarda.CreateFile();
        //CargaGuarda.WriteToFile();
        //CargaGuarda.ReadFromFile();
        
        createDefaultLevel();
        loadDefaultLevel();
    }
    /**
     * Carga el nivel definindo como predeterminado desde un archivo.
     */
    private void loadDefaultLevel() {
        lvl = CargaGuarda.GetLevelData("default_lvl");
    }
    /**
     * Crea un nivel predeterminado con tiles en blanco(ID = 0)
     */
    private void createDefaultLevel() {
        int[] arr = new int[45]; // Campo 5 x 9
        for(int i = 0; i < arr.length; i++){
            arr[i] = 0;
        }
        
        CargaGuarda.CreateLevel("default_lvl", arr);
    }
    /*
    En el tutorial(ep. 8 p.2) hay una seccion para guardar el nivel actual,
    pero siempre el nivel esta en blanco al empezar asi
    que no se implementa.
    */
    
    

    @Override
    public void render(Graphics g) {
        // Dibuja jardin
        g.drawImage(tileManager.getSprite(0), 0, 0, null);
 
        for (int row = 0; row < lvl.length; row++) {
            for (int col = 0; col < lvl[row].length; col++) {
                int plantaId = lvl[row][col];
                if (plantaId != 0) {
                    java.awt.image.BufferedImage spr = tileManager.getSpriteByPlantaId(plantaId);
                    int renderY = GRID_Y + row * CELL_HEIGHT + CELL_HEIGHT - spr.getHeight();
                    g.drawImage(spr, GRID_X + col * CELL_WIDTH, renderY, null);
                }
            }
        }

        // Ghost / drag preview: planta seleccionada siguiendo el cursor
        int plantaSeleccionadaId = hotbar.getSelectedPlantaId();
        if (plantaSeleccionadaId != 0) {
            java.awt.image.BufferedImage ghostSpr = tileManager.getSpriteByPlantaId(plantaSeleccionadaId);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.55f));

            int col = (mouseX - GRID_X) / CELL_WIDTH;
            int row = (mouseY - GRID_Y) / CELL_HEIGHT;
            boolean overGrid = col >= 0 && col < GRID_COLS && row >= 0 && row < GRID_ROWS;

            if (overGrid) {
                // Snapeado a la celda más cercana
                g2d.drawImage(ghostSpr,
                    GRID_X + col * CELL_WIDTH,
                    GRID_Y + row * CELL_HEIGHT,
                    CELL_WIDTH, CELL_HEIGHT,
                    null);
            } else {
                // Fuera del grid: sigue el cursor libremente (centrado en el puntero)
                g2d.drawImage(ghostSpr,
                    mouseX - CELL_WIDTH  / 2,
                    mouseY - CELL_HEIGHT / 2,
                    CELL_WIDTH, CELL_HEIGHT,
                    null);
            }

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }

        // Hotbar
        hotbar.draw(g);
        
        enemyManager.draw(g);
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
