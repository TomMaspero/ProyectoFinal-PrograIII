/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import inputs.KeyboardListener;
import inputs.MyMouseListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static java.lang.System.currentTimeMillis;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * Contiene componentes tanto de manejo de la pantalla del juego como del procesamiento de inputs,
 * mediante las clases especificas {@link inputs.KeyboardListener} y {@link inputs.MyMouseListener}
 * @author Lucio
 */
public class Pantalla extends JPanel {
    
    private Dimension size;
    private Juego juego;
    
    private MyMouseListener myMouseListener;
    private KeyboardListener myKeyboardListener;
    
    
    
    public Pantalla(Juego juego){
        this.juego = juego;
        
        setPanelSize();
    }
    
    private void setPanelSize(){
        size = new Dimension(1280,920);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }
    
    public void initInputs(){
        myMouseListener = new MyMouseListener(juego);
        myKeyboardListener = new KeyboardListener(juego);
        
        addMouseListener(myMouseListener);
        addMouseMotionListener(myMouseListener);
        addKeyListener(myKeyboardListener);
        
        requestFocus();
    }
    
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        juego.getRender().render(g);
    }
}
