/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managers;

import helpers.CargaGuarda;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import objetos.Tile;

/**
 *
 * @author lucio
 */
public class TileManager {
    
    public Tile PASTO,CAMINO,AGUA,PLANTA,GIRASOL,JARDIN;
    public BufferedImage peaAtlas,sunAtlas,gardenAtlas,grassAtlas; // Temporal - Se crean atlas distintos segun objeto/entidad
    public ArrayList<Tile> tiles = new ArrayList<>();

    public TileManager() {
        loadAtlas();
        createTiles();
    }

    private void loadAtlas() {
        peaAtlas = CargaGuarda.getSpriteAtlas("pea.png");
        sunAtlas = CargaGuarda.getSpriteAtlas("sunflower.png");
        gardenAtlas = CargaGuarda.getSpriteAtlas("garden_escalado.png");
        grassAtlas = CargaGuarda.getSpriteAtlas("pasto_t.png");
        //peaAtlas = CargaGuarda.getPeaSpriteAtlas();
        //sunAtlas = CargaGuarda.getSunSpriteAtlas();
        //gardenAtlas = CargaGuarda.getGardenSpriteAtlas();
        
    }

    private void createTiles() {
        int id = 0;
        tiles.add(PLANTA = new Tile(getSprite(169,13,peaAtlas),id++,"Planta")); // ID 0
        tiles.add(GIRASOL = new Tile(getSprite(237,120,sunAtlas),id++,"Girasol")); // ID 1
        tiles.add(JARDIN = new Tile(getLargeSprite(0,0,gardenAtlas),id++,"Jardin")); // ID 2
        tiles.add(PASTO = new Tile(getMediumSprite(0,0,grassAtlas),id++,"Pasto")); // ID 3
    }
    
    public BufferedImage getSprite(int id){
        return tiles.get(id).getSprite();
    }
    
    private BufferedImage getSprite(int xC,int yC,BufferedImage atlas){ // Se especifica la coordenada del sprite y el atlas de origen
        return atlas.getSubimage(xC, yC, 29, 31);
    }
    
    private BufferedImage getMediumSprite(int xC, int yC, BufferedImage atlas) {
        return atlas.getSubimage(xC, yC, 245, 168);
    }

    private BufferedImage getLargeSprite(int xC, int yC, BufferedImage atlas) {
        return atlas.getSubimage(xC, yC, 640, 360);
    }
    
    
    
}
