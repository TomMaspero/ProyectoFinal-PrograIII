package managers;

import entidades.Enemigo;
import entidades.Proyectil;
import escenas.Jugando;
import helpers.CargaGuarda;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class CombatManager {
    //private List<Proyectil> proyectiles = new ArrayList<>();
    //private List<Enemigo> enemigos = new ArrayList<>();
    
    private Jugando jugando;
    private BufferedImage[] imgProyectiles;
    private ArrayList<Proyectil> proyectiles = new ArrayList<>();
    
    public CombatManager(Jugando jugando){
        this.jugando = jugando;
        imgProyectiles = new BufferedImage[5]; // 5 Sprites max 
        cargarImgProyectiles();
    }
    
    public void agregaProyectil(int x, int y){
        proyectiles.add(new Proyectil(x, y)); 
        // Reemplazar por coordenadas reales de pantalla.
        // Se usa en Jugando -> mouseClicked para un test de spawneo                                        
    }
    
    private void cargarImgProyectiles() {
        BufferedImage atlas = CargaGuarda.getSpriteAtlas("peaAtlas.png");
        imgProyectiles[0] = atlas.getSubimage(2,108,10,10); // reemplazar subimage por coord en atlas
    }
    
    public void update(){
        for(Proyectil p : proyectiles)
            p.mover(2.0f, 0); // Mueve proyectiles hacia la derecha ->
    }
    
    public void draw(Graphics g){
        for(Proyectil p : proyectiles)
            dibujaProyectil(p,g);
    }
    
    private void dibujaProyectil(Proyectil p, Graphics g){
        g.drawImage(imgProyectiles[0],(int) p.getX(),(int) p.getY(), null);
    }
    
}
