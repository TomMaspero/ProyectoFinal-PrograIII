/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package enemigos;

import java.awt.Rectangle;

/**
 *
 * @author lucio
 */
public class Enemigo {
    
    private float x, y; // Float es preciso para especificar velocidad de movimiento
    private Rectangle colision; 
    private int vida;
    private int ID;
    private int tipoEnemigo;
    
    public Enemigo(float x, float y, int id, int tipoEnemigo){
        this.x = x;
        this.y = y;
        this.ID = id;
        this.tipoEnemigo = tipoEnemigo;
        colision = new Rectangle((int) x,(int) y, 32, 32); // Los ultimos 2 hay q setearlos segun el tamaño del sprite
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Rectangle getColision() {
        return colision;
    }

    public void setColision(Rectangle colision) {
        this.colision = colision;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getTipoEnemigo() {
        return tipoEnemigo;
    }

    public void setTipoEnemigo(int tipoEnemigo) {
        this.tipoEnemigo = tipoEnemigo;
    }
    
    
    
}
