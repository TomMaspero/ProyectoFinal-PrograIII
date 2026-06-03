package Database;
import java.sql.*;

public final class DBConnect {
    private final String connectionString;
    private final String dbUsername;
    private final String dbPassword;
    private Connection connection;
    
    public DBConnect(String connectionString, String dbUsername, String dbPassword){
        this.connectionString = connectionString;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
        
        try {
            loadDriver();
            connectDB();    
        }
        catch (SQLException sqlEx){
            System.out.println("SQL Connection error");
        }
        catch (ClassNotFoundException classEx){
            System.out.println("Driver error");
        }
    }
    
    public void loadDriver() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("Driver loaded");
    }
    
    public void connectDB() throws SQLException, ClassNotFoundException {
        connection = DriverManager.getConnection(connectionString, dbUsername, dbPassword);
        System.out.println("Database connected");
    }
    
    public ResultSet runQuery(String query){
        ResultSet resultSet = null;
        
        try{
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        }
        catch (SQLException sqlEx) {
            System.out.println("Error while running query");
        }
        
        return resultSet;
    }
}

