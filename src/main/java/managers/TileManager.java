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

    public Tile PASTO, JARDIN;
    private BufferedImage gardenAtlas, grassAtlas;
    public ArrayList<Tile> tiles = new ArrayList<>(); // Arraylist que contiene todos los tiles del juego

    public TileManager() {
        loadAtlas();
        createTiles();
    }
    /**
     * Carga los atlas de tiles de fondo.
     * Los sprites de plantas los gestiona SpriteManager.
     */
    private void loadAtlas() {
        gardenAtlas = CargaGuarda.getSpriteAtlas("garden_escalado.png");
        grassAtlas  = CargaGuarda.getSpriteAtlas("pasto_t.png");
    }
    /**
     * Genera los tiles de fondo.
     *   tiles[0] = JARDIN
     *   tiles[1] = PASTO
     */
    private void createTiles() {
        int id = 0;
        tiles.add(JARDIN = new Tile(getLargeSprite(0, 0, gardenAtlas),  id++, "Jardin")); // ID 0
        tiles.add(PASTO  = new Tile(getMediumSprite(0, 0, grassAtlas), id++, "Pasto"));  // ID 1
    }
    /**
     * Retorna un tile leido desde la lista segun su ID.
     * @param id ID del tile guardado
     * @return 
     */
    public BufferedImage getSprite(int id){
        return tiles.get(id).getSprite();
    }

    /**
     * Retorna el sprite estático de una planta según su plantaId de la base de datos.
     * Delega a SpriteManager — debe haberse llamado SpriteManager.loadAll() antes.
     * @param plantaId ID de la planta en la tabla 'plantas' (1=Peashooter, 2=Sunflower)
     */
    public BufferedImage getSpriteByPlantaId(int plantaId) {
        return SpriteManager.getStaticSprite(plantaId);
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
    
    
    
}
