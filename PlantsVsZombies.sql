CREATE TABLE jugadores (
    jugadorId INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL UNIQUE,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE partidas (
    partidaId INT AUTO_INCREMENT PRIMARY KEY,
    jugadorId INT NOT NULL,
    oleadas_superadas INT DEFAULT 0,
    zombies_eliminados INT DEFAULT 0,
    plantas_perdidas INT DEFAULT 0,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (jugadorId) REFERENCES jugadores(jugadorId)
);

CREATE TABLE puntajes (
    puntajeId INT AUTO_INCREMENT PRIMARY KEY,
    jugadorId INT NOT NULL,
    puntuacion INT NOT NULL,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (jugadorId) REFERENCES jugadores(jugadorId)
);

CREATE TABLE configuracion (
    jugadorId INT PRIMARY KEY,
    volumen INT DEFAULT 50,
    dificultad VARCHAR(20) DEFAULT 'NORMAL',
    pantalla_completa BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (jugadorId) REFERENCES jugadores(jugadorId)
);

CREATE TABLE plantas (
    plantaId INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    costo_sol INT NOT NULL,
    dano INT DEFAULT 0,
    velocidad_ataque DECIMAL(4,2) DEFAULT 0.0,
    ruta_sprite VARCHAR(100)
);

INSERT INTO plantas (nombre, costo_sol, dano, velocidad_ataque, ruta_sprite)
VALUES ('Peashooter', 100, 20, 1.5, 'peaAtlas.png'),
       ('Sunflower',  50,  0, 0.0, 'sunflower.png');
