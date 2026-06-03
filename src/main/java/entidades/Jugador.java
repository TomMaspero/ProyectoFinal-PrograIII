package entidades;

import java.time.LocalDateTime;

public class Jugador {
    private int jugadorId;
    private String nombre;
    private LocalDateTime fechaRegistro;

    public Jugador() {}

    public Jugador(String nombre) {
        this.nombre = nombre;
    }

    public int getJugadorId() { return jugadorId; }
    public void setJugadorId(int jugadorId) { this.jugadorId = jugadorId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    @Override
    public String toString() {
        return "Jugador{jugadorId=" + jugadorId + ", nombre='" + nombre + "'}";
    }
}
