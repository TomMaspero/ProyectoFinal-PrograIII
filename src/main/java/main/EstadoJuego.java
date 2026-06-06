/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package main;

/**
 * Se definen los estados del juego y sus métodos relacionados.
 * @author lucio
 */
public enum EstadoJuego {
    JUGANDO, MENU, AJUSTES;
    
    
    public static EstadoJuego estadoJuego = MENU;
    public static void SetEstadoJuego(EstadoJuego estado){
        estadoJuego = estado;
    }
}
