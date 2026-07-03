-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 03, 2026 at 06:15 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `plantsvszombies`
--

-- --------------------------------------------------------

--
-- Table structure for table `configuracion`
--

CREATE TABLE `configuracion` (
  `jugadorId` int(11) NOT NULL,
  `volumen` int(11) DEFAULT 50,
  `dificultad` varchar(20) DEFAULT 'NORMAL',
  `pantalla_completa` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `enemigos`
--

CREATE TABLE `enemigos` (
  `enemigoId` int(11) NOT NULL,
  `nombre` varchar(20) NOT NULL,
  `vida` int(11) NOT NULL,
  `puntaje` int(11) NOT NULL,
  `peso` int(11) NOT NULL,
  `ruta_sprite` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `enemigos`
--

INSERT INTO `enemigos` (`enemigoId`, `nombre`, `vida`, `puntaje`, `peso`, `ruta_sprite`) VALUES
(1, 'normal', 100, 1, 121, 'zombieAtlasEdit.png'),
(2, 'cono', 200, 3, 50, 'coneZombieAtlasEdit.png'),
(3, 'balde', 400, 5, 25, 'bucketZombieAtlasEdit.png'),
(4, 'bandera', 140, 10, 4, 'flagZombieAtlasEdit.png');

-- --------------------------------------------------------

--
-- Table structure for table `jugadores`
--

CREATE TABLE `jugadores` (
  `jugadorId` int(11) NOT NULL,
  `nombre` varchar(20) NOT NULL,
  `fecha_registro` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `partidas`
--

CREATE TABLE `partidas` (
  `partidaId` int(11) NOT NULL,
  `jugadorId` int(11) NOT NULL,
  `oleadas_superadas` int(11) DEFAULT 0,
  `zombies_eliminados` int(11) DEFAULT 0,
  `plantas_perdidas` int(11) DEFAULT 0,
  `fecha` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `plantas`
--

CREATE TABLE `plantas` (
  `plantaId` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `costo_sol` int(11) NOT NULL,
  `dano` int(11) DEFAULT 0,
  `ruta_sprite` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `plantas`
--

INSERT INTO `plantas` (`plantaId`, `nombre`, `costo_sol`, `dano`, `ruta_sprite`) VALUES
(1, 'Peashooter', 100, 20, 'peaAtlas.png'),
(2, 'Sunflower', 50, 0, 'sunflower.png');

-- --------------------------------------------------------

--
-- Table structure for table `puntajes`
--

CREATE TABLE `puntajes` (
  `puntajeId` int(11) NOT NULL,
  `jugadorId` int(11) NOT NULL,
  `puntuacion` int(11) NOT NULL,
  `fecha` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `configuracion`
--
ALTER TABLE `configuracion`
  ADD PRIMARY KEY (`jugadorId`);

--
-- Indexes for table `enemigos`
--
ALTER TABLE `enemigos`
  ADD PRIMARY KEY (`enemigoId`);

--
-- Indexes for table `jugadores`
--
ALTER TABLE `jugadores`
  ADD PRIMARY KEY (`jugadorId`),
  ADD UNIQUE KEY `nombre` (`nombre`);

--
-- Indexes for table `partidas`
--
ALTER TABLE `partidas`
  ADD PRIMARY KEY (`partidaId`),
  ADD KEY `jugadorId` (`jugadorId`);

--
-- Indexes for table `plantas`
--
ALTER TABLE `plantas`
  ADD PRIMARY KEY (`plantaId`);

--
-- Indexes for table `puntajes`
--
ALTER TABLE `puntajes`
  ADD PRIMARY KEY (`puntajeId`),
  ADD KEY `jugadorId` (`jugadorId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `enemigos`
--
ALTER TABLE `enemigos`
  MODIFY `enemigoId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `jugadores`
--
ALTER TABLE `jugadores`
  MODIFY `jugadorId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;

--
-- AUTO_INCREMENT for table `partidas`
--
ALTER TABLE `partidas`
  MODIFY `partidaId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `plantas`
--
ALTER TABLE `plantas`
  MODIFY `plantaId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `puntajes`
--
ALTER TABLE `puntajes`
  MODIFY `puntajeId` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `configuracion`
--
ALTER TABLE `configuracion`
  ADD CONSTRAINT `configuracion_ibfk_1` FOREIGN KEY (`jugadorId`) REFERENCES `jugadores` (`jugadorId`);

--
-- Constraints for table `partidas`
--
ALTER TABLE `partidas`
  ADD CONSTRAINT `partidas_ibfk_1` FOREIGN KEY (`jugadorId`) REFERENCES `jugadores` (`jugadorId`);

--
-- Constraints for table `puntajes`
--
ALTER TABLE `puntajes`
  ADD CONSTRAINT `puntajes_ibfk_1` FOREIGN KEY (`jugadorId`) REFERENCES `jugadores` (`jugadorId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
