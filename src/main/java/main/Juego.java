package main;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Juego extends JFrame{
    private Pantalla pantallaJuego;
    
    private BufferedImage img;
    
    public Juego(){
        importImg();
        
        setSize(640,640);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        pantallaJuego = new Pantalla(img);
        add(pantallaJuego);
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
    }

    
}
