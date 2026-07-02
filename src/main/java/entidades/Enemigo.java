/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.awt.Rectangle;

/**
 *
 * @author lucio
 */
public class Enemigo extends Entidad {
    private int vida;
    private int ID;
    private int tipoEnemigo;
    
    public Enemigo(float x, float y, int id, int tipoEnemigo){
        super(x, y, 32); // el ultimo se setea segun el tamanio del sprite
        this.ID = id;
        this.tipoEnemigo = tipoEnemigo;
        this.vida = 100;
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
