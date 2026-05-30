/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static java.lang.System.currentTimeMillis;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Lucio
 */
public class Pantalla extends JPanel {

    //private Random random
    private BufferedImage img;
    private ArrayList<BufferedImage> sprites = new ArrayList<>();
    
    
    public Pantalla(BufferedImage img){
        this.img = img;
        
        loadSprites();
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        //g.drawImage(sprites.get(0), 0, 0, null);
        
        for(int x = 0;x<8;x++){
            g.drawImage(sprites.get(x), x*28, 0, null);
            
        }
        repaint();
    }
    
    // sprite peashooter en 169,13 en getSubimage
    private void loadSprites() {
        
            for(int x=0;x<8;x++){
                sprites.add(img.getSubimage(169+x*29,13, 28, 32)); // Hay que rehacer la imagen de sprites
                                                                    // para que esten alineados, asi se pueden recorrer
            }
        
    }
    
}
