/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IU;

import java.awt.Color;
import java.awt.Graphics;
import static main.EstadoJuego.*;
import static main.EstadoJuego.SetEstadoJuego;

/**
 *
 * @author lucio
 */
public class Hotbar {
    private int x, y, width, height;
    private MyButton bMenu;

    public Hotbar(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        
        initButtons();
    }
    
    public void draw(Graphics g){
        // Fondo
        g.setColor(Color.ORANGE);
        g.fillRect(x,y,width,height);
        // Botones
        drawButtons(g);
    }
    
    private void initButtons() {
        bMenu = new MyButton("Menu",2,482,100,30);
    }
    
    private void drawButtons(Graphics g) {
        bMenu.draw(g);
    }
    


    public void mouseClicked(int x, int y) {
        if(bMenu.getBounds().contains(x,y)){
            SetEstadoJuego(MENU);
        }
    }


    public void mouseMoved(int x, int y) {
         bMenu.setMouseOver(false);
        if(bMenu.getBounds().contains(x,y)){
            bMenu.setMouseOver(true);
        }
    }


    public void mousePressed(int x, int y) {
        if(bMenu.getBounds().contains(x,y)){
            bMenu.setMousePressed(true);
        }
    }


    public void mouseReleased(int x, int y) {
        resetButtons();
    }
    
    private void resetButtons() {
        bMenu.resetBooleans();    
    }
}
