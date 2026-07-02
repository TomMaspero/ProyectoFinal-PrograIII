/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import main.EstadoJuego;
import static main.EstadoJuego.*;
import main.Juego;

/**
 * Se encarga de traducir los comandos del raton en acciones del juego
 * @author lucio
 */
public class MyMouseListener implements MouseListener,MouseMotionListener{

    private Juego juego;
    
    public MyMouseListener(Juego juego){
        this.juego = juego;
    }
    @Override
    /**
     * Leyendo las coordenadas del raton, comprueba si se hizo click en un objeto y cambia la escena de ser necesario.
     */
    public void mouseClicked(MouseEvent me) {
        if(me.getButton() == MouseEvent.BUTTON1){
            int x = me.getX() / 2, y = me.getY() / 2;
            switch(EstadoJuego.estadoJuego){
                case MENU:    juego.getMenu().mouseClicked(x, y);    break;
                case JUGANDO: juego.getJugando().mouseClicked(x, y); break;
                case AJUSTES: juego.getAjustes().mouseClicked(x, y); break;
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        int x = me.getX() / 2, y = me.getY() / 2;
        switch(EstadoJuego.estadoJuego){
            case MENU:    juego.getMenu().mouseMoved(x, y);    break;
            case JUGANDO: juego.getJugando().mouseMoved(x, y); break;
            case AJUSTES: juego.getAjustes().mouseMoved(x, y); break;
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        int x = me.getX() / 2, y = me.getY() / 2;
        switch(EstadoJuego.estadoJuego){
            case MENU:    juego.getMenu().mousePressed(x, y);    break;
            case JUGANDO: juego.getJugando().mousePressed(x, y); break;
            case AJUSTES: juego.getAjustes().mousePressed(x, y); break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        int x = me.getX() / 2, y = me.getY() / 2;
        switch(EstadoJuego.estadoJuego){
            case MENU:    juego.getMenu().mouseReleased(x, y);    break;
            case JUGANDO: juego.getJugando().mouseReleased(x, y); break;
            case AJUSTES: juego.getAjustes().mouseReleased(x, y); break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseExited(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    
}
