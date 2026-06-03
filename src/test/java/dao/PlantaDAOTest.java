package dao;

import Database.DBConnect;
import Database.DBManager;
import entidades.Planta;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class PlantaDAOTest {

    static PlantaDAO plantaDAO;

    @BeforeAll
    static void setup() {
        DBConnect dbConnect = new DBConnect("jdbc:mysql://localhost:3306/PlantsVsZombies", "root", "");
        DBManager dbManager = new DBManager(dbConnect);
        plantaDAO = new PlantaDAO(dbManager);
    }

    @Test
    void testFindAllReturnsTwoPlants() {
        List<Planta> plantas = plantaDAO.findAll();
        assertNotNull(plantas, "findAll() no debe retornar NULL");
        assertEquals(2, plantas.size(), "Debe retornar 2 plantas (lanzaGuisantes y giraSol");
    }

    @Test
    void testFindByIdReturnsPeashooter() {
        Planta planta = plantaDAO.findById(1);
        assertNotNull(planta, "findById(1) debe retornar la primer planta");
        assertEquals("Peashooter", planta.getNombre());
        assertEquals(100, planta.getCostoSol());
    }

    @Test
    void testFindByIdReturnsSunflower() {
        Planta planta = plantaDAO.findById(2);
        assertNotNull(planta, "findById(2) debe retornar la segunda planta");
        assertEquals("Sunflower", planta.getNombre());
        assertEquals(50, planta.getCostoSol());
    }

    @Test
    void testFindByIdReturnsNullWhenMissing() {
        Planta planta = plantaDAO.findById(999999);
        assertNull(planta, "findById debe retornar NULL cuando el ID no existe");
    }
}
