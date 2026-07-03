/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IU;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.RenderingHints;
import java.awt.Rectangle;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

/**
 * Clase base para definir los botones en la IU del juego
 * @author lucio
 */
public class MyButton {
    private int x,y,width,height;
    private int id = -1;
    private BufferedImage sprite;
    private String text;
    private Rectangle bounds;
    private boolean mouseOver, mousePressed;

    // Radio de las esquinas redondeadas
    private static final int ARC = 16;

    public MyButton(String text,int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.id = -1;
        initBounds();
    }

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
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawShadow(g2);
        drawBody(g2);
        drawBorder(g2);
        drawText(g2);
    }

    private void drawShadow(Graphics2D g2){
        int offset = mousePressed ? 2 : 4; // la sombra "se achica" al presionar
        g2.setColor(new Color(0, 0, 0, 60)); // negro semitransparente
        g2.fill(new RoundRectangle2D.Float(x + 2, y + offset, width, height, ARC, ARC));
    }

    private void drawBody(Graphics2D g2){
        RoundRectangle2D.Float shape = new RoundRectangle2D.Float(x, y, width, height, ARC, ARC);

        Color top, bottom;
        if (mousePressed) {
            top = new Color(180, 180, 180);
            bottom = new Color(150, 150, 150);
        } else if (mouseOver) {
            top = new Color(235, 235, 235);
            bottom = new Color(200, 200, 200);
        } else {
            top = Color.WHITE;
            bottom = new Color(220, 220, 220);
        }

        GradientPaint gradient = new GradientPaint(x, y, top, x, y + height, bottom);
        g2.setPaint(gradient);
        g2.fill(shape);

        if (sprite != null) {
            int sx = x + (width  - sprite.getWidth())  / 2;
            int sy = Math.max(y, y + (height / 2 - sprite.getHeight()) / 2);
            // pequeño desplazamiento al presionar para dar efecto
            if (mousePressed) sy += 1;
            g2.drawImage(sprite, sx, sy, null);
        }
    }

    private void drawText(Graphics2D g2){
        int w = g2.getFontMetrics().stringWidth(text);
        g2.setColor(Color.BLACK);
        int textY = (sprite != null) ? y + height - 4 : y + height / 2;
        if (mousePressed) textY += 1;
        g2.drawString(text, x + width / 2 - w / 2, textY);
    }

    private void drawBorder(Graphics2D g2){
        g2.setColor(mouseOver ? new Color(80, 80, 80) : new Color(50, 50, 50));
        g2.setStroke(new java.awt.BasicStroke(mousePressed ? 2.5f : 1.5f));
        g2.draw(new RoundRectangle2D.Float(x, y, width - 1, height - 1, ARC, ARC));
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
