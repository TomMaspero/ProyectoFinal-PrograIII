package Database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class DBManagerTest {

    static DBConnect dbConnect;
    static DBManager dbManager;

    private static final String TEST_NOMBRE = "__test__";

    @BeforeAll
    static void setup() {
        dbConnect = new DBConnect("jdbc:mysql://localhost:3306/PlantsVsZombies", "root", "");
        dbManager = new DBManager(dbConnect);
    }

    @AfterEach
    void cleanup() {
        dbManager.runUpdate("DELETE FROM jugadores WHERE nombre = ?", TEST_NOMBRE);
    }

    @Test
    void testSelectReturnsNonNullList() {
        List<Map<String, Object>> result = dbManager.runSelect("SELECT * FROM jugadores");
        assertNotNull(result, "runSelect no debe retornar NULL");
        assertInstanceOf(List.class, result);
    }

    @Test
    void testInsertReturnsGeneratedKey() {
        int id = dbManager.runInsert("INSERT INTO jugadores (nombre) VALUES (?)", TEST_NOMBRE);
        assertTrue(id > 0, "runInsert debe devolver una clave positiva autogenerada");
    }

    @Test
    void testUpdateReturnsAffectedRows() {
        int id = dbManager.runInsert("INSERT INTO jugadores (nombre) VALUES (?)", TEST_NOMBRE);
        int rowsAffected = dbManager.runUpdate(
                "UPDATE jugadores SET nombre = ? WHERE jugadorId = ?",
                TEST_NOMBRE, id);
        assertEquals(1, rowsAffected, "runUpdate debe retornar 1 fila modificada cuando se busca por ID");
    }

    @Test
    void testDeleteReturnsAffectedRows() {
        dbManager.runInsert("INSERT INTO jugadores (nombre) VALUES (?)", TEST_NOMBRE);
        int rowsAffected = dbManager.runUpdate(
                "DELETE FROM jugadores WHERE nombre = ?", TEST_NOMBRE);
        assertEquals(1, rowsAffected, "runUpdate (DELETE) debe retornar 1 sola fila eliminada cuando se busca por ID");
    }
}
