package dao;

import Database.DBConnect;
import Database.DBManager;
import entidades.Jugador;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JugadorDAOTest {

    static DBManager dbManager;
    static JugadorDAO jugadorDAO;

    private static final String TEST_NOMBRE = "__test__";

    @BeforeAll
    static void setup() {
        DBConnect dbConnect = new DBConnect("jdbc:mysql://localhost:3306/PlantsVsZombies", "root", "");
        dbManager = new DBManager(dbConnect);
        jugadorDAO = new JugadorDAO(dbManager);
    }

    @AfterEach
    void cleanup() {
        dbManager.runUpdate("DELETE FROM jugadores WHERE nombre = ?", TEST_NOMBRE);
    }

    @Test
    void testSaveReturnsPositiveId() {
        Jugador jugador = new Jugador(TEST_NOMBRE);
        int id = jugadorDAO.save(jugador);
        assertTrue(id > 0, "save() debe retornar un entero positivo autogenerado");
        assertEquals(id, jugador.getJugadorId(), "save() debe setear jugadorId en la entidad");
    }

    @Test
    void testFindByNombreReturnsJugador() {
        jugadorDAO.save(new Jugador(TEST_NOMBRE));
        Jugador found = jugadorDAO.findByNombre(TEST_NOMBRE);
        assertNotNull(found, "findByNombre debe retornar el jugador agregado");
        assertEquals(TEST_NOMBRE, found.getNombre());
    }

    @Test
    void testFindByIdReturnsJugador() {
        Jugador jugador = new Jugador(TEST_NOMBRE);
        jugadorDAO.save(jugador);
        Jugador found = jugadorDAO.findById(jugador.getJugadorId());
        assertNotNull(found, "findById debe retornar el jugador agregado");
        assertEquals(TEST_NOMBRE, found.getNombre());
    }

    @Test
    void testFindByNombreReturnsNullWhenMissing() {
        Jugador found = jugadorDAO.findByNombre("nonexistent_xyz_999");
        assertNull(found, "findByNombre debe retornar NULL cuando el nombre no existe");
    }

    @Test
    void testFindByIdReturnsNullWhenMissing() {
        Jugador found = jugadorDAO.findById(999999);
        assertNull(found, "findById debe retornar NULL cuando el ID no existe");
    }
}
