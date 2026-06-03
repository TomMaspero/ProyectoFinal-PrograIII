/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package escenas;

import IU.MyButton;
import java.awt.Color;
import java.awt.Graphics;
import static main.EstadoJuego.*;
import main.Juego;

/**
 *
 * @author lucio
 */
public class Menu extends EscenaJuego implements MetodosEscena{
    private MyButton bJugar,bAjustes,bSalir;
    public Menu(Juego juego) {
        super(juego);
        initButtons();
    }

    private void initButtons() {
        bJugar = new MyButton("Jugar",100,100,100,30);
    }
    
    @Override
    public void render(Graphics g) {
        drawButtons(g);
    }

    private void drawButtons(Graphics g) {
        bJugar.draw(g);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(bJugar.getBounds().contains(x,y)){
            SetEstadoJuego(JUGANDO);
        }
    }

    public void mouseMoved(int x, int y) {
         bJugar.setMouseOver(false);
        if(bJugar.getBounds().contains(x,y)){
            bJugar.setMouseOver(true);
        }
    }

    public void mousePressed(int x, int y) {
        if(bJugar.getBounds().contains(x,y)){
            bJugar.setMousePressed(true);
        }
    }
    
}
