package main;

import escenas.Ajustes;
import escenas.Jugando;
import escenas.Menu;
import javax.swing.JFrame;
import Database.*;
import dao.*;
import managers.SpriteManager;
import managers.TileManager;


/**
 * Clase principal del juego, la cual contiene variables de renderización, conexión con la base de datos,
 * el bucle principal, actualizaciones y su hilo.
 * @author Lucio
 */
public class Juego extends JFrame implements Runnable{
    private Pantalla pantallaJuego;
    private final double FPS_SET = 30.0;
    private final double UPS_SET = 60.0;
    private final DBConnect dbConnect;
    private final DBManager dbManager;

    // DAOs
    private final JugadorDAO jugadorDAO;
    private final PartidaDAO partidaDAO;
    private final PuntajeDAO puntajeDAO;
    private final ConfiguracionDAO configuracionDAO;
    private final PlantaDAO plantaDAO;
    private final EnemigoDAO enemigoDAO;

    private Thread hiloJuego;

    //Clases
    private Render render;
    private Menu menu;
    private Jugando jugando;
    private Ajustes ajustes;
    
    public Juego(){ 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setIcon();
        
        dbConnect = new DBConnect("jdbc:mysql://localhost:3306/PlantsVsZombies", "root", "");
        dbManager = new DBManager(dbConnect);

        jugadorDAO       = new JugadorDAO(dbManager);
        partidaDAO       = new PartidaDAO(dbManager);
        puntajeDAO       = new PuntajeDAO(dbManager);
        configuracionDAO = new ConfiguracionDAO(dbManager);
        plantaDAO        = new PlantaDAO(dbManager);
        enemigoDAO       = new EnemigoDAO(dbManager);

        SpriteManager.loadAll(); // Cargar todos los sprites antes de crear escenas

        initClases();
        
        
        add(pantallaJuego);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    
    

    public static void main(String[] args) {
        Juego juego = new Juego();
        juego.pantallaJuego.initInputs(); // Es un metodo de Pantalla porque se toman solamente 
                                         // las coordenadas del JPanel
        juego.start(); // Inicialización del juego dentro de un hilo.
    }

    /**
     * Contiene las variables referidas a los FPS, UPS y el bucle de juego.
     */
    @Override
    public void run() {
        double timePerFrame = 1000000000.0/ FPS_SET;
        double timePerUpdate = 1000000000.0/ UPS_SET;
        long lastFrame = System.nanoTime();
        long lastTimeCheck = System.currentTimeMillis();
        long lastUpdate = System.nanoTime();
        int frames = 0;
        int updates = 0;
        
        
        
        // Renderizado
        while (true) {
            
            long now = System.nanoTime();
            if (now - lastFrame >= timePerFrame) { // Si el tiempo entre frames es mayor o igual al establecido
                repaint(); // Redibujo la pantalla
                lastFrame = now; // Actualizo ultimo frame
                frames++;
            }
            // Actualizaciones
            if (now - lastUpdate >= timePerUpdate) {
                lastUpdate = now;
                updates++;
                updateGame(); // Por ahora se encarga de actualizar la posicion
                               // de los enemigos
            }
            // Estadisticas
            
            if (System.currentTimeMillis() - lastTimeCheck >= 1000) {
                System.out.println("FPS: " + frames + " UPS: " + updates);
                frames = 0;
                updates = 0;
                lastTimeCheck = System.currentTimeMillis();
            }

            long sleepNanos = (long) Math.min(timePerFrame - (now - lastFrame),
                                              timePerUpdate - (now - lastUpdate));
            if (sleepNanos > 1_000_000) {
                try {
                    Thread.sleep(sleepNanos / 1_000_000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } else {
                Thread.yield();
            }
        }
    }
    
    private void setIcon() {
        setIconImage(new TileManager().getIcon());
    }
    
    private void updateGame(){
        switch(EstadoJuego.estadoJuego){
                case MENU: break;
                case JUGANDO: jugando.update();break;
                case AJUSTES: break;
                default: break;
        }
    }

    private void start() {
        hiloJuego = new Thread(this){};
        hiloJuego.start();
    }
    
    // Getters y setters
    
    public Render getRender(){
        return render;
    }

    private void initClases() {
        render = new Render(this);
        pantallaJuego = new Pantalla(this);
        menu = new Menu(this);
        ajustes = new Ajustes(this);
        jugando = new Jugando(this);
    }

    public Menu getMenu() {
        return menu;
    }

    public Jugando getJugando() {
        return jugando;
    }

    public void resetJugando() {
        jugando = new Jugando(this);
    }

    public Ajustes getAjustes() {
        return ajustes;
    }

    public JugadorDAO getJugadorDAO()             { return jugadorDAO; }
    public PartidaDAO getPartidaDAO()             { return partidaDAO; }
    public PuntajeDAO getPuntajeDAO()             { return puntajeDAO; }
    public ConfiguracionDAO getConfiguracionDAO() { return configuracionDAO; }
    public PlantaDAO getPlantaDAO()               { return plantaDAO; }
    public EnemigoDAO getEnemigoDAO()             { return enemigoDAO; }
}
