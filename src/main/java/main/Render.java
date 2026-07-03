 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import static java.awt.SystemColor.menu;

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
        Graphics2D g2d = (Graphics2D) g.create();
        try {
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            g2d.scale(2.0, 2.0);
            switch(EstadoJuego.estadoJuego){
                case MENU:
                    juego.getMenu().render(g2d);
                    break;
                case AJUSTES:
                    juego.getAjustes().render(g2d);
                    break;
                case JUGANDO:
                    juego.getJugando().render(g2d);
                    break;
            }
        } finally {
            g2d.dispose();
        }
    }
}
