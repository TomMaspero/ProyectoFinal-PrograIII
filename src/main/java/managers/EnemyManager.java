/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managers;

import helpers.CargaGuarda;
import escenas.Jugando;
import entidades.Enemigo;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author lucio
 */
public class EnemyManager {
    private Jugando jugando;
    private BufferedImage[] imgEnemigos;
    private ArrayList<Enemigo> enemigos = new ArrayList<>();
    
    public EnemyManager(Jugando jugando){
        this.jugando = jugando;
        imgEnemigos = new BufferedImage[5]; // 5 Sprites max para enemigos
        cargarImgEnemigos();
    }
    
    public void agregaEnemigo(int x, int y){
        enemigos.add(new Enemigo(x, y, 0, 0)); 
        // Reemplazar por coordenadas reales de pantalla.
        // Se usa en Jugando -> mouseClicked para un test de spawneo                                        
    }
    
    private void cargarImgEnemigos() {
        BufferedImage atlas = CargaGuarda.getSpriteAtlas("zombieAtlasEdit.png");
        imgEnemigos[0] = atlas.getSubimage(7,6,27,44); // reemplazar subimage por coord en atlas
    }
    
    public void update(){
        for(Enemigo e : enemigos)
            e.mover(-0.5f, 0);
    }
    
    public void draw(Graphics g){
        for(Enemigo e : enemigos)
            dibujaEnemigo(e,g);
    }
    
    private void dibujaEnemigo(Enemigo e, Graphics g){
        g.drawImage(imgEnemigos[0],(int) e.getX(),(int) e.getY(), null);
    }

    public ArrayList<Enemigo> getEnemigos() {
        return enemigos;
    }

    public void removeDeadEnemies() {
        enemigos.removeIf(e -> e.getVida() <= 0);
    }
}
