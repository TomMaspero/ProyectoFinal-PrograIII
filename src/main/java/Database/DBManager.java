package Database;
import java.sql.*;

public class DBManager {
    private final DBConnect dbConnect;
    
    public DBManager(DBConnect dbConnect){
        this.dbConnect = dbConnect;
    }
    
    // TODO: Retornar una List<Map<String, String>> ?
    public void RunSelect(String table, String columns, String join, String where, String orderBy){
        if (table == null || table.isEmpty()){
            System.out.println("Error: Table is null or empty");
            return;
        }
        
        String query = "SELECT "
                + ((columns == null || columns.isEmpty()) ? "*" : columns)
                + " FROM " + table
                + ((join != null && !join.isEmpty()) ? " " + join : "")
                + ((where != null && !where.isEmpty()) ? " " + where : "")
                + ((orderBy != null && !orderBy.isEmpty()) ? " " + orderBy : ""
        );
        
        try{
            ResultSet rs = dbConnect.runQuery(query);
            
            if (rs == null){
                System.out.println("Error: result set is null");
                System.out.println("Executed Query: " + query);
                return;
            }
            
            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();
            
            // print header row
            for (int i = 1; i <= columnCount; i++){
                System.out.printf("%-30s", meta.getColumnName(i));
            }
            System.out.println();
            System.out.println("-".repeat(30 * columnCount));
            
            while (rs.next()){
                for (int i = 1; i <= columnCount; i++){
                    System.out.printf("%-30s", rs.getString(i));
                }
                
                System.out.println("");
            }
        } catch (SQLException sqlEx){
            sqlEx.printStackTrace();
        }
    }
}
