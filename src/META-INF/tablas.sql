-- phpMyAdmin SQL Dump
-- version 4.4.12
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 21-10-2015 a las 22:16:43
-- Versión del servidor: 5.6.25
-- Versión de PHP: 5.6.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `obligatorio_main`
--

-- --------------------------------------------------------


--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE IF NOT EXISTS `categoria` (
  `idCategoria` bigint(20) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria_productos`
--

CREATE TABLE IF NOT EXISTS `categoria_productos` (
  `categoria` bigint(20) NOT NULL,
  `idCategoria` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `nota`
--

CREATE TABLE IF NOT EXISTS `nota` (
  `idNota` bigint(20) NOT NULL,
  `texto` varchar(255) DEFAULT NULL,
  `usuario` tinyblob
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `notificacion`
--

CREATE TABLE IF NOT EXISTS `notificacion` (
  `idNotificacion` bigint(20) NOT NULL,
  `leido` bit(1) NOT NULL,
  `texto` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE IF NOT EXISTS `producto` (
  `idProducto` bigint(20) NOT NULL,
  `atributos` varchar(255) NOT NULL,
  `bytesImagen` longblob,
  `descripcion` varchar(255) DEFAULT NULL,
  `idAV` bigint(20) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `nombreImagen` varchar(255) DEFAULT NULL,
  `precio` double NOT NULL,
  `stock` int(11) NOT NULL,
  `categoria_idCategoria` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`idCategoria`);

--
-- Indices de la tabla `categoria_productos`
--
ALTER TABLE `categoria_productos`
  ADD UNIQUE KEY `UK_pf8dhv8fvo0g0t5h040ann58x` (`idCategoria`),
  ADD KEY `FK_jmsdgvlcwrq3ynin411rc25il` (`categoria`);

--
-- Indices de la tabla `nota`
--
ALTER TABLE `nota`
  ADD PRIMARY KEY (`idNota`);

--
-- Indices de la tabla `notificacion`
--
ALTER TABLE `notificacion`
  ADD PRIMARY KEY (`idNotificacion`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`idProducto`),
  ADD KEY `FK_1qlkl3me6n77angtfpnd5gcln` (`categoria_idCategoria`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `idCategoria` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `nota`
--
ALTER TABLE `nota`
  MODIFY `idNota` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `notificacion`
--
ALTER TABLE `notificacion`
  MODIFY `idNotificacion` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `idProducto` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `categoria_productos`
--
ALTER TABLE `categoria_productos`
  ADD CONSTRAINT `FK_jmsdgvlcwrq3ynin411rc25il` FOREIGN KEY (`categoria`) REFERENCES `categoria` (`idCategoria`),
  ADD CONSTRAINT `FK_pf8dhv8fvo0g0t5h040ann58x` FOREIGN KEY (`idCategoria`) REFERENCES `producto` (`idProducto`);

--
-- Filtros para la tabla `producto`
--
ALTER TABLE `producto`
  ADD CONSTRAINT `FK_1qlkl3me6n77angtfpnd5gcln` FOREIGN KEY (`categoria_idCategoria`) REFERENCES `categoria` (`idCategoria`);


