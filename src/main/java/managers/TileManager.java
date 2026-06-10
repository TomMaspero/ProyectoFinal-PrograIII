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
 * Se encarga de manejar los tiles del campo de juego.
 * @author lucio
 */
public class TileManager {
    
    public Tile PASTO,CAMINO,AGUA,PLANTA,GIRASOL,JARDIN;
    public BufferedImage peaAtlas,sunAtlas,gardenAtlas,grassAtlas; // Temporal - Se crean atlas distintos segun objeto/entidad
    public ArrayList<Tile> tiles = new ArrayList<>(); // Arraylist que contiene todos los tiles del juego

    public TileManager() {
        loadAtlas();
        createTiles();
    }
    /**
     * Se especifican las cargas de atlas para los tiles.
     */
    private void loadAtlas() {
        peaAtlas = CargaGuarda.getSpriteAtlas("pea.png");
        sunAtlas = CargaGuarda.getSpriteAtlas("sunflower.png");
        gardenAtlas = CargaGuarda.getSpriteAtlas("garden_escalado.png");
        grassAtlas = CargaGuarda.getSpriteAtlas("pasto_t.png");
    }
    /**
     * Genera y guarda los tiles leidos desde los atlas en un nuevo tile almacenado en la lista.
     */
    private void createTiles() {
        int id = 0;
        tiles.add(PLANTA = new Tile(getSprite(169,13,peaAtlas),id++,"Planta")); // ID 0
        tiles.add(GIRASOL = new Tile(getSprite(237,120,sunAtlas),id++,"Girasol")); // ID 1
        tiles.add(JARDIN = new Tile(getLargeSprite(0,0,gardenAtlas),id++,"Jardin")); // ID 2
        tiles.add(PASTO = new Tile(getMediumSprite(0,0,grassAtlas),id++,"Pasto")); // ID 3
    }
    /**
     * Retorna un sprite leido desde la lista segun su ID.
     * @param id ID del sprite guardado
     * @return 
     */
    public BufferedImage getSprite(int id){
        return tiles.get(id).getSprite();
    }
    /**
     * Retorna un tile pequeño segun coordenadas y su atlas.
     */
    private BufferedImage getSprite(int xC,int yC,BufferedImage atlas){ // Se especifica la coordenada del sprite y el atlas de origen
        return atlas.getSubimage(xC, yC, 29, 31);
    }
    /**
     * Retorna un tile mediano segun coordenadas y su atlas.
     */
    private BufferedImage getMediumSprite(int xC, int yC, BufferedImage atlas) { //
        return atlas.getSubimage(xC, yC, 245, 168);
    }
    /**
     * Retorna un tile grande segun coordenadas y su atlas.
     */
    private BufferedImage getLargeSprite(int xC, int yC, BufferedImage atlas) {
        return atlas.getSubimage(xC, yC, 640, 360);
    }
    /**
     * Devuelve un objeto tile segun el ID especificado en parametro
     * @param id ID del tile buscado
     * @return Tile correspondiente al ID
     */
    public Tile getTile(int id){
        return tiles.get(id);
    }
    
    
    
}
