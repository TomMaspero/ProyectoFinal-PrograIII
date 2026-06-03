/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static java.lang.System.currentTimeMillis;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Lucio
 */
public class Pantalla extends JPanel {

    //private Random random

    
    private Dimension size;
    private Juego juego;
    
    
    
    public Pantalla(Juego juego){
        this.juego = juego;
        
        setPanelSize();
    }
    
    private void setPanelSize(){
        size = new Dimension(640,480);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        juego.getRender().render(g);
    }
}
