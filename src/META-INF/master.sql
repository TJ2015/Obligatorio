-- phpMyAdmin SQL Dump
-- version 4.4.12
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 22-10-2015 a las 00:26:57
-- Versión del servidor: 5.6.25
-- Versión de PHP: 5.6.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `obligatorio_main`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `av`
--

CREATE TABLE IF NOT EXISTS `av` (
  `idAV` bigint(20) NOT NULL,
  `nombreAV` varchar(255) DEFAULT NULL,
  `usuarioCreador_idUsuario` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `av_nota`
--

CREATE TABLE IF NOT EXISTS `av_nota` (
  `AV_idAV` bigint(20) NOT NULL,
  `notas_idNota` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `av_usuariocreador`
--

CREATE TABLE IF NOT EXISTS `av_usuariocreador` (
  `usuarioCreador` bigint(20) NOT NULL,
  `idUsuario` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `av_usuarioscompartidos`
--

CREATE TABLE IF NOT EXISTS `av_usuarioscompartidos` (
  `AV_idAV` bigint(20) NOT NULL,
  `usuariosCompartidos_idUsuario` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
  `descripcion` varchar(255) DEFAULT NULL,
  `idAV` bigint(20) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `precio` double NOT NULL,
  `stock` int(11) NOT NULL,
  `categoria_idCategoria` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE IF NOT EXISTS `usuario` (
  `idUsuario` bigint(20) NOT NULL,
  `apellido` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fechaNacimiento` datetime DEFAULT NULL,
  `nick` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`idUsuario`, `apellido`, `email`, `fechaNacimiento`, `nick`, `nombre`, `password`) VALUES
(1, 'Perez', 'jp@gmail.com', '2015-10-21 20:22:19', 'jotape', 'Juan', 'jotape'),
(2, 'Gomez', 'rob@gmail.com', '2015-10-21 20:22:19', 'robgom', 'Roberto', 'robgom'),
(3, 'Lopez', 'marlo@gmail.com', '2015-10-21 20:22:19', 'marlo', 'Maria', 'marlo'),
(4, 'Fernandez', 'lucifer@gmail.com', '2015-10-21 20:22:19', 'lucifer', 'Lucia', 'lucifer'),
(5, '1', '1@1.com', '2015-10-21 20:22:19', '1', '1', '1'),
(6, '2', '2@2.com', '2015-10-21 20:22:19', '2', '2', '2');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario_avcompartidos`
--

CREATE TABLE IF NOT EXISTS `usuario_avcompartidos` (
  `Usuario_idUsuario` bigint(20) NOT NULL,
  `AVcompartidos_idAV` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `av`
--
ALTER TABLE `av`
  ADD PRIMARY KEY (`idAV`),
  ADD KEY `FK_ffyfj1yojqu6eujmf6ibsgwqv` (`usuarioCreador_idUsuario`);

--
-- Indices de la tabla `av_nota`
--
ALTER TABLE `av_nota`
  ADD UNIQUE KEY `UK_qrer97e23jw2fjlm8tn9vg7dn` (`notas_idNota`),
  ADD KEY `FK_ovnfce282tbikyp7mi4oc8cw5` (`AV_idAV`);

--
-- Indices de la tabla `av_usuariocreador`
--
ALTER TABLE `av_usuariocreador`
  ADD UNIQUE KEY `UK_lwoiyu0ti935wwepvbxqavfwc` (`idUsuario`),
  ADD KEY `FK_hd70i8on3okwi205jqb8us9w8` (`usuarioCreador`);

--
-- Indices de la tabla `av_usuarioscompartidos`
--
ALTER TABLE `av_usuarioscompartidos`
  ADD KEY `FK_mhgfput6v6wtbj2fex9saopha` (`AV_idAV`),
  ADD KEY `UK_6fs67215r98r91xul672y3a8k` (`usuariosCompartidos_idUsuario`) USING BTREE;

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
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`idUsuario`);

--
-- Indices de la tabla `usuario_avcompartidos`
--
ALTER TABLE `usuario_avcompartidos`
  ADD UNIQUE KEY `UK_31aoc7x2q3fbs7vhqawyw82sm` (`AVcompartidos_idAV`),
  ADD KEY `FK_jm0y6di4c3imh9nq162xtg3db` (`Usuario_idUsuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `av`
--
ALTER TABLE `av`
  MODIFY `idAV` bigint(20) NOT NULL AUTO_INCREMENT;
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
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `idUsuario` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `av`
--
ALTER TABLE `av`
  ADD CONSTRAINT `FK_ffyfj1yojqu6eujmf6ibsgwqv` FOREIGN KEY (`usuarioCreador_idUsuario`) REFERENCES `usuario` (`idUsuario`);

--
-- Filtros para la tabla `av_nota`
--
ALTER TABLE `av_nota`
  ADD CONSTRAINT `FK_ovnfce282tbikyp7mi4oc8cw5` FOREIGN KEY (`AV_idAV`) REFERENCES `av` (`idAV`),
  ADD CONSTRAINT `FK_qrer97e23jw2fjlm8tn9vg7dn` FOREIGN KEY (`notas_idNota`) REFERENCES `nota` (`idNota`);

--
-- Filtros para la tabla `av_usuariocreador`
--
ALTER TABLE `av_usuariocreador`
  ADD CONSTRAINT `FK_hd70i8on3okwi205jqb8us9w8` FOREIGN KEY (`usuarioCreador`) REFERENCES `usuario` (`idUsuario`),
  ADD CONSTRAINT `FK_lwoiyu0ti935wwepvbxqavfwc` FOREIGN KEY (`idUsuario`) REFERENCES `av` (`idAV`);

--
-- Filtros para la tabla `av_usuarioscompartidos`
--
ALTER TABLE `av_usuarioscompartidos`
  ADD CONSTRAINT `FK_6fs67215r98r91xul672y3a8k` FOREIGN KEY (`usuariosCompartidos_idUsuario`) REFERENCES `usuario` (`idUsuario`),
  ADD CONSTRAINT `FK_mhgfput6v6wtbj2fex9saopha` FOREIGN KEY (`AV_idAV`) REFERENCES `av` (`idAV`);

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

--
-- Filtros para la tabla `usuario_avcompartidos`
--
ALTER TABLE `usuario_avcompartidos`
  ADD CONSTRAINT `FK_31aoc7x2q3fbs7vhqawyw82sm` FOREIGN KEY (`AVcompartidos_idAV`) REFERENCES `av` (`idAV`),
  ADD CONSTRAINT `FK_jm0y6di4c3imh9nq162xtg3db` FOREIGN KEY (`Usuario_idUsuario`) REFERENCES `usuario` (`idUsuario`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
