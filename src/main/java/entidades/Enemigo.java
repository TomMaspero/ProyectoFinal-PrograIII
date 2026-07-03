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
    private int enemigoId;
    private int puntaje;
    
    public Enemigo(float x, float y, TipoEnemigo tipoEnemigo){
        super(x, y, 32); // el ultimo se setea segun el tamanio del sprite
        this.enemigoId = tipoEnemigo.getEnemigoId();
        this.vida = tipoEnemigo.getVida();
        this.puntaje = tipoEnemigo.getPuntaje();
    }
    
    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getEnemigoId() {
        return enemigoId;
    }

    public int getPuntaje() {
        return puntaje;
    }
}
