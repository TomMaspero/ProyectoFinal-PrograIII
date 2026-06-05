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
    private int id;
    private String nombre;

    public Tile(BufferedImage sprite, int id, String nombre) {
        this.sprite = sprite;
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
    

    public BufferedImage getSprite(){
        return sprite;
    }
    
}
