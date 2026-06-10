/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package escenas;

import IU.Hotbar;
import helpers.EditorNivel;
import java.awt.Graphics;
import main.Juego;
import managers.TileManager;
import objetos.Tile;

/**
 *
 * @author lucio
 */
public class Jugando extends EscenaJuego implements MetodosEscena{
    private int[][] lvl; // Array de numeros que representan los tiles del nivel cargado
    private TileManager tileManager;
    private Hotbar hotbar;
    private Tile selectedTile;
    private int mouseX,mouseY;
    private boolean drawSelect;
    
    
    public Jugando(Juego juego) {
        super(juego);
        
        lvl = EditorNivel.getLevelData();
        tileManager = new TileManager();
        hotbar = new Hotbar(0,360,640,100,this);
        // Nivel
        // Tile Manager
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(tileManager.getSprite(2),0,0,null); // Dibuja jardin
        g.drawImage(tileManager.getSprite(3),76,17, null); // Dibuja pasto
       for(int y = 0; y < lvl.length; y++){ // Dibuja planta y girasol segun lvl
           for(int x = 0; x<lvl[y].length;x++){
               int id = lvl[y][x]; 
               g.drawImage(tileManager.getSprite(id), 78+x*29, 19+y*31, null);
           }
       }
       
       hotbar.draw(g);
       drawSelectedTile(g);
    }
    
    private void drawSelectedTile(Graphics g) {
        if(selectedTile != null && drawSelect){
        g.drawImage(selectedTile.getSprite(),mouseX,mouseY,null);
        }
    }
    
    public void setSelectedTile(Tile tile){
        this.selectedTile = tile;
        drawSelect = true;
    }
    
    public TileManager getTileManager(){
        return tileManager;
    }
    

    @Override
    public void mouseClicked(int x, int y) {
        if(y>=360){
            hotbar.mouseClicked(x, y);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        if(y>=360){
            hotbar.mouseMoved(x, y);
            drawSelect = false;
        }else{
            drawSelect = true;
            // dibuja en tiles de 32x32
            mouseX = (x / 32) * 32;
            mouseY = (y / 32) * 32;
        }
    }

    @Override
    public void mousePressed(int x, int y) {
         if(y>=360){
            hotbar.mousePressed(x, y);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
            hotbar.mouseReleased(x, y);
    }

    
    
}
