SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";
/*!40101 SET NAMES utf8mb4 */;


CREATE TABLE IF NOT EXISTS `categoria` (
  `idCategoria` bigint(20) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idCategoria`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `categoria_productos` (
  `categoria` bigint(20) NOT NULL,
  `idCategoria` bigint(20) NOT NULL,
  UNIQUE KEY `UK_pf8dhv8fvo0g0t5h040ann58x` (`idCategoria`),
  KEY `FK_jmsdgvlcwrq3ynin411rc25il` (`categoria`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `nota` (
  `idNota` bigint(20) NOT NULL AUTO_INCREMENT,
  `texto` varchar(255) DEFAULT NULL,
  `usuario` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idNota`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `notificacion` (
  `idNotificacion` bigint(20) NOT NULL AUTO_INCREMENT,
  `leido` bit(1) NOT NULL,
  `texto` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idNotificacion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `producto` (
  `idProducto` bigint(20) NOT NULL AUTO_INCREMENT,
  `atributos` varchar(255) NOT NULL,
  `bytesImagen` longblob,
  `descripcion` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `nombreImagen` varchar(255) DEFAULT NULL,
  `precio` double NOT NULL,
  `stock` int(11) NOT NULL,
  `categoria_idCategoria` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`idProducto`),
  KEY `FK_1qlkl3me6n77angtfpnd5gcln` (`categoria_idCategoria`),
  CONSTRAINT `FK_1qlkl3me6n77angtfpnd5gcln` FOREIGN KEY (`categoria_idCategoria`) REFERENCES `categoria` (`idCategoria`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
ALTER TABLE `categoria_productos`
  ADD CONSTRAINT `FK_jmsdgvlcwrq3ynin411rc25il` FOREIGN KEY (`categoria`) REFERENCES `categoria` (`idCategoria`),
  ADD CONSTRAINT `FK_pf8dhv8fvo0g0t5h040ann58x` FOREIGN KEY (`idCategoria`) REFERENCES `producto` (`idProducto`);
  
CREATE TABLE IF NOT EXISTS `productoacomprar` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cantidad` int(11) NOT NULL,
  `producto_idProducto` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_gvkd86kcd8aqflwon16hc13vu` (`producto_idProducto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `alerta` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `condicion` varchar(255) NOT NULL,
  `prod_idProducto` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*
CREATE TABLE IF NOT EXISTS `accion` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
*/

CREATE TABLE IF NOT EXISTS `log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `usuario` varchar(255) DEFAULT NULL,
  `accion_id` bigint(20) DEFAULT NULL,
  `objetivo_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*
CREATE TABLE IF NOT EXISTS `objetivo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  `idObjetivo` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
*//*
ALTER TABLE `log`
  ADD KEY `FK_4qxrgm43ttukmy3myom4340di` (`accion_id`),
  ADD KEY `FK_l5s490bot2ufjoka0jwv16jjd` (`objetivo_id`);
*//*
ALTER TABLE `log`
  ADD CONSTRAINT `FK_4qxrgm43ttukmy3myom4340di` FOREIGN KEY (`accion_id`) REFERENCES `accion` (`id`),
  ADD CONSTRAINT `FK_l5s490bot2ufjoka0jwv16jjd` FOREIGN KEY (`objetivo_id`) REFERENCES `objetivo` (`id`);
  */
ALTER TABLE `productoacomprar`
  ADD CONSTRAINT `FK_gvkd86kcd8aqflwon16hc13vu` FOREIGN KEY (`producto_idProducto`) REFERENCES `producto` (`idProducto`);
