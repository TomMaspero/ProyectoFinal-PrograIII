package Database;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

public class DBConnectTest {

    static DBConnect dbConnect;

    @BeforeAll
    static void setup() {
        dbConnect = new DBConnect("jdbc:mysql://localhost:3306/PlantsVsZombies", "root", "");
    }

    @Test
    void testConnectionNotNull() {
        assertNotNull(dbConnect.getConnection(),
                "La conexión no debe ser null luego de la inicialización");
    }

    @Test
    void testConnectionIsValid() throws SQLException {
        assertTrue(dbConnect.getConnection().isValid(5),
                "La conexión debe ser válida (responde dentro de 5 segundos)");
    }

    @Test
    void testDriverLoads() {
        assertDoesNotThrow(() -> dbConnect.loadDriver(),
                "loadDriver() no debe tirar ninguna excepción");
    }
}
