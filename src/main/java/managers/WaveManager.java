/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managers;

/**
 *
 * @author Lucio
 */
import escenas.Jugando;
import java.util.Random;

public class WaveManager {

    private EnemyManager enemyManager;
    private Random random = new Random();
    private int spawnTimer = 0;
    private int nextSpawnTime;

    // Constantes del grid
    private int gridY;
    private int cellHeight;
    private int gridRows;
    private int spawnX;  // coords de spawn para los zombies

    private static final int BASE_INTERVAL = 300;      // intervalo de spawn (5 s/60UPS)
    private static final int RANDOM_OFFSET_MAX = 180;   // intervalo random 0,3 seg
    
    private static final int[] UMBRALES_PUNTOS   = {0, 30, 80, 200, 500};
    private static final int[] INTERVALOS_BASE   = {300, 240, 180, 120, 90};
    private int nivelActual = 0;
    private int oleadaActual = 1;
    private Jugando jugando;

    public WaveManager(Jugando jugando, EnemyManager enemyManager, int gridY, int cellHeight, int gridRows, int spawnX) {
        this.jugando = jugando;
        this.enemyManager = enemyManager;
        this.gridY = gridY;
        this.cellHeight = cellHeight;
        this.gridRows = gridRows;
        this.spawnX = spawnX;
        this.nextSpawnTime = calcularSiguienteSpawn();
    }

    public void update() {
        actualizarNivelDificultad();
        spawnTimer++;
        if (spawnTimer >= nextSpawnTime) {
            spawnTimer = 0;
            nextSpawnTime = calcularSiguienteSpawn();
            spawnZombie();
        }
    }
    
    private void actualizarNivelDificultad() {
        int puntosActuales = jugando.getPuntos();
        int nuevoNivel = 0;
        
        for (int i = 0; i < UMBRALES_PUNTOS.length; i++) {
            if (puntosActuales >= UMBRALES_PUNTOS[i]) {
                nuevoNivel = i;
            }
        }
        
        if (nuevoNivel > nivelActual) {
            nivelActual = nuevoNivel;
            oleadaActual++;
        }
    }
    
    private int calcularSiguienteSpawn() {
        int baseActual = INTERVALOS_BASE[nivelActual];
        return baseActual + random.nextInt(RANDOM_OFFSET_MAX);
    }

    private void spawnZombie() {
        // La cantidad de zombies por tanda escala con la oleada actual
        for (int i = 0; i < oleadaActual; i++) {
            int row = random.nextInt(gridRows);  // 0–4
            // y alinea el zombie a la celda segun el tamaño de px
            int y = gridY + row * cellHeight + (cellHeight - 44) / 2;
            enemyManager.agregaEnemigo(spawnX, y);
        }
    }
    
    public int getOleadaActual() {
        return oleadaActual;
    }
}
