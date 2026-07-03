/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managers;

/**
 *
 * @author Lucio
 */
import config.GameConfig;
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

        for (int i = 0; i < GameConfig.WAVE_UMBRALES_PUNTOS.length; i++) {
            if (puntosActuales >= GameConfig.WAVE_UMBRALES_PUNTOS[i]) {
                nuevoNivel = i;
            }
        }

        if (nuevoNivel > nivelActual) {
            nivelActual = nuevoNivel;
            oleadaActual++;
        }
    }

    private int calcularSiguienteSpawn() {
        int baseActual = GameConfig.WAVE_INTERVALOS_BASE[nivelActual];
        return baseActual + random.nextInt(GameConfig.WAVE_RANDOM_OFFSET_MAX);
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
