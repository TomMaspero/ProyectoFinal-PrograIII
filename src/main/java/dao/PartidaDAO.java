package dao;

import Database.DBManager;
import entidades.Partida;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PartidaDAO {
    private final DBManager dbManager;

    public PartidaDAO(DBManager dbManager) {
        this.dbManager = dbManager;
    }
    
    public int save(Partida partida) {
        String sql = "INSERT INTO partidas (jugadorId, oleadas_superadas, zombies_eliminados, plantas_perdidas, puntuacion) "
                   + "VALUES (?, ?, ?, ?, ?)";
        int generatedId = dbManager.runInsert(sql,
                partida.getJugadorId(),
                partida.getOleadasSuperadas(),
                partida.getZombiesEliminados(),
                partida.getPlantasPerdidas(),
                partida.getPuntuacion());
        if (generatedId != -1) {
            partida.setPartidaId(generatedId);
        }
        return generatedId;
    }

    public List<Partida> findByJugador(int jugadorId) {
        String sql = "SELECT * FROM partidas WHERE jugadorId = ? ORDER BY fecha DESC";
        List<Map<String, Object>> rows = dbManager.runSelect(sql, jugadorId);
        List<Partida> partidas = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            partidas.add(mapRow(row));
        }
        return partidas;
    }

    /** Puntajes totales por jugador, para la pantalla de Highscores. */
    public List<Map<String, Object>> findHighscores() {
        String sql = "SELECT j.nombre AS nombre, "
                   + "SUM(p.zombies_eliminados) AS zombies_eliminados, "
                   + "SUM(p.puntuacion) AS puntuacion "
                   + "FROM partidas p "
                   + "JOIN jugadores j ON p.jugadorId = j.jugadorId "
                   + "GROUP BY j.jugadorId, j.nombre "
                   + "ORDER BY puntuacion DESC";
        return dbManager.runSelect(sql);
    }

    private static LocalDateTime toLocalDateTime(Object value) {
        if (value instanceof LocalDateTime ldt) return ldt;
        if (value instanceof java.sql.Timestamp ts) return ts.toLocalDateTime();
        return null;
    }

    private Partida mapRow(Map<String, Object> row) {
        Partida p = new Partida();
        p.setPartidaId(((Number) row.get("partidaId")).intValue());
        p.setJugadorId(((Number) row.get("jugadorId")).intValue());
        p.setOleadasSuperadas(((Number) row.get("oleadas_superadas")).intValue());
        p.setZombiesEliminados(((Number) row.get("zombies_eliminados")).intValue());
        p.setPlantasPerdidas(((Number) row.get("plantas_perdidas")).intValue());
        p.setPuntuacion(((Number) row.get("puntuacion")).intValue());
        p.setFecha(toLocalDateTime(row.get("fecha")));
        return p;
    }
}
