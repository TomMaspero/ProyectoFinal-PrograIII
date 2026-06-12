 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.awt.Graphics;

/**
 * Se encarga del renderizado principal del juego.
 * @param juego Se pasa el juego como parametro. Luego se utiliza para indicar sus escenas.
 */
public class Render {
    private Juego juego;
    
    public Render(Juego juego){
        this.juego = juego;
    }
    /**
     * Se utiliza para renderizar las escenas del juego segun lo definido en la clase {@link main.EstadoJuego}
     * 
     */
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
}
