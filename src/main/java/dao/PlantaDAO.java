package dao;

import Database.DBManager;
import entidades.Planta;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlantaDAO {
    private final DBManager dbManager;

    public PlantaDAO(DBManager dbManager) {
        this.dbManager = dbManager;
    }
    
    public List<Planta> findAll() {
        String sql = "SELECT * FROM plantas ORDER BY plantaId";
        List<Map<String, Object>> rows = dbManager.runSelect(sql);
        List<Planta> plantas = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            plantas.add(mapRow(row));
        }
        return plantas;
    }
    
    public Planta findById(int plantaId) {
        String sql = "SELECT * FROM plantas WHERE plantaId = ?";
        List<Map<String, Object>> rows = dbManager.runSelect(sql, plantaId);
        if (rows.isEmpty()) return null;
        return mapRow(rows.get(0));
    }

    private Planta mapRow(Map<String, Object> row) {
        Planta p = new Planta();
        p.setPlantaId(((Number) row.get("plantaId")).intValue());
        p.setNombre((String) row.get("nombre"));
        p.setCostoSol(((Number) row.get("costo_sol")).intValue());
        p.setDano(((Number) row.get("dano")).intValue());
        p.setRutaSprite((String) row.get("ruta_sprite"));
        return p;
    }
}
