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
 *
 * @author lucio
 */
public class MyMouseListener implements MouseListener,MouseMotionListener{

    private Juego juego;
    
    public MyMouseListener(Juego juego){
        this.juego = juego;
    }
    @Override
    public void mouseClicked(MouseEvent me) {
        if(me.getButton() == MouseEvent.BUTTON1){
            switch(EstadoJuego.estadoJuego){
                case MENU:
                    juego.getMenu().mouseClicked(me.getX(),me.getY());
                    break;
                case JUGANDO:
                    juego.getJugando().mouseClicked(me.getX(),me.getY());
                    break;
                case AJUSTES:
                    juego.getAjustes().mouseClicked(me.getX(),me.getY());
                    break;
            }
        }
    }
    
    @Override
    public void mouseMoved(MouseEvent me) {
        switch(EstadoJuego.estadoJuego){
                case MENU:
                    juego.getMenu().mouseMoved(me.getX(),me.getY());
                    break;
                case JUGANDO:
                    juego.getJugando().mouseMoved(me.getX(),me.getY());
                    break;
                case AJUSTES:
                    juego.getAjustes().mouseClicked(me.getX(),me.getY());
                    break;
            }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        switch(EstadoJuego.estadoJuego){
                case MENU:
                    juego.getMenu().mousePressed(me.getX(),me.getY());
                    break;
                case JUGANDO:
                    juego.getJugando().mousePressed(me.getX(),me.getY());
                    break;
                case AJUSTES:
                    juego.getAjustes().mouseClicked(me.getX(),me.getY());
                    break;
            }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        switch(EstadoJuego.estadoJuego){
                case MENU:
                    juego.getMenu().mouseReleased(me.getX(),me.getY());
                    break;
                case JUGANDO:
                    juego.getJugando().mouseReleased(me.getX(), me.getY());
                    break;
                case AJUSTES:
                    juego.getAjustes().mouseClicked(me.getX(),me.getY());
                    break;
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
