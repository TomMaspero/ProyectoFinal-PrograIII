# Documentación — Suite de Tests

## Descripción general

El proyecto utiliza **JUnit 5 (Jupiter)** para las pruebas unitarias de la capa de base de datos. Los tests verifican la conexión con MySQL, los métodos CRUD de `DBManager`, y las operaciones de los DAOs.

Los tests requieren que la base de datos `PlantsVsZombies` esté corriendo en `localhost:3306` con usuario `root` y contraseña vacía, y que las 5 tablas estén creadas.

---

## Dependencias agregadas (`pom.xml`)

```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.10.2</version>
    <scope>test</scope>
</dependency>

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>3.2.5</version>
        </plugin>
    </plugins>
</build>
```

El plugin `maven-surefire-plugin` es necesario para que Maven detecte y ejecute tests de JUnit 5.

---

## Estructura de archivos

```
src/test/java/
  Database/
    DBConnectTest.java      — tests de conexión
    DBManagerTest.java      — tests de los métodos CRUD
  dao/
    JugadorDAOTest.java     — tests del DAO de jugadores
    PlantaDAOTest.java      — tests del DAO de plantas (datos sembrados)
```

Cada clase de test declara su propia conexión en un método `@BeforeAll` estático. No se utiliza herencia entre clases de test.

---

## Cómo ejecutar los tests

### Desde la terminal (Maven)
```
mvn test
```

### Desde NetBeans
Click derecho sobre el proyecto → **Test**
O sobre un archivo de test específico → **Test File**

---

## Clases de test

### `Database/DBConnectTest`

Verifica que la conexión con la base de datos se establece correctamente. Es el primer test a ejecutar cuando hay problemas de conectividad — si estos fallan, los demás también fallarán.

| Test | Qué verifica |
|---|---|
| `testConnectionNotNull` | `getConnection()` no devuelve `null` |
| `testConnectionIsValid` | `connection.isValid(5)` devuelve `true` (responde en menos de 5 segundos) |
| `testDriverLoads` | `loadDriver()` no lanza ninguna excepción |

---

### `Database/DBManagerTest`

Verifica los tres métodos de `DBManager` contra la base de datos real. Inserta filas de prueba con `nombre = '__test__'` y las elimina en `@AfterEach` para no dejar datos sucios.

| Test | Qué verifica |
|---|---|
| `testSelectReturnsNonNullList` | `runSelect()` nunca devuelve `null`, aunque la tabla esté vacía |
| `testInsertReturnsGeneratedKey` | `runInsert()` devuelve un entero positivo (la clave autogenerada) |
| `testUpdateReturnsAffectedRows` | `runUpdate()` (UPDATE) devuelve `1` al modificar una fila existente |
| `testDeleteReturnsAffectedRows` | `runUpdate()` (DELETE) devuelve `1` al eliminar una fila existente |

**Limpieza:** `@AfterEach` ejecuta `DELETE FROM jugadores WHERE nombre = '__test__'` para que cada test comience con un estado predecible.

---

### `dao/JugadorDAOTest`

Verifica las operaciones save/find de `JugadorDAO`. Igual que `DBManagerTest`, usa `nombre = '__test__'` para los datos de prueba y los elimina tras cada test.

| Test | Qué verifica |
|---|---|
| `testSaveReturnsPositiveId` | `save()` devuelve un id positivo y lo setea en la entidad |
| `testFindByNombreReturnsJugador` | Tras un `save()`, `findByNombre()` encuentra el jugador con el nombre correcto |
| `testFindByIdReturnsJugador` | Tras un `save()`, `findById()` encuentra el jugador con el nombre correcto |
| `testFindByNombreReturnsNullWhenMissing` | `findByNombre()` devuelve `null` para un nombre que no existe |
| `testFindByIdReturnsNullWhenMissing` | `findById(999999)` devuelve `null` |

---

### `dao/PlantaDAOTest`

Verifica `PlantaDAO` contra las dos filas sembradas en la tabla `plantas` (Peashooter y Sunflower). No se modifican datos, por lo que no necesita limpieza.

| Test | Qué verifica |
|---|---|
| `testFindAllReturnsTwoPlants` | `findAll()` devuelve exactamente 2 plantas |
| `testFindByIdReturnsPeashooter` | `findById(1)` devuelve una planta con `nombre = "Peashooter"` y `costoSol = 100` |
| `testFindByIdReturnsSunflower` | `findById(2)` devuelve una planta con `nombre = "Sunflower"` y `costoSol = 50` |
| `testFindByIdReturnsNullWhenMissing` | `findById(999999)` devuelve `null` |
