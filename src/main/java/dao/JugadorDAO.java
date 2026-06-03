package dao;

import Database.DBManager;
import entidades.Jugador;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class JugadorDAO {
    private final DBManager dbManager;

    public JugadorDAO(DBManager dbManager) {
        this.dbManager = dbManager;
    }
    
    public int save(Jugador jugador) {
        String sql = "INSERT INTO jugadores (nombre) VALUES (?)";
        int generatedId = dbManager.runInsert(sql, jugador.getNombre());
        if (generatedId != -1) {
            jugador.setJugadorId(generatedId);
        }
        return generatedId;
    }
    
    public Jugador findByNombre(String nombre) {
        String sql = "SELECT * FROM jugadores WHERE nombre = ?";
        List<Map<String, Object>> rows = dbManager.runSelect(sql, nombre);
        if (rows.isEmpty()) return null;
        return mapRow(rows.get(0));
    }
    
    public Jugador findById(int jugadorId) {
        String sql = "SELECT * FROM jugadores WHERE jugadorId = ?";
        List<Map<String, Object>> rows = dbManager.runSelect(sql, jugadorId);
        if (rows.isEmpty()) return null;
        return mapRow(rows.get(0));
    }

    /** MySQL 8.x connector returns LocalDateTime for DATETIME; older drivers return Timestamp. */
    private static LocalDateTime toLocalDateTime(Object value) {
        if (value instanceof LocalDateTime ldt) return ldt;
        if (value instanceof java.sql.Timestamp ts) return ts.toLocalDateTime();
        return null;
    }

    private Jugador mapRow(Map<String, Object> row) {
        Jugador j = new Jugador();
        j.setJugadorId(((Number) row.get("jugadorId")).intValue());
        j.setNombre((String) row.get("nombre"));
        j.setFechaRegistro(toLocalDateTime(row.get("fecha_registro")));
        return j;
    }
}
