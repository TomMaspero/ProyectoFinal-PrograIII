package main;

import escenas.Ajustes;
import escenas.Jugando;
import escenas.Menu;
import inputs.KeyboardListener;
import inputs.MyMouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import Database.*;
import dao.*;



public class Juego extends JFrame implements Runnable{
    private Pantalla pantallaJuego;
    private final double FPS_SET = 60.0;
    private final double UPS_SET = 60.0;
    private final DBConnect dbConnect;
    private final DBManager dbManager;
    private Thread hiloJuego;
    //Clases
    private Render render;
    private Menu menu;
    private Jugando jugando;
    private Ajustes ajustes;
    
    public Juego(){ 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        dbConnect = new DBConnect("jdbc:mysql://localhost:3306/PlantsVsZombies", "root", "");
        dbManager = new DBManager(dbConnect);
        initClases();
        
         
        add(pantallaJuego);
        pack();
        
        setVisible(true);
    }
    
    
    

    public static void main(String[] args) {
        Juego juego = new Juego();
        juego.pantallaJuego.initInputs(); // Es un metodo de Pantalla porque se toman solamente 
                                            // las coordenadas del JPanel
        juego.start();
    }


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
            }
            // Estadisticas
            
            if (System.currentTimeMillis() - lastTimeCheck >= 1000) {
                System.out.println("FPS: " + frames + "UPS: " + updates);
                frames = 0;
                updates = 0;
                lastTimeCheck = System.currentTimeMillis();
            }
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

    public Ajustes getAjustes() {
        return ajustes;
    }
    
}
