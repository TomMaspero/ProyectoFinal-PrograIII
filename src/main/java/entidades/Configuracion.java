package entidades;

public class Configuracion {
    private int jugadorId;
    private int volumen;
    private String dificultad;
    private boolean pantallaCompleta;

    public Configuracion() {
        this.volumen = 50;
        this.dificultad = "NORMAL";
        this.pantallaCompleta = false;
    }

    public Configuracion(int jugadorId) {
        this();
        this.jugadorId = jugadorId;
    }

    public int getJugadorId() { return jugadorId; }
    public void setJugadorId(int jugadorId) { this.jugadorId = jugadorId; }

    public int getVolumen() { return volumen; }
    public void setVolumen(int volumen) { this.volumen = volumen; }

    public String getDificultad() { return dificultad; }
    public void setDificultad(String dificultad) { this.dificultad = dificultad; }

    public boolean isPantallaCompleta() { return pantallaCompleta; }
    public void setPantallaCompleta(boolean pantallaCompleta) { this.pantallaCompleta = pantallaCompleta; }
}
