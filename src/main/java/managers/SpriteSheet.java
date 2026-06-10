package managers;

import helpers.CargaGuarda;
import java.awt.image.BufferedImage;

/**
 * Wrapper de un atlas de sprites con frames de tamaño uniforme.
 * Permite extraer frames individuales por columna/fila, o una fila completa
 * como arreglo listo para usar en animaciones.
 */
public class SpriteSheet {
    private final BufferedImage atlas;
    private final int frameW, frameH;

    /**
     * @param resource  Nombre del archivo en resources (ej. "peaAtlas.png")
     * @param frameW    Ancho de cada frame en px
     * @param frameH    Alto de cada frame en px
     */
    public SpriteSheet(String resource, int frameW, int frameH) {
        this.atlas  = CargaGuarda.getSpriteAtlas(resource);
        this.frameW = frameW;
        this.frameH = frameH;
    }

    /**
     * Extrae un frame por posición en la grilla del atlas.
     * @param col Columna (0-based)
     * @param row Fila    (0-based)
     */
    public BufferedImage getFrame(int col, int row) {
        return atlas.getSubimage(col * frameW, row * frameH, frameW, frameH);
    }

    /**
     * Extrae una fila completa del atlas como arreglo de frames.
     * @param row       Fila (0-based)
     * @param numFrames Número de frames a extraer desde la columna 0
     */
    public BufferedImage[] getRow(int row, int numFrames) {
        BufferedImage[] frames = new BufferedImage[numFrames];
        for (int i = 0; i < numFrames; i++) {
            frames[i] = getFrame(i, row);
        }
        return frames;
    }

    public int getFrameW() { return frameW; }
    public int getFrameH() { return frameH; }
}
