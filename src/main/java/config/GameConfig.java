package config;

/**
 * Constantes de juego para facilitar ajustes de balance
 * Los valores especificos de cada planta/enemigo (costo, dano, vida, puntaje, peso)
 * siguen viniendo de la base de datos — esta clase es solo para constantes que no
 * tienen representacion en la DB
 */
public class GameConfig {

    // Oleadas (WaveManager) 
    public static final int WAVE_RANDOM_OFFSET_MAX = 180; // variacion aleatoria extra (0-3s)
    public static final int[] WAVE_UMBRALES_PUNTOS = {0, 30, 80, 200, 500};
    public static final int[] WAVE_INTERVALOS_BASE = {420, 360, 300, 240, 120}; // ticks entre spawns (empezando en 7s a 60 UPS)

    // Combate 
    public static final float PROYECTIL_VELOCIDAD = 1.75f;
    public static final int PROYECTIL_DANIO = 20;
    public static final float ZOMBIE_VELOCIDAD = -0.25f;

    // ── Plantas
    public static final int FIRE_INTERVAL = 96;    // ticks entre disparos de un peashooter (1.6s)
    public static final int SUN_INTERVAL = 480;    // ticks entre generacion de sol de un girasol (8s)
    public static final int PASSIVE_SUN_INTERVAL = SUN_INTERVAL * 2; // generacion pasiva de sol
    public static final int SOL_INICIAL = 100;
    public static final int SOL_POR_GENERACION = 25;

    // ── Vidas 
    public static final int VIDAS_INICIALES = 5;
}
