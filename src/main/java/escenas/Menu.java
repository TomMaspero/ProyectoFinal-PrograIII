/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package escenas;

import java.awt.Color;
import java.awt.Graphics;
import main.Juego;

/**
 *
 * @author lucio
 */
public class Menu extends EscenaJuego implements MetodosEscena{
    
    public Menu(Juego juego) {
        super(juego);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(0, 0, 640, 480);
    }
    
}
