package dao;

import Database.DBManager;
import entidades.TipoEnemigo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EnemigoDAO {
    private final DBManager dbManager;
    
    public EnemigoDAO(DBManager dbManager) {
        this.dbManager = dbManager;
    }
    
    public List<TipoEnemigo> findAll() {
        String sql = "SELECT * FROM enemigos ORDER BY enemigoId";
        List<Map<String, Object>> rows = dbManager.runSelect(sql);
        List<TipoEnemigo> tipos = new ArrayList<>();
        
        for (Map<String, Object> row : rows) {
            tipos.add(mapRow(row));
        }
        
        return tipos;
    }
    
    private TipoEnemigo mapRow(Map<String, Object> row) {
        TipoEnemigo t = new TipoEnemigo();
        
        t.setEnemigoId(((Number) row.get("enemigoId")).intValue());
        t.setNombre((String) row.get("nombre"));
        t.setVida(((Number) row.get("vida")).intValue());
        t.setPuntaje(((Number) row.get("puntaje")).intValue());
        t.setRutaSprite((String) row.get("ruta_sprite"));
        t.setPeso(((Number) row.get("peso")).intValue());
        
        return t;
    }
}
