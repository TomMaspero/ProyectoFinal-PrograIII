package entidades;

public class TipoEnemigo {
    private int enemigoId;
    private String nombre;
    private int vida;
    private int puntaje;
    private String rutaSprite;
    private int peso;
    
    public TipoEnemigo() {
    }

    public int getEnemigoId() {
        return enemigoId;
    }

    public void setEnemigoId(int enemigoId) {
        this.enemigoId = enemigoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public String getRutaSprite() {
        return rutaSprite;
    }

    public void setRutaSprite(String rutaSprite) {
        this.rutaSprite = rutaSprite;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }
    
}
