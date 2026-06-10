package managers;

import java.awt.image.BufferedImage;

/**
 * Cicla un arreglo de frames a velocidad configurable.
 * Se actualiza llamando a update() una vez por game tick (UPS).
 *
 * Uso típico:
 *   Animation anim = SpriteManager.createIdleAnimation(plantaId, 8);
 *   // En el game loop:
 *   anim.update();
 *   g.drawImage(anim.getCurrentFrame(), x, y, null);
 */
public class Animation {
    private final BufferedImage[] frames;
    private final int speed;        // ticks por frame
    private int tick         = 0;
    private int currentFrame = 0;

    /**
     * @param frames Arreglo de frames de la animación
     * @param speed  Cuántos ticks de juego dura cada frame (mayor = más lenta)
     */
    public Animation(BufferedImage[] frames, int speed) {
        this.frames = frames;
        this.speed  = speed;
    }

    /** Debe llamarse una vez por tick del game loop. */
    public void update() {
        if (++tick >= speed) {
            tick = 0;
            currentFrame = (currentFrame + 1) % frames.length;
        }
    }

    /** Devuelve el frame que debe renderizarse en este tick. */
    public BufferedImage getCurrentFrame() {
        return frames[currentFrame];
    }

    /** Reinicia la animación al primer frame. */
    public void reset() {
        tick         = 0;
        currentFrame = 0;
    }

    public int getFrameCount() { return frames.length; }
}
