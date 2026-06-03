/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetos;

import java.awt.image.BufferedImage;

/**
 *
 * @author lucio
 */
public class Tile {
    
    private BufferedImage sprite;

    public Tile(BufferedImage sprite) {
        this.sprite = sprite;
    }
    
    public BufferedImage getSprite(){
        return sprite;
    }
    
}
