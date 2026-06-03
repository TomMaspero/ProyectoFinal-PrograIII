package dao;

import Database.DBManager;
import entidades.Configuracion;
import java.util.List;
import java.util.Map;

public class ConfiguracionDAO {
    private final DBManager dbManager;

    public ConfiguracionDAO(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public void save(Configuracion config) {
        String sql = "INSERT INTO configuracion (jugadorId, volumen, dificultad, pantalla_completa) "
                   + "VALUES (?, ?, ?, ?)";
        dbManager.runInsert(sql,
                config.getJugadorId(),
                config.getVolumen(),
                config.getDificultad(),
                config.isPantallaCompleta());
    }
    
    public Configuracion findByJugador(int jugadorId) {
        String sql = "SELECT * FROM configuracion WHERE jugadorId = ?";
        List<Map<String, Object>> rows = dbManager.runSelect(sql, jugadorId);
        if (rows.isEmpty()) return new Configuracion(jugadorId);
        return mapRow(rows.get(0));
    }
    
    public void update(Configuracion config) {
        String sql = "UPDATE configuracion SET volumen = ?, dificultad = ?, pantalla_completa = ? "
                   + "WHERE jugadorId = ?";
        dbManager.runUpdate(sql,
                config.getVolumen(),
                config.getDificultad(),
                config.isPantallaCompleta(),
                config.getJugadorId());
    }

    private Configuracion mapRow(Map<String, Object> row) {
        Configuracion c = new Configuracion();
        c.setJugadorId(((Number) row.get("jugadorId")).intValue());
        c.setVolumen(((Number) row.get("volumen")).intValue());
        c.setDificultad((String) row.get("dificultad"));
        Object pc = row.get("pantalla_completa");
        c.setPantallaCompleta(pc != null && ((Number) pc).intValue() != 0);
        return c;
    }
}
