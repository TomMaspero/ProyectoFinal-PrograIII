package Database;

import java.sql.*;
import java.util.*;

public class DBManager {
    private final DBConnect dbConnect;

    public DBManager(DBConnect dbConnect) {
        this.dbConnect = dbConnect;
    }

    /**
     * Ejecuta una query SELECT con parámetros opcionales
     * Retorna una LinkedHashMap<columnName, value> con las filas
     */
    public List<Map<String, Object>> runSelect(String sql, Object... params) {
        List<Map<String, Object>> results = new ArrayList<>();
        
        try (PreparedStatement ps = dbConnect.getConnection().prepareStatement(sql)) {
            setParams(ps, params);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            
            int cols = meta.getColumnCount();
            
            while (rs.next()) {
                Map<String, Object> row = new LinkedHashMap<>();
                for (int i = 1; i <= cols; i++) {
                    row.put(meta.getColumnName(i), rs.getObject(i));
                }
                results.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return results;
    }

    /**
     * Ejecuta un INSERT y retorna la Primary Key autogenerada (ó -1 si falla)
     */
    public int runInsert(String sql, Object... params) {
        try (PreparedStatement ps = dbConnect.getConnection()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setParams(ps, params);
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            
            if (keys.next()) {
                return keys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return -1;
    }

    /**
     * Ejecuta un UPDATE o DELETE y retorna el numero de filas afectadas
     */
    public int runUpdate(String sql, Object... params) {
        try (PreparedStatement ps = dbConnect.getConnection().prepareStatement(sql)) {
            setParams(ps, params);
            
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }

    private void setParams(PreparedStatement ps, Object[] params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }
    }
}
