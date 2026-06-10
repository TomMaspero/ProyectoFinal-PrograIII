/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IU;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Clase base para definir los botones en la IU del juego
 * @author lucio
 */
public class MyButton {
    private int x,y,width,height,id;
    private int id = -1;
    private BufferedImage sprite;
    private String text;
    private Rectangle bounds;
    private boolean mouseOver, mousePressed;
    /**
     * Constructor base para los botones de la IU del juego
     * @param text Nombre del boton
     * @param x Posición x de renderizado en pantalla(px)
     * @param y Posición y de renderizado en pantalla(px)
     * @param width Ancho del boton(px)
     * @param height Alto del boton(px)
     */
    public MyButton(String text,int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.id = -1; // no se usa en este constructor
        
        initBounds();
    }
    
    /**
     * Constructor para botones de la hotbar con ID de planta y sprite.
     */
    public MyButton(String text, int id, BufferedImage sprite, int x, int y, int width, int height) {
        this(text, x, y, width, height);
        this.id = id;
        this.sprite = sprite;

        initBounds();
    }

    public int getId() { return id; }

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

        if (sprite != null) {
            // Centra el sprite en la mitad superior del botón; clamp para no salir del borde
            int sx = x + (width  - sprite.getWidth())  / 2;
            int sy = Math.max(y, y + (height / 2 - sprite.getHeight()) / 2);
            g.drawImage(sprite, sx, sy, null);
        }
    }

    private void drawText(Graphics g){
        int w = g.getFontMetrics().stringWidth(text);
        g.setColor(Color.BLACK);
        // Si hay sprite, el texto va en la mitad inferior; si no, centrado
        int textY = (sprite != null) ? y + height - 4 : y + height / 2;
        g.drawString(text, x + width / 2 - w / 2, textY);
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
