package managers;

import helpers.CargaGuarda;
import java.awt.image.BufferedImage;

/**
 * Registro estático de todos los sprites y animaciones de plantas.
 * Debe inicializarse una vez al arrancar el juego con SpriteManager.loadAll()
 *
 * Fuentes:
 *   Peashooter → peaAtlas.png (234×130)
 *
 *   Sunflower → sunflower.png (626×213), sección IDLE extraída manualmente.
 */
public class SpriteManager {

    private static SpriteSheet peaSheet;
    private static BufferedImage[] sunflowerIdleFrames;

    /** Carga todos los atlas. Llamar una sola vez antes de crear TileManager. */
    public static void loadAll() {
        // peaAtlas.png: 234 px / 9 cols = 29 px por frame; filas de planta = 31 px
        peaSheet = new SpriteSheet("peaAtlas.png", 29, 31);
        loadSunflowerFrames();
    }

    /**
     * Extrae los frames idle del almanaque de Sunflower.
     * Ajustar startX, startY, w, h si los sprites quedan desalineados.
     */
    private static void loadSunflowerFrames() {
        BufferedImage atlas = CargaGuarda.getSpriteAtlas("sunflower.png");
        // Sección [IDLE] en sunflower.png (626×213):
        // 8 frames de 30×33 px, separados por 2 px (stride = 32)
        // startX/startY: ajustar si los sprites quedan desalineados
        int startX = 175, startY = 120, w = 30, h = 33, stride = 32, count = 8;
        sunflowerIdleFrames = new BufferedImage[count];
        for (int i = 0; i < count; i++) {
            sunflowerIdleFrames[i] = atlas.getSubimage(startX + i * stride, startY, w, h);
        }
    }

    /**
     * Devuelve el primer frame idle de una planta.
     * Usado para el sprite estático en la hotbar y en el grid.
     *
     * @param plantaId ID de la planta en la tabla 'plantas' (1=Peashooter, 2=Sunflower)
     */
    public static BufferedImage getStaticSprite(int plantaId) {
        return switch (plantaId) {
            case 1 -> peaSheet.getFrame(0, 0);
            case 2 -> sunflowerIdleFrames[0];
            default -> null;
        };
    }

    /**
     * Devuelve todos los frames de la animación idle.
     *
     * @param plantaId ID de la planta
     */
    public static BufferedImage[] getIdleFrames(int plantaId) {
        return switch (plantaId) {
            case 1 -> peaSheet.getRow(0, 9);
            case 2 -> sunflowerIdleFrames.clone();
            default -> new BufferedImage[0];
        };
    }

    /**
     * Crea una animación idle lista para usar.
     *
     * @param plantaId     ID de la planta
     * @param ticksPerFrame Cuántos ticks de juego dura cada frame
     */
    public static Animation createIdleAnimation(int plantaId, int ticksPerFrame) {
        return new Animation(getIdleFrames(plantaId), ticksPerFrame);
    }
}
