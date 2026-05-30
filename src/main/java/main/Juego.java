package main;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Juego extends JFrame implements Runnable{
    private Pantalla pantallaJuego;
    private final double FPS_SET = 60.0;
    private final double UPS_SET = 60.0;
    private BufferedImage img;
    
    private Thread hiloJuego;
    
    public Juego(){ 
        importImg();
        
        setSize(640,480);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        pantallaJuego = new Pantalla(img);
        add(pantallaJuego);
        setVisible(true);
    }
    
    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/pea.png");
        
        try{
            img = ImageIO.read(is);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Juego juego = new Juego();
        
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

    
}
