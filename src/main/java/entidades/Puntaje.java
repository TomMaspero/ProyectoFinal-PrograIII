package entidades;

import java.time.LocalDateTime;

public class Puntaje {
    private int puntajeId;
    private int jugadorId;
    private int puntuacion;
    private LocalDateTime fecha;

    public Puntaje() {}

    public Puntaje(int jugadorId, int puntuacion) {
        this.jugadorId = jugadorId;
        this.puntuacion = puntuacion;
    }

    public int getPuntajeId() { return puntajeId; }
    public void setPuntajeId(int puntajeId) { this.puntajeId = puntajeId; }

    public int getJugadorId() { return jugadorId; }
    public void setJugadorId(int jugadorId) { this.jugadorId = jugadorId; }

    public int getPuntuacion() { return puntuacion; }
    public void setPuntuacion(int puntuacion) { this.puntuacion = puntuacion; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
