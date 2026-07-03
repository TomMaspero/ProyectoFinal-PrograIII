package entidades;

public class Proyectil extends Entidad {
    private int danio = 20;
    private float velocidad = 2.0f;
    private int fila;
    private boolean activo = true;
    private boolean splatting = false;
    private int splatTicks = 0;

    public Proyectil(float x, float y, int fila) {
        super(x, y, 10);
        this.fila = fila;
    }

    public void update() {
        if (splatting) {
            splatTicks++;
            if (splatTicks >= 5){
                //System.out.println("suma tick");
                activo = false;
            }
            return;
        }
        if (getX() > 640)
            activo = false;
        else
            mover(velocidad, 0);
    }

    public void hit() {
        splatting = true;
    }

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

    public int getFila() {
        return fila;
    }

    public boolean isActivo() {
        return activo;
    }

    public boolean isSplatting() {
        return splatting;
    }
}
