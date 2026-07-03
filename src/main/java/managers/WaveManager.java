/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package managers;

/**
 *
 * @author Lucio
 */
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

    public WaveManager(EnemyManager enemyManager, int gridY, int cellHeight, int gridRows, int spawnX) {
        this.enemyManager = enemyManager;
        this.gridY = gridY;
        this.cellHeight = cellHeight;
        this.gridRows = gridRows;
        this.spawnX = spawnX;
        this.nextSpawnTime = calcularSiguienteSpawn();
    }

    public void update() {
        spawnTimer++;
        if (spawnTimer >= nextSpawnTime) {
            spawnTimer = 0;
            nextSpawnTime = calcularSiguienteSpawn();
            spawnZombie();
        }
    }

    private int calcularSiguienteSpawn() {
        return BASE_INTERVAL + random.nextInt(RANDOM_OFFSET_MAX);
    }

    private void spawnZombie() {
        int row = random.nextInt(gridRows);  // 0–4
        // y alinea el zombie a la celda segun el tamaño de px
        int y = gridY + row * cellHeight + (cellHeight - 44) / 2;
        enemyManager.agregaEnemigo(spawnX, y);
    }
}
