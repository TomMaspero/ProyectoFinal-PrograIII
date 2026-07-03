/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.EstadoJuego;

import main.Juego;
import static main.EstadoJuego.*;



/**
 * Se encarga de traducir los comandos de teclado a las acciones del juego.
 * @author lucio
 */
public class KeyboardListener implements KeyListener{
    private Juego juego;

    public KeyboardListener(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        if (EstadoJuego.estadoJuego == JUGANDO) {
            juego.getJugando().keyTyped(ke.getKeyChar());
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (EstadoJuego.estadoJuego == JUGANDO && juego.getJugando().isPidiendoNombre()) {
            return; // el jugador esta escribiendo su nombre — no procesar atajos de debug
        }

        if(ke.getKeyCode() == KeyEvent.VK_A){
            EstadoJuego.estadoJuego = MENU;
            System.out.println("[A]Accediendo al menu...");
        }
        
        if(ke.getKeyCode() == KeyEvent.VK_S){
            EstadoJuego.estadoJuego = AJUSTES;
            System.out.println("[S]Opciones...");
        }
        
        if(ke.getKeyCode() == KeyEvent.VK_D){
            juego.resetJugando();
            EstadoJuego.estadoJuego = JUGANDO;
            System.out.println("[D]Jugar...");
        }
        
        
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
