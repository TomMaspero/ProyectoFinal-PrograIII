/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managers;

import helpers.CargaGuarda;
import escenas.Jugando;
import entidades.Enemigo;
import entidades.TipoEnemigo;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author lucio
 */
public class EnemyManager {
    private Jugando jugando;
    private List<TipoEnemigo> tipos;
    private Map<Integer, BufferedImage> spritesPorTipo = new HashMap<>();
    private ArrayList<Enemigo> enemigos = new ArrayList<>();
    private Random random = new Random();
    private int pesoTotal;
    
    public EnemyManager(Jugando jugando, List<TipoEnemigo> tipos){
        this.jugando = jugando;
        this.tipos = tipos;
        cargarImgEnemigos();
        
        int total = 0;
        for (TipoEnemigo t : tipos) {
            total += t.getPeso();
        }
        
        pesoTotal = total;
    }
    
    public void agregaEnemigo(int x, int y){
        TipoEnemigo tipo = elegirTipoAleatorio();
        enemigos.add(new Enemigo(x, y, tipo));
        // Se usa en Jugando -> mouseClicked para un test de spawneo
    }
    
    private TipoEnemigo elegirTipoAleatorio() {
        int r = random.nextInt(pesoTotal);
        int acumulado = 0;
        
        for (TipoEnemigo t : tipos) {
            acumulado += t.getPeso();
            if (r < acumulado) {
                return t;
            }
        }
        
        return tipos.get(tipos.size() - 1);
    }
    
    private void cargarImgEnemigos() {
        for (TipoEnemigo t : tipos) {
            BufferedImage atlas = CargaGuarda.getSpriteAtlas(t.getRutaSprite());
            
            Rectangle crop = getCropRect(t.getNombre());
            
            spritesPorTipo.put(t.getEnemigoId(), atlas.getSubimage(crop.x, crop.y, crop.width, crop.height));
        }
    }
    
    // obtener las coordenadas del atlas para cada tipo de enemigo:
    private Rectangle getCropRect(String nombre) {
        return switch (nombre) {
            case "normal"  -> new Rectangle(7, 6, 27, 44);
            case "cono"    -> new Rectangle(8, 2, 26, 54);
            case "balde"   -> new Rectangle(4, 10, 35, 57);
            case "bandera" -> new Rectangle(4, 7, 41, 54);
            default -> throw new IllegalArgumentException("tipo de enemigo no reconocido");
        };
    }
    
    public void update(){
        for(Enemigo e : enemigos)
            e.mover(-0.2f, 0);
    }
    
    public void draw(Graphics g){
        for(Enemigo e : enemigos)
            dibujaEnemigo(e,g);
    }
    
    private void dibujaEnemigo(Enemigo e, Graphics g){
        BufferedImage img = spritesPorTipo.get(e.getEnemigoId());
        g.drawImage(img,(int) e.getX(),(int) e.getY(), null);
    }
    
    public ArrayList<Enemigo> getEnemigos() {
        return enemigos;
    }

    public int removeDeadEnemies() {
        int tamInicio = enemigos.size(); // Cuenta los enemigos
        enemigos.removeIf(e -> e.getVida() <= 0); // Resta los muertos
        int tamFinal = tamInicio - enemigos.size();
        return tamFinal;
    }
    
    // retorna cuantos enemigos pasaron la linea
    public int removeEnemiesTrasPasarLimite(int posLineaX) {
        int cont = 0;
        Iterator<Enemigo> it = enemigos.iterator();
        while (it.hasNext()) {
            if (it.next().getX() <= posLineaX) {
                it.remove();
                cont++;
            }
        }
        
        return cont;
    }
    
    public int calcularPuntajeMuertes() {
        int total = 0;
        for (Enemigo e : enemigos) {
            if (e.getVida() <= 0) {
                total += e.getPuntaje();
            }
        }
        
        return total;
    }
}
