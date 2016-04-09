-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 21-07-2014 a las 14:56:49
-- Versión del servidor: 5.6.12-log
-- Versión de PHP: 5.4.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `smart`
--
CREATE DATABASE IF NOT EXISTS `smart` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `smart`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estacionamiento`
--

CREATE TABLE IF NOT EXISTS `estacionamiento` (
  `idEstacionamiento` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(100) DEFAULT NULL,
  `Capacidad` int(11) DEFAULT NULL,
  `Puestos_inicial` int(11) DEFAULT NULL,
  `Puestos_reservados` int(11) DEFAULT NULL,
  `Puestos_disponibles` int(11) DEFAULT NULL,
  `Puestos_ocupados` int(11) NOT NULL,
  `trafico` int(11) NOT NULL,
  PRIMARY KEY (`idEstacionamiento`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Volcado de datos para la tabla `estacionamiento`
--

INSERT INTO `estacionamiento` (`idEstacionamiento`, `Nombre`, `Capacidad`, `Puestos_inicial`, `Puestos_reservados`, `Puestos_disponibles`, `Puestos_ocupados`, `trafico`) VALUES
(1, 'S1', 150, 150, 0, 9, 141, 0),
(2, 'S2', 150, 150, 0, 0, 0, 0),
(3, 'S3', 150, 150, 0, 99, 0, 0),
(4, 'S4', 150, 150, 0, 0, 0, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `publicidad`
--

CREATE TABLE IF NOT EXISTS `publicidad` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(100) NOT NULL,
  `ruta` varchar(100) NOT NULL,
  `mostrar` int(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Volcado de datos para la tabla `publicidad`
--

INSERT INTO `publicidad` (`id`, `descripcion`, `ruta`, `mostrar`) VALUES
(1, 'rolex', 'img/publicidad2.jpg', 0),
(2, 'movistar', 'img/publicidad3.jpg', 0),
(3, 'papa', 'img/publicidad.jpg', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE IF NOT EXISTS `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `usuario` varchar(50) NOT NULL,
  `clave` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `usuario`, `clave`) VALUES
(1, 'admin', 'admin');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
