 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author lucio
 */
public class Render {
    private Juego juego;
        private BufferedImage img;
    private ArrayList<BufferedImage> sprites = new ArrayList<>();
    
    public Render(Juego juego){
        this.juego = juego;
        importImg();
        loadSprites();
    }
    
    public void render(Graphics g){
        switch(EstadoJuego.estadoJuego){
            case MENU:
                juego.getMenu().render(g);
                break;
            case AJUSTES:
                juego.getAjustes().render(g);
                break;
            case JUGANDO:
                juego.getJugando().render(g);
                break;
        }
    }
    
    private void loadSprites() {
        
            for(int x=0;x<8;x++){
                sprites.add(img.getSubimage(169+x*29,13, 28, 32)); // Hay que rehacer la imagen de sprites
                                                                    // para que esten alineados, asi se pueden recorrer
            }
        
    }
    
    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/pea.png");
        
        try{
            img = ImageIO.read(is);
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    
    
}
