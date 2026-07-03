package entidades;

public class Planta {
    private int plantaId;
    private String nombre;
    private int costoSol;
    private int dano;
    private String rutaSprite;

    public Planta() {}

    public Planta(String nombre, int costoSol, int dano, String rutaSprite) {
        this.nombre = nombre;
        this.costoSol = costoSol;
        this.dano = dano;
        this.rutaSprite = rutaSprite;
    }

    public int getPlantaId() { return plantaId; }
    public void setPlantaId(int plantaId) { this.plantaId = plantaId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getCostoSol() { return costoSol; }
    public void setCostoSol(int costoSol) { this.costoSol = costoSol; }

    public int getDano() { return dano; }
    public void setDano(int dano) { this.dano = dano; }

    public String getRutaSprite() { return rutaSprite; }
    public void setRutaSprite(String rutaSprite) { this.rutaSprite = rutaSprite; }

    @Override
    public String toString() {
        return "Planta{plantaId=" + plantaId + ", nombre='" + nombre + "', costoSol=" + costoSol + "}";
    }
}
