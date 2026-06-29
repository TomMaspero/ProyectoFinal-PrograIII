/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managers;

import helpers.CargaGuarda;
import escenas.Jugando;
import enemigos.Enemigo;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author lucio
 */
public class EnemyManager {
    private Jugando jugando;
    private BufferedImage[] imgEnemigos;
    private Enemigo testEnemigo;
    
    public EnemyManager(Jugando jugando){
        this.jugando = jugando;
        imgEnemigos = new BufferedImage[5]; // 5 Sprites max para enemigos
        testEnemigo = new Enemigo(100, 100, 0, 0); // Reemplazar por coordenadas reales de pantalla.
        cargarImgEnemigos();
    }
    
    private void cargarImgEnemigos() {
        BufferedImage atlas = CargaGuarda.getSpriteAtlas("zombieAtlasEdit.png");
        imgEnemigos[0] = atlas.getSubimage(7,6,27,44); // reemplazar subimage por coord en atlas
    }
    
    public void update(){
    
    }
    
    public void draw(Graphics g){
        dibujaEnemigo(testEnemigo,g);
    }
    
    private void dibujaEnemigo(Enemigo e, Graphics g){
        g.drawImage(imgEnemigos[0],(int) e.getX(),(int) e.getY(), null);
    }

    
}   
