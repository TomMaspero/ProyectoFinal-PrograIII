package entidades;

public class Proyectil extends Entidad {
    private int danio = 20;
    private float velocidad = 2.0f;
    
    public Proyectil(float x, float y) {
        super(x, y, 10); // chequear tamanio sprite
    }
    
    /*public void update() {
        if (getX() < 640) { // limite pantalla (chequear numero)
            mover(velocidad, 0);
        }
    }*/

    public int getDanio() {
        return danio;
    }

    public void setDanio(int danio) {
        this.danio = danio;
    }

    public float getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(float velocidad) {
        this.velocidad = velocidad;
    }
}
