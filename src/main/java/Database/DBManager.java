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
            }
        } catch (SQLException sqlEx){
            sqlEx.printStackTrace();
        }
    }
}
