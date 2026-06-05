/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IU;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author lucio
 */
public class MyButton {
    private int x,y,width,height;
    private String text;
    private Rectangle bounds;
    private boolean mouseOver, mousePressed;
    
    public MyButton(String text,int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        
        initBounds();
    }
    
    // Hay que agregar otro constructor para botones de la hotbar, agregando id
    

    private void initBounds() {
        this.bounds = new Rectangle(x,y,width,height);
    }
    
    public void draw(Graphics g){
        // Cuerpo
        drawBody(g);
        // Borde
        drawBorder(g);
        
        // Texto
        drawText(g);
    }
    
    private void drawBody(Graphics g){
        if(mouseOver){
            g.setColor(Color.gray);
        }else
            g.setColor(Color.WHITE);
        g.fillRect(x,y,width,height);
    }
    
    private void drawText(Graphics g){
        int w = g.getFontMetrics().stringWidth(text);
        g.setColor(Color.BLACK);
        g.drawString(text, x + width / 2 - w / 2, y + height / 2);
    }
    
    private void drawBorder(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(x,y,width,height);
        if(mousePressed){
            g.drawRect(x+1, y+1, width-2, height-2);
            g.drawRect(x+2, y+2, width-4, height-4);
        }
    }
    
    public void setMouseOver(boolean mouseOver){
        this.mouseOver = mouseOver;
    }
    
    public boolean isMouseOver(){
        return mouseOver;
    }
    
    public void setMousePressed(boolean mousePressed){
        this.mousePressed = mousePressed;
    }
    
    public boolean isMousePressed(){
        return mousePressed;
    }
    
    public Rectangle getBounds(){
        return bounds;
    }
    
    public void resetBooleans(){
        this.mouseOver = false;
        this.mousePressed = false;
    }

    
    
    
}
