package dao;

import Database.DBManager;
import entidades.Puntaje;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PuntajeDAO {
    private final DBManager dbManager;

    public PuntajeDAO(DBManager dbManager) {
        this.dbManager = dbManager;
    }
    
    public int save(Puntaje puntaje) {
        String sql = "INSERT INTO puntajes (jugadorId, puntuacion) VALUES (?, ?)";
        int generatedId = dbManager.runInsert(sql,
                puntaje.getJugadorId(),
                puntaje.getPuntuacion());
        if (generatedId != -1) {
            puntaje.setPuntajeId(generatedId);
        }
        return generatedId;
    }
    
    public List<Puntaje> getTopN(int n) {
        String sql = "SELECT p.*, j.nombre FROM puntajes p "
                   + "JOIN jugadores j ON p.jugadorId = j.jugadorId "
                   + "ORDER BY p.puntuacion DESC LIMIT ?";
        List<Map<String, Object>> rows = dbManager.runSelect(sql, n);
        List<Puntaje> puntajes = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            puntajes.add(mapRow(row));
        }
        return puntajes;
    }
    
    public List<Puntaje> findByJugador(int jugadorId) {
        String sql = "SELECT * FROM puntajes WHERE jugadorId = ? ORDER BY fecha DESC";
        List<Map<String, Object>> rows = dbManager.runSelect(sql, jugadorId);
        List<Puntaje> puntajes = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            puntajes.add(mapRow(row));
        }
        return puntajes;
    }

    private static LocalDateTime toLocalDateTime(Object value) {
        if (value instanceof LocalDateTime ldt) return ldt;
        if (value instanceof java.sql.Timestamp ts) return ts.toLocalDateTime();
        return null;
    }

    private Puntaje mapRow(Map<String, Object> row) {
        Puntaje p = new Puntaje();
        p.setPuntajeId(((Number) row.get("puntajeId")).intValue());
        p.setJugadorId(((Number) row.get("jugadorId")).intValue());
        p.setPuntuacion(((Number) row.get("puntuacion")).intValue());
        p.setFecha(toLocalDateTime(row.get("fecha")));
        return p;
    }
}
