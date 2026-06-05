/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IU;

import escenas.Jugando;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import static main.EstadoJuego.*;
import static main.EstadoJuego.SetEstadoJuego;
import objetos.Tile;

/**
 *
 * @author lucio
 */
public class Hotbar {
    private int x, y, width, height;
    private MyButton bMenu; // Boton para volver al menu
    private Jugando jugando;
    private ArrayList<MyButton> tileButtons = new ArrayList<>(); // Aca se guardan los botones de colocar de la hotbar

    public Hotbar(int x, int y, int width, int height, Jugando jugando) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.jugando = jugando;
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
        int i,w,h,xStart,yStart,xOffset;
        w = 50;
        h = 50;
        xStart = 110;
        yStart = 362;
        xOffset = (int)(w*1.1f);
        i=0;
        bMenu = new MyButton("Menu",2,362,100,30);
        
        for(Tile tile : jugando.getTileManager().tiles){
            tileButtons.add(new MyButton(tile.getNombre(),xStart + xOffset * i,362,50,50));
            i++;
        }
    }
    
    private void drawButtons(Graphics g) {
        bMenu.draw(g);
        drawTileButtons(g);
        
    }
    
    private void drawTileButtons(Graphics g) {
        // Dibuja los botones segun su nombre, sin imagen
        // en los tutoriales hay una seccion para agregar imagenes
        
        for(MyButton b : tileButtons){
             b.draw(g);
        }
    }

    public void mouseClicked(int x, int y) {
        if(bMenu.getBounds().contains(x,y)){
            SetEstadoJuego(MENU);
        }
    }


    public void mouseMoved(int x, int y) {
        for(MyButton b : tileButtons){
                b.setMouseOver(false);
            }
        bMenu.setMouseOver(false);
        
        if(bMenu.getBounds().contains(x,y)){
            bMenu.setMouseOver(true);
        }else{
            for(MyButton b : tileButtons){
                if(b.getBounds().contains(x,y)){
                    b.setMouseOver(true);
                    return;
                }
            }
        }
    }


    public void mousePressed(int x, int y) {
        if(bMenu.getBounds().contains(x,y)){
            bMenu.setMousePressed(true);
        }else{
        for(MyButton b : tileButtons){
            if(b.getBounds().contains(x,y)){
             b.setMousePressed(true);
             return;
            }
            }
        }
    }


    public void mouseReleased(int x, int y) {
        resetButtons();
    }
    
    private void resetButtons() {
        bMenu.resetBooleans();    
        for(MyButton b : tileButtons){
                b.resetBooleans();
            }
    }

    
}
