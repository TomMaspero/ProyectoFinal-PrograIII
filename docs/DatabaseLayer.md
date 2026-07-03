# Documentación — Capa de Base de Datos

## Descripción general

La capa de base de datos del proyecto está compuesta por tres niveles:

1. **Conexión** (`Database/`) — establece y gestiona la conexión JDBC con MySQL.
2. **Entidades** (`entidades/`) — clases POJO que representan cada tabla.
3. **DAOs** (`dao/`) — clases de acceso a datos que ejecutan las consultas.

La tecnología utilizada es JDBC puro (sin ORM). La base de datos es MySQL y se conecta a `PlantsVsZombies` en `localhost:3306`.

---

## Esquema de la base de datos

### `jugadores`
Almacena las cuentas de los jugadores.

```sql
CREATE TABLE jugadores (
    jugadorId      INT AUTO_INCREMENT PRIMARY KEY,
    nombre         VARCHAR(20) NOT NULL UNIQUE,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP
);
```

### `partidas`
Registra el historial de partidas jugadas.

```sql
CREATE TABLE partidas (
    partidaId          INT AUTO_INCREMENT PRIMARY KEY,
    jugadorId          INT NOT NULL,
    oleadas_superadas  INT DEFAULT 0,
    zombies_eliminados INT DEFAULT 0,
    plantas_perdidas   INT DEFAULT 0,
    fecha              DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (jugadorId) REFERENCES jugadores(jugadorId)
);
```

### `puntajes`
Tabla de puntuaciones / leaderboard.

```sql
CREATE TABLE puntajes (
    puntajeId  INT AUTO_INCREMENT PRIMARY KEY,
    jugadorId  INT NOT NULL,
    puntuacion INT NOT NULL,
    fecha      DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (jugadorId) REFERENCES jugadores(jugadorId)
);
```

### `configuracion`
Preferencias de cada jugador (una fila por jugador).

```sql
CREATE TABLE configuracion (
    jugadorId       INT PRIMARY KEY,
    volumen         INT DEFAULT 50,
    dificultad      VARCHAR(20) DEFAULT 'NORMAL',
    pantalla_completa BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (jugadorId) REFERENCES jugadores(jugadorId)
);
```

### `plantas`
Catálogo de plantas disponibles en el juego. Tiene dos filas sembradas por defecto.

```sql
CREATE TABLE plantas (
    plantaId        INT AUTO_INCREMENT PRIMARY KEY,
    nombre          VARCHAR(50) NOT NULL,
    costo_sol       INT NOT NULL,
    dano            INT DEFAULT 0,
    ruta_sprite     VARCHAR(100)
);

INSERT INTO plantas (nombre, costo_sol, dano, ruta_sprite)
VALUES ('Peashooter', 100, 20, 'peaAtlas.png'),
       ('Sunflower',  50,  0, 'sunflower.png'),
       ('Pala',       0,   0, 'pala.png');
```

---

## Clases de conexión (`Database/`)

### `DBConnect`
Gestiona la conexión física con MySQL usando JDBC.

| Elemento | Descripción |
|---|---|
| Constructor `DBConnect(url, user, password)` | Carga el driver y abre la conexión en un solo paso |
| `loadDriver()` | Carga `com.mysql.cj.jdbc.Driver` |
| `connectDB()` | Abre la conexión vía `DriverManager.getConnection()` |
| `getConnection()` | Devuelve el objeto `Connection` activo |
| `runQuery(String sql)` | Ejecuta una consulta SELECT y devuelve un `ResultSet` (uso heredado) |

La conexión se inicializa en `Juego.java` al arrancar el programa:
```java
dbConnect = new DBConnect("jdbc:mysql://localhost:3306/PlantsVsZombies", "root", "");
```

### `DBManager`
Capa de abstracción sobre `DBConnect`. Todos los métodos usan `PreparedStatement` para prevenir inyección SQL.

| Método | Descripción |
|---|---|
| `runSelect(String sql, Object... params)` | Ejecuta un SELECT y devuelve `List<Map<String, Object>>`. Cada mapa representa una fila (clave = nombre de columna). |
| `runInsert(String sql, Object... params)` | Ejecuta un INSERT y devuelve la clave primaria autogenerada (o `-1` si falla). |
| `runUpdate(String sql, Object... params)` | Ejecuta un UPDATE o DELETE y devuelve la cantidad de filas afectadas. |

**Ejemplo de uso:**
```java
// SELECT
List<Map<String, Object>> filas = dbManager.runSelect(
    "SELECT * FROM jugadores WHERE nombre = ?", "Tom");

// INSERT
int id = dbManager.runInsert(
    "INSERT INTO jugadores (nombre) VALUES (?)", "Tom");

// UPDATE
int afectadas = dbManager.runUpdate(
    "UPDATE configuracion SET volumen = ? WHERE jugadorId = ?", 80, 1);
```

---

## Entidades (`entidades/`)

Clases POJO simples. No tienen anotaciones ni dependencias externas. Cada clase refleja exactamente la estructura de su tabla.

### `Jugador`
| Campo | Tipo | Columna |
|---|---|---|
| `jugadorId` | `int` | `jugadorId` (PK) |
| `nombre` | `String` | `nombre` |
| `fechaRegistro` | `LocalDateTime` | `fecha_registro` |

Constructor conveniente: `new Jugador("NombreJugador")`

### `Partida`
| Campo | Tipo | Columna |
|---|---|---|
| `partidaId` | `int` | `partidaId` (PK) |
| `jugadorId` | `int` | `jugadorId` (FK) |
| `oleadasSuperadas` | `int` | `oleadas_superadas` |
| `zombiesEliminados` | `int` | `zombies_eliminados` |
| `plantasPerdidas` | `int` | `plantas_perdidas` |
| `fecha` | `LocalDateTime` | `fecha` |

Constructor conveniente: `new Partida(jugadorId)`

### `Puntaje`
| Campo | Tipo | Columna |
|---|---|---|
| `puntajeId` | `int` | `puntajeId` (PK) |
| `jugadorId` | `int` | `jugadorId` (FK) |
| `puntuacion` | `int` | `puntuacion` |
| `fecha` | `LocalDateTime` | `fecha` |

Constructor conveniente: `new Puntaje(jugadorId, puntuacion)`

### `Configuracion`
| Campo | Tipo | Columna |
|---|---|---|
| `jugadorId` | `int` | `jugadorId` (PK/FK) |
| `volumen` | `int` | `volumen` |
| `dificultad` | `String` | `dificultad` |
| `pantallaCompleta` | `boolean` | `pantalla_completa` |

Constructor conveniente: `new Configuracion(jugadorId)` — inicializa con valores por defecto (volumen 50, dificultad NORMAL).

### `Planta`
| Campo | Tipo | Columna |
|---|---|---|
| `plantaId` | `int` | `plantaId` (PK) |
| `nombre` | `String` | `nombre` |
| `costoSol` | `int` | `costo_sol` |
| `dano` | `int` | `dano` |
| `rutaSprite` | `String` | `ruta_sprite` |

---

## DAOs (`dao/`)

Cada DAO recibe un `DBManager` en su constructor y es instanciado una sola vez en `Juego.java`.

```java
// En Juego.java
jugadorDAO        = new JugadorDAO(dbManager);
partidaDAO        = new PartidaDAO(dbManager);
puntajeDAO        = new PuntajeDAO(dbManager);
configuracionDAO  = new ConfiguracionDAO(dbManager);
plantaDAO         = new PlantaDAO(dbManager);
```

Acceso desde otras escenas vía getters de `Juego`:
```java
juego.getJugadorDAO().findByNombre("Tom");
juego.getPuntajeDAO().getTopN(10);
```

### `JugadorDAO`
| Método | Descripción |
|---|---|
| `save(Jugador)` | Inserta un jugador nuevo. Setea `jugadorId` en el objeto pasado. Devuelve el id generado. |
| `findByNombre(String)` | Busca por nombre. Devuelve `null` si no existe. |
| `findById(int)` | Busca por id. Devuelve `null` si no existe. |

### `PartidaDAO`
| Método | Descripción |
|---|---|
| `save(Partida)` | Guarda una partida finalizada. Setea `partidaId` en el objeto. |
| `findByJugador(int jugadorId)` | Devuelve todas las partidas del jugador, ordenadas de más reciente a más antigua. |

### `PuntajeDAO`
| Método | Descripción |
|---|---|
| `save(Puntaje)` | Guarda una entrada de puntaje. Setea `puntajeId` en el objeto. |
| `getTopN(int n)` | Devuelve los `n` mejores puntajes globales (incluye `nombre` del jugador via JOIN). |
| `findByJugador(int jugadorId)` | Devuelve todos los puntajes de un jugador, ordenados de más reciente a más antiguo. |

### `ConfiguracionDAO`
| Método | Descripción |
|---|---|
| `save(Configuracion)` | Inserta la fila de configuración inicial para un jugador nuevo. |
| `findByJugador(int jugadorId)` | Devuelve la configuración del jugador. Si no existe, devuelve una `Configuracion` con valores por defecto. |
| `update(Configuracion)` | Actualiza los tres campos editables (`volumen`, `dificultad`, `pantalla_completa`). |

### `PlantaDAO`
| Método | Descripción |
|---|---|
| `findAll()` | Devuelve todas las plantas del catálogo, ordenadas por `plantaId`. Usar al iniciar el juego para popular la bandeja de selección. |
| `findById(int plantaId)` | Devuelve una planta por id, o `null` si no existe. |

---

## Nota sobre fechas (`LocalDateTime` vs `Timestamp`)

MySQL Connector/J 8.x devuelve `LocalDateTime` directamente para columnas `DATETIME`. Los DAOs incluyen un helper privado `toLocalDateTime()` que maneja ambos tipos para garantizar compatibilidad:

```java
private static LocalDateTime toLocalDateTime(Object value) {
    if (value instanceof LocalDateTime ldt) return ldt;
    if (value instanceof java.sql.Timestamp ts) return ts.toLocalDateTime();
    return null;
}
```
