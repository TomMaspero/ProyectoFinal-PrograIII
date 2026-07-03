package managers;

import entidades.Enemigo;
import entidades.Proyectil;
import escenas.Jugando;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class CombatManager {

    private Jugando jugando;
    private ArrayList<Proyectil> proyectiles = new ArrayList<>();
    private BufferedImage peaSprite;
    private BufferedImage peaSplatSprite;

    public CombatManager(Jugando jugando) {
        this.jugando = jugando;
        peaSprite = SpriteManager.getPeaSprite();
        peaSplatSprite = SpriteManager.getPeaSplatSprite();
    }

    public void spawn(float x, float y, int fila) {
        proyectiles.add(new Proyectil(x, y, fila));
    }

    public void update(ArrayList<Enemigo> enemigos) {
        for (Proyectil p : proyectiles) {
            p.update();
            if (!p.isSplatting() && p.isActivo()) {
                for (Enemigo e : enemigos) {
                    if (e.getVida() > 0 && p.getColision().intersects(e.getColision())) { // Un zombie y un proyectil colisionan
                        e.setVida(e.getVida() - p.getDanio());
                        p.hit();
                        break;
                    }
                }
            }
        }
        proyectiles.removeIf(p -> !p.isActivo());
    }

    public void draw(Graphics g) {
        for (Proyectil p : proyectiles) {
            BufferedImage sprite = p.isSplatting() ? peaSplatSprite : peaSprite;
            g.drawImage(sprite, (int) p.getX(), (int) p.getY(), 16, 16, null);
        }
    }
}
