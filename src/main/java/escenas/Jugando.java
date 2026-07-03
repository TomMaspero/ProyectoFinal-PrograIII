package escenas;

import IU.Hotbar;
import config.GameConfig;
import entidades.Enemigo;
import entidades.Jugador;
import entidades.Partida;
import entidades.Planta;
import entidades.Proyectil;
import entidades.Puntaje;
import entidades.TipoEnemigo;
import helpers.CargaGuarda;
import helpers.EditorNivel;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import main.Juego;
import managers.EnemyManager;
import managers.MusicManager;
import managers.TileManager;
import main.EstadoJuego;
import managers.CombatManager;
import managers.WaveManager;

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
    private CombatManager combatManager;
    private WaveManager waveManager;
    private int mouseX, mouseY;

    private int[][] fireTimers;
    private final boolean[] rowHasEnemy = new boolean[GRID_ROWS]; // reusado cada tick, evita reescanear

    // Sol
    private int sol = GameConfig.SOL_INICIAL;
    private int[][] sunTimers;
    private List<Planta> plantas;
    private BufferedImage solIcon;
    private ArrayList<FloatingText> floatingTexts = new ArrayList<>();

    private int passiveSunTimer = 0;

    // Puntos
    private int puntos = 0; // Puntuacion total
    private int plantasPerdidas = 0;
    private int zombiesEliminados = 0;
    private int zombiesEnLimite = 0;

    // Vidas
    private int vidas = GameConfig.VIDAS_INICIALES;
    private boolean derrota = false;
    private BufferedImage heartFull, heartEmpty;
    
    // puntaje
    private static final Font FONT_PUNTOS = new Font("Arial", Font.BOLD, 12);
    private boolean pidiendoNombre = false;
    private StringBuilder nombreIngresado = new StringBuilder();
    private static final int NOMBRE_MAX_LEN = 3;
    private static final Font FONT_NOMBRE = new Font("Consolas", Font.BOLD, 28);
    
    private static class FloatingText {
        int x, y, ticksLeft, totalTicks;
        String text;
        Color color;
        boolean down;
        FloatingText(int x, int y, String text, Color color, int ticks, boolean down) {
            this.x = x; this.y = y; this.text = text;
            this.color = color; this.ticksLeft = ticks;
            this.totalTicks = ticks; this.down = down;
        }
    }

    // Fonts reutilizados (evita crear objetos nuevos cada frame)
    private static final Font FONT_FT       = new Font("Arial", Font.BOLD, 10);
    private static final Font FONT_SOL      = new Font("Arial", Font.BOLD, 12);
    private static final Font FONT_DEBUG_BTN = new Font("Arial", Font.PLAIN, 9);
    private static final Font FONT_DEBUG_GRID = new Font("Arial", Font.BOLD, 7);
    private static final Font FONT_DERROTA  = new Font("Arial", Font.BOLD, 40);
    private static final Font FONT_MENU_BTN = new Font("Arial", Font.BOLD, 14);

    private static final AlphaComposite ALPHA_GHOST = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.55f);
    private static final AlphaComposite ALPHA_FULL  = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
    private static final AlphaComposite ALPHA_OVERLAY = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
    private static final AlphaComposite ALPHA_DEBUG = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.18f);

    // Debug grid overlay
    private boolean showDebugGrid = false;
    private static final Rectangle DEBUG_BTN = new Rectangle(565, 344, 70, 14);
    
    // boton derrota para volver al menu ppal
    private static final Rectangle MENU_BTN_DERROTA = new Rectangle(245, 210, 150, 35);

    // Constantes del grid — calibradas sobre yard_resize.png (640×360)
    // Área de pasto: x=112–454 (9×38px), y=42–342 (5×60px)
    private static final int GRID_X      = 112;
    private static final int GRID_Y      = 42;
    private static final int CELL_WIDTH  = 38;
    private static final int CELL_HEIGHT = 60;
    private static final int GRID_COLS   = 9;
    private static final int GRID_ROWS   = 5;
    private static final int GRID_RIGHT  = GRID_X + GRID_COLS * CELL_WIDTH;  // 454
    private static final int GRID_BOTTOM = GRID_Y + GRID_ROWS * CELL_HEIGHT; // 342

    // Tamaño de render de los sprites (75% de la celda)
    private static final int SPRITE_W = Math.round(CELL_WIDTH  * 0.8f); // 29
    private static final int SPRITE_H = Math.round(CELL_HEIGHT * 0.6f); // 45
    // Offsets para centrar horizontalmente y alinear por la base
    private static final int SPRITE_OFF_X = (CELL_WIDTH  - SPRITE_W) / 2;
    private static final int SPRITE_OFF_Y =  CELL_HEIGHT - SPRITE_H - 8;
    
    // linea de muerte de los enemigos
    private static final int DEATH_LINE_X = GRID_X - 20; // aprox 92px a la izq de la grilla

    public Jugando(Juego juego) {
        super(juego);
        lvl = EditorNivel.getLevelData();
        tileManager = new TileManager();

        plantas = juego.getPlantaDAO().findAll();
        hotbar = new Hotbar(0, 360, 640, 100, plantas, tileManager);
        List<TipoEnemigo> tiposEnemigos = juego.getEnemigoDAO().findAll();
        enemyManager = new EnemyManager(this, tiposEnemigos);
        combatManager = new CombatManager(this);
        fireTimers = new int[GRID_ROWS][GRID_COLS];
        sunTimers = new int[GRID_ROWS][GRID_COLS];
        solIcon = CargaGuarda.getSpriteAtlas("Sol.png");
        heartFull = CargaGuarda.getSpriteAtlas("heart_full.png");
        heartEmpty = CargaGuarda.getSpriteAtlas("heart_empty.png");
        waveManager = new WaveManager(this, enemyManager, GRID_Y, CELL_HEIGHT, GRID_ROWS, 640);

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
    
    public void update(){
        if (derrota) {
            return; // retornar si el juego termina
        }
        updatePlantFiring();
        updateSunGeneration();
        waveManager.update();
        combatManager.update(enemyManager.getEnemigos());
        enemyManager.update();
        checkPlantZombieCollisions();
        
        // suma el puntaje antes de remover los enemigos
        puntos += enemyManager.calcularPuntajeMuertes();
        
        // Elimina muertos y los cuenta
        zombiesEliminados += enemyManager.removeDeadEnemies(); 
        
        // resto al contador de vidas la cantidad de enemigos que hayan llegado al final
        zombiesEnLimite = enemyManager.removeEnemiesTrasPasarLimite(DEATH_LINE_X);
        vidas -= zombiesEnLimite;
        if (vidas <= 0) {
            vidas = 0;
            derrota = true;
            pidiendoNombre = true;
        }
        
        passiveSunTimer++;
        if (passiveSunTimer >= GameConfig.PASSIVE_SUN_INTERVAL) {
            passiveSunTimer = 0;
            sol += GameConfig.SOL_POR_GENERACION;
            floatingTexts.add(new FloatingText(555, 24, "+25", Color.YELLOW, 60, true));
        }
        
        floatingTexts.removeIf(ft -> --ft.ticksLeft <= 0);
    }

    private void updateSunGeneration() {
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                if (row < lvl.length && col < lvl[row].length && lvl[row][col] == 2) { // Si hay un girasol colocado
                    sunTimers[row][col]++;
                    if (sunTimers[row][col] >= GameConfig.SUN_INTERVAL) {
                        sunTimers[row][col] = 0;
                        sol += GameConfig.SOL_POR_GENERACION;
                        int tx = GRID_X + col * CELL_WIDTH;
                        int ty = GRID_Y + row * CELL_HEIGHT;
                        floatingTexts.add(new FloatingText(tx, ty, "+25", Color.YELLOW, 60, false));
                    }
                } else {
                    sunTimers[row][col] = 0;
                }
            }
        }
    }

    /** Marca en rowHasEnemy[] qué filas tienen al menos un enemigo. Una sola pasada sobre la lista. */
    private void computeRowsWithEnemies() {
        for (int r = 0; r < GRID_ROWS; r++)
            rowHasEnemy[r] = false;
        for (Enemigo e : enemyManager.getEnemigos()) {
            float cy = e.getY() + e.getColision().height / 2f;
            int row = (int) ((cy - GRID_Y) / CELL_HEIGHT);
            if (row >= 0 && row < GRID_ROWS)
                rowHasEnemy[row] = true;
        }
    }

    private void updatePlantFiring() {
        computeRowsWithEnemies();
        for (int row = 0; row < GRID_ROWS; row++) {
            boolean hasEnemy = rowHasEnemy[row];
            for (int col = 0; col < GRID_COLS; col++) {
                if (row < lvl.length && col < lvl[row].length && lvl[row][col] == 1 && hasEnemy) {
                    fireTimers[row][col]++;
                    if (fireTimers[row][col] >= GameConfig.FIRE_INTERVAL) {
                        fireTimers[row][col] = 0;
                        float spawnX = GRID_X + col * CELL_WIDTH + CELL_WIDTH;
                        float spawnY = GRID_Y + row * CELL_HEIGHT + CELL_HEIGHT / 2 - 8;
                        combatManager.spawn(spawnX, spawnY, row);
                    }
                } else {
                    fireTimers[row][col] = 0;
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        // Dibuja jardin
        g.drawImage(tileManager.getSprite(0), 0, 0, null);
 
        // Dibuja las plantas
        for (int row = 0; row < lvl.length; row++) {
            for (int col = 0; col < lvl[row].length; col++) {
                int plantaId = lvl[row][col];
                if (plantaId != 0) {
                    BufferedImage spr = tileManager.getSpriteByPlantaId(plantaId);
                    g.drawImage(spr,
                        GRID_X + col * CELL_WIDTH  + SPRITE_OFF_X,
                        GRID_Y + row * CELL_HEIGHT + SPRITE_OFF_Y,
                        SPRITE_W, SPRITE_H,
                        null);
                }
            }
        }

        // Ghost / drag preview: planta seleccionada siguiendo el cursor
        int plantaSeleccionadaId = hotbar.getSelectedPlantaId();
        if (plantaSeleccionadaId != 0) {
            BufferedImage ghostSpr = tileManager.getSpriteByPlantaId(plantaSeleccionadaId);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setComposite(ALPHA_GHOST);

            int col = (mouseX - GRID_X) / CELL_WIDTH;
            int row = (mouseY - GRID_Y) / CELL_HEIGHT;
            boolean overGrid = col >= 0 && col < GRID_COLS && row >= 0 && row < GRID_ROWS;

            if (overGrid) {
                // Snapeado a la celda más cercana, alineado por la base
                g2d.drawImage(ghostSpr,
                    GRID_X + col * CELL_WIDTH  + SPRITE_OFF_X,
                    GRID_Y + row * CELL_HEIGHT + SPRITE_OFF_Y,
                    SPRITE_W, SPRITE_H,
                    null);
            } else {
                // Fuera del grid: sigue el cursor libremente (centrado en el puntero)
                g2d.drawImage(ghostSpr,
                    mouseX - SPRITE_W / 2,
                    mouseY - SPRITE_H / 2,
                    SPRITE_W, SPRITE_H,
                    null);
            }

            g2d.setComposite(ALPHA_FULL);
        }

        // Hotbar
        hotbar.draw(g);

        enemyManager.draw(g);
        
        combatManager.draw(g);

        // Texto flotante de + 25
        g.setFont(FONT_FT);
        for (FloatingText ft : floatingTexts) {
            int elapsed = ft.totalTicks - ft.ticksLeft;
            int drawY = ft.down ? ft.y + elapsed : ft.y - elapsed;
            g.setColor(ft.color);
            g.drawString(ft.text, ft.x, drawY);
        }

        // Contador de vidas
        for (int i = 0; i < GameConfig.VIDAS_INICIALES; i++) {
            BufferedImage corazon = (i < vidas) ? heartFull : heartEmpty;
            g.drawImage(corazon, 20 + i * 18, 4, 16, 16, null);
        }
        
        // Contador de Sol
        g.drawImage(solIcon, 555, 4, 20, 20, null);
        g.setColor(Color.YELLOW);
        g.setFont(FONT_SOL);
        g.drawString(String.valueOf(sol), 578, 18);

        // Contador de puntos
        g.setFont(FONT_PUNTOS);
        g.setColor(Color.WHITE);
        String textoPuntos = "Puntos: " + puntos;
        g.drawString(textoPuntos, 550, 375);

        // Debug overlay (siempre encima de todo)
        if (showDebugGrid) drawDebugGrid(g);
        drawDebugToggle(g);

        if (derrota) {
            renderDerrota(g);
        }
    }

    private int getCostoPlanta(int plantaId) {
        for (Planta p : plantas) {
            if (p.getPlantaId() == plantaId) return p.getCostoSol();
        }
        return 0;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    @Override
    public void mouseClicked(int x, int y) { }

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
        if (derrota) {
            if (!pidiendoNombre && MENU_BTN_DERROTA.contains(x, y)) {
                EstadoJuego.SetEstadoJuego(EstadoJuego.MENU);
                MusicManager.playMenuTheme();
            }
            return;
        }
        
        if (DEBUG_BTN.contains(x, y)) {
            showDebugGrid = !showDebugGrid;
            return;
        }
        if (y >= 360) {
            hotbar.mouseReleased(x, y);
            if (EstadoJuego.estadoJuego == EstadoJuego.MENU)
                MusicManager.playMenuTheme();
        } else {
            int sel = hotbar.getSelectedPlantaId();
            if (sel != 0) {
                int col = (x - GRID_X) / CELL_WIDTH;
                int row = (y - GRID_Y) / CELL_HEIGHT;
                if (col >= 0 && col < GRID_COLS && row >= 0 && row < GRID_ROWS
                        && lvl[row][col] == 0) {
                    int costo = getCostoPlanta(sel);
                    if (sol >= costo) {
                        lvl[row][col] = sel;
                        sol -= costo;
                    } else {
                        boolean yaHayError = floatingTexts.stream()
                                .anyMatch(ft -> ft.color == Color.RED);
                        if (!yaHayError)
                            floatingTexts.add(new FloatingText(x - 40, y, "No hay suficiente Sol!", Color.RED, 90, false));
                    }
                }
            } else if (showDebugGrid && x > GRID_RIGHT && y >= GRID_Y && y <= GRID_BOTTOM) {
                enemyManager.agregaEnemigo(x, y);   
}
            // Combat manager agrega proyectil en x y 
        }
    }
    
    private void checkPlantZombieCollisions() {
        Iterator<Enemigo> it = enemyManager.getEnemigos().iterator();
        while (it.hasNext()) {
            Enemigo e = it.next();

            // Con que columna del grid interactua el zombie
            int col = (int)((e.getX() - GRID_X) / CELL_WIDTH);

            // Con que fila interactua
            int row = (int)((e.getY() + 16 - GRID_Y) / CELL_HEIGHT);
            // +16 para centrarlo

            // Bounds check
            if (col < 0 || col >= GRID_COLS || row < 0 || row >= GRID_ROWS)
                continue;

            // Si hay una planta en la celda, la elimina junto al zombie
            if (lvl[row][col] != 0) {
                lvl[row][col] = 0;   // Sacar la planta
                plantasPerdidas++;
                it.remove();          // Sacar al zombie
                zombiesEliminados++;
                puntos += e.getPuntaje(); // puntaje real segun el tipo de zombie
            }
        }
    }

    // render que se muestra cuando se le acaban las vidas al jugador
    public void renderDerrota(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setComposite(ALPHA_OVERLAY);
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, 640, 460);
        g2d.setComposite(ALPHA_FULL);

        g2d.setFont(FONT_DERROTA);
        String msg = "Derrota";
        int msgW = g2d.getFontMetrics().stringWidth(msg);
        g2d.setColor(Color.RED);
        g2d.drawString(msg, 320 - msgW / 2, 150);

        g2d.setFont(FONT_MENU_BTN);
        g2d.setColor(Color.WHITE);
        String puntosMsg = "Puntos: " + puntos;
        int pmW = g2d.getFontMetrics().stringWidth(puntosMsg);
        g2d.drawString(puntosMsg, 320 - pmW / 2, 185);

        if (pidiendoNombre) {
            String prompt = "Ingresa tus iniciales:";
            int prW = g2d.getFontMetrics().stringWidth(prompt);
            g2d.drawString(prompt, 320 - prW / 2, 215);

            StringBuilder display = new StringBuilder(nombreIngresado);
            while (display.length() < NOMBRE_MAX_LEN) display.append('_');
            g2d.setFont(FONT_NOMBRE);
            g2d.setColor(Color.YELLOW);
            String nombreStr = display.toString();
            int nW = g2d.getFontMetrics().stringWidth(nombreStr);
            g2d.drawString(nombreStr, 320 - nW / 2, 260);
        } 
        else {
            g2d.setColor(Color.DARK_GRAY);
            g2d.fillRect(MENU_BTN_DERROTA.x, MENU_BTN_DERROTA.y, MENU_BTN_DERROTA.width, MENU_BTN_DERROTA.height);
            g2d.setColor(Color.WHITE);
            g2d.drawRect(MENU_BTN_DERROTA.x, MENU_BTN_DERROTA.y, MENU_BTN_DERROTA.width, MENU_BTN_DERROTA.height);
            String btnText = "Volver al Menu";
            int btnW = g2d.getFontMetrics().stringWidth(btnText);
            g2d.drawString(btnText, MENU_BTN_DERROTA.x + (MENU_BTN_DERROTA.width - btnW) / 2, MENU_BTN_DERROTA.y + 23);
        }
    }
    
    @Override
    public void keyTyped(char c) {
        if (!pidiendoNombre) return;
        
        if (Character.isLetterOrDigit(c) && nombreIngresado.length() < NOMBRE_MAX_LEN) {
            nombreIngresado.append(Character.toUpperCase(c));
        }
        
        if (nombreIngresado.length() == NOMBRE_MAX_LEN) {
            confirmarNombre();
        }
    }

    private void confirmarNombre() {
        pidiendoNombre = false;
        guardarPartida(nombreIngresado.toString());
    }
    
    private void guardarPartida(String nombre) {
        Jugador jugador = getJuego().getJugadorDAO().findByNombre(nombre);
        int jugadorId;
        if (jugador != null) {
            jugadorId = jugador.getJugadorId();
        } else {
            jugadorId = getJuego().getJugadorDAO().save(new Jugador(nombre));
        }

        Partida partida = new Partida(jugadorId);
        partida.setOleadasSuperadas(waveManager.getOleadaActual());
        partida.setZombiesEliminados(zombiesEliminados);
        partida.setPlantasPerdidas(plantasPerdidas);
        getJuego().getPartidaDAO().save(partida);

        getJuego().getPuntajeDAO().save(new Puntaje(jugadorId, puntos));
    }

    public int getPuntos() {
        return puntos;
    }

    public boolean isPidiendoNombre() {
        return pidiendoNombre;
    }

    // ── Debug helpers ────────────────────────────────────────────────────────

    private void drawDebugToggle(Graphics g) {
        g.setColor(showDebugGrid ? new Color(200, 0, 0) : new Color(60, 60, 60));
        g.fillRect(DEBUG_BTN.x, DEBUG_BTN.y, DEBUG_BTN.width, DEBUG_BTN.height);
        g.setColor(Color.WHITE);
        g.setFont(FONT_DEBUG_BTN);
        String label = showDebugGrid ? "Debug: ON" : "Debug: OFF";
        int lw = g.getFontMetrics().stringWidth(label);
        g.drawString(label, DEBUG_BTN.x + (DEBUG_BTN.width - lw) / 2,
                            DEBUG_BTN.y + DEBUG_BTN.height - 3);
    }

    private void drawDebugGrid(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(FONT_DEBUG_GRID);

        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                int cx = GRID_X + col * CELL_WIDTH;
                int cy = GRID_Y + row * CELL_HEIGHT;

                // Semi-transparent red fill
                g2d.setComposite(ALPHA_DEBUG);
                g2d.setColor(Color.RED);
                g2d.fillRect(cx, cy, CELL_WIDTH, CELL_HEIGHT);

                // Solid red border
                g2d.setComposite(ALPHA_FULL);
                g2d.setColor(Color.RED);
                g2d.drawRect(cx, cy, CELL_WIDTH, CELL_HEIGHT);

                // Label: (col,row) on top, lvl value below
                g2d.setColor(Color.YELLOW);
                String pos = col + "," + row;
                int pw = g2d.getFontMetrics().stringWidth(pos);
                g2d.drawString(pos, cx + (CELL_WIDTH - pw) / 2, cy + 8);

                if (lvl != null && row < lvl.length && col < lvl[row].length) {
                    String val = "id:" + lvl[row][col];
                    int vw = g2d.getFontMetrics().stringWidth(val);
                    g2d.setColor(Color.WHITE);
                    g2d.drawString(val, cx + (CELL_WIDTH - vw) / 2, cy + CELL_HEIGHT - 2);
                }
            }
        }

        // Grid origin marker
        g2d.setColor(Color.CYAN);
        g2d.fillOval(GRID_X - 3, GRID_Y - 3, 6, 6);
        g2d.setColor(Color.CYAN);
        g2d.drawString("(" + GRID_X + "," + GRID_Y + ")", GRID_X + 5, GRID_Y - 2);
        
        // linea limite de zombies
        g2d.setComposite(ALPHA_FULL);
        g2d.setColor(Color.BLUE);
        g2d.drawLine(DEATH_LINE_X, GRID_Y, DEATH_LINE_X, GRID_BOTTOM);

        // Cajas de colision de enemigos y proyectiles
        g2d.setColor(Color.GREEN);
        for (Enemigo e : enemyManager.getEnemigos()) {
            Rectangle c = e.getColision();
            g2d.drawRect(c.x, c.y, c.width, c.height);
        }

        g2d.setColor(Color.MAGENTA);
        for (Proyectil p : combatManager.getProyectiles()) {
            Rectangle c = p.getColision();
            g2d.drawRect(c.x, c.y, c.width, c.height);
        }
    }
}
