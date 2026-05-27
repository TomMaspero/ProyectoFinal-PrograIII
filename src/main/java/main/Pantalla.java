/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Lucio
 */
public class Pantalla extends JPanel {

    private BufferedImage img;
    
    private ArrayList<BufferedImage> sprites = new ArrayList<>();
    
    public Pantalla(BufferedImage img){
        this.img = img;
        
        loadSprites();
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        g.drawImage(sprites.get(1), 0, 0, null);
        /*
        g.setColor(Color.RED);
        for(int x = 0;x<32;x++){
        g.fillRect(x * 32, 0, 32, 32);
        }
        */
    }
    // sprite peashooter en 169,13 en getSubimage
    private void loadSprites() {
        for(int y=0;y<10;y++){
            for(int x=0;x<10;x++){
                sprites.add(img.getSubimage(x * 1, y * 1, 28, 32)); // Hay que rehacer la imagen de sprites
                                                                    // para que esten alineados, asi se pueden recorrer
            }
        }
    }
    
}
