package entidades;

import java.time.LocalDateTime;

public class Partida {
    private int partidaId;
    private int jugadorId;
    private int oleadasSuperadas;
    private int zombiesEliminados;
    private int plantasPerdidas;
    private int puntuacion;
    private LocalDateTime fecha;

    public Partida() {}

    public Partida(int jugadorId) {
        this.jugadorId = jugadorId;
    }

    public int getPartidaId() { return partidaId; }
    public void setPartidaId(int partidaId) { this.partidaId = partidaId; }

    public int getJugadorId() { return jugadorId; }
    public void setJugadorId(int jugadorId) { this.jugadorId = jugadorId; }

    public int getOleadasSuperadas() { return oleadasSuperadas; }
    public void setOleadasSuperadas(int oleadasSuperadas) { this.oleadasSuperadas = oleadasSuperadas; }

    public int getZombiesEliminados() { return zombiesEliminados; }
    public void setZombiesEliminados(int zombiesEliminados) { this.zombiesEliminados = zombiesEliminados; }

    public int getPlantasPerdidas() { return plantasPerdidas; }
    public void setPlantasPerdidas(int plantasPerdidas) { this.plantasPerdidas = plantasPerdidas; }

    public int getPuntuacion() { return puntuacion; }
    public void setPuntuacion(int puntuacion) { this.puntuacion = puntuacion; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
