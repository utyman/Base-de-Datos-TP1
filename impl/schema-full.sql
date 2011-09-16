Warning: The option '--all' is deprecated and will be removed in a future release. Please use --create-options instead.
-- MySQL dump 10.13  Distrib 5.1.54, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: bases
-- ------------------------------------------------------
-- Server version	5.1.54-1ubuntu4

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `asignaciones_chofer`
--

DROP TABLE IF EXISTS `asignaciones_chofer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asignaciones_chofer` (
  `dni` varchar(10) NOT NULL,
  `id_viaje` int(11) NOT NULL,
  PRIMARY KEY (`dni`,`id_viaje`),
  KEY `viajes_asignacion_constraint` (`id_viaje`),
  CONSTRAINT `dni_asignacion_constraint` FOREIGN KEY (`dni`) REFERENCES `choferes` (`dni`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `viajes_asignacion_constraint` FOREIGN KEY (`id_viaje`) REFERENCES `viajes` (`id_viaje`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asignaciones_chofer`
--

LOCK TABLES `asignaciones_chofer` WRITE;
/*!40000 ALTER TABLE `asignaciones_chofer` DISABLE KEYS */;
/*!40000 ALTER TABLE `asignaciones_chofer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `choferes`
--

DROP TABLE IF EXISTS `choferes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `choferes` (
  `dni` varchar(10) NOT NULL,
  `telefono` varchar(20) NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `domicilio` varchar(30) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `apellido` varchar(100) NOT NULL,
  PRIMARY KEY (`dni`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `choferes`
--

LOCK TABLES `choferes` WRITE;
/*!40000 ALTER TABLE `choferes` DISABLE KEYS */;
/*!40000 ALTER TABLE `choferes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ciudades`
--

DROP TABLE IF EXISTS `ciudades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ciudades` (
  `id_ciudad` int(11) NOT NULL,
  `nombre_ciudad` varchar(30) NOT NULL,
  PRIMARY KEY (`id_ciudad`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ciudades`
--

LOCK TABLES `ciudades` WRITE;
/*!40000 ALTER TABLE `ciudades` DISABLE KEYS */;
/*!40000 ALTER TABLE `ciudades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `condiciones_climaticas`
--

DROP TABLE IF EXISTS `condiciones_climaticas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `condiciones_climaticas` (
  `id_condicion_climatica` int(11) NOT NULL,
  `descripcion` varchar(100) NOT NULL,
  PRIMARY KEY (`id_condicion_climatica`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `condiciones_climaticas`
--

LOCK TABLES `condiciones_climaticas` WRITE;
/*!40000 ALTER TABLE `condiciones_climaticas` DISABLE KEYS */;
/*!40000 ALTER TABLE `condiciones_climaticas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `licencias`
--

DROP TABLE IF EXISTS `licencias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `licencias` (
  `nro_licencia` int(11) NOT NULL,
  `fecha_otorgamiento` date NOT NULL,
  `tipo` varchar(100) NOT NULL,
  `observaciones` varchar(100) NOT NULL,
  `fecha_renovacion` datetime NOT NULL,
  `dni_chofer` varchar(10) NOT NULL,
  `fecha_vencimiento` datetime NOT NULL,
  PRIMARY KEY (`nro_licencia`,`fecha_otorgamiento`,`tipo`),
  KEY `dnis_constraint` (`dni_chofer`),
  CONSTRAINT `dnis_constraint` FOREIGN KEY (`dni_chofer`) REFERENCES `choferes` (`dni`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `licencias`
--

LOCK TABLES `licencias` WRITE;
/*!40000 ALTER TABLE `licencias` DISABLE KEYS */;
/*!40000 ALTER TABLE `licencias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `marcas`
--

DROP TABLE IF EXISTS `marcas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `marcas` (
  `id_marca` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`id_marca`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `marcas`
--

LOCK TABLES `marcas` WRITE;
/*!40000 ALTER TABLE `marcas` DISABLE KEYS */;
/*!40000 ALTER TABLE `marcas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `modelos`
--

DROP TABLE IF EXISTS `modelos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `modelos` (
  `id_modelo` int(11) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `id_marca` int(11) NOT NULL,
  PRIMARY KEY (`id_modelo`,`id_marca`) USING BTREE,
  KEY `marca_fk_constraint` (`id_marca`),
  CONSTRAINT `marca_fk_constraint` FOREIGN KEY (`id_marca`) REFERENCES `marcas` (`id_marca`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `modelos`
--

LOCK TABLES `modelos` WRITE;
/*!40000 ALTER TABLE `modelos` DISABLE KEYS */;
/*!40000 ALTER TABLE `modelos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `periodos_ano`
--

DROP TABLE IF EXISTS `periodos_ano`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `periodos_ano` (
  `id_periodo_ano` int(11) NOT NULL,
  `descripcion` varchar(100) NOT NULL,
  PRIMARY KEY (`id_periodo_ano`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `periodos_ano`
--

LOCK TABLES `periodos_ano` WRITE;
/*!40000 ALTER TABLE `periodos_ano` DISABLE KEYS */;
/*!40000 ALTER TABLE `periodos_ano` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recorridos`
--

DROP TABLE IF EXISTS `recorridos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recorridos` (
  `id_recorrido` int(11) NOT NULL,
  `id_ciudad_origen` int(11) NOT NULL,
  `id_ciudad_destino` int(11) NOT NULL,
  `direccion_origen` varchar(100) NOT NULL,
  `direccion_destino` varchar(100) NOT NULL,
  PRIMARY KEY (`id_recorrido`),
  KEY `ciudad_origen_constraint` (`id_ciudad_origen`),
  KEY `ciudad_destino_constraint` (`id_ciudad_destino`),
  CONSTRAINT `ciudad_destino_constraint` FOREIGN KEY (`id_ciudad_destino`) REFERENCES `ciudades` (`id_ciudad`),
  CONSTRAINT `ciudad_origen_constraint` FOREIGN KEY (`id_ciudad_origen`) REFERENCES `ciudades` (`id_ciudad`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recorridos`
--

LOCK TABLES `recorridos` WRITE;
/*!40000 ALTER TABLE `recorridos` DISABLE KEYS */;
/*!40000 ALTER TABLE `recorridos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rutas`
--

DROP TABLE IF EXISTS `rutas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rutas` (
  `id_ruta` int(11) NOT NULL,
  `id_recorrido` int(11) NOT NULL,
  `longitud` int(11) NOT NULL,
  `tiempo_estimado` int(11) NOT NULL,
  `cantidad_peajes` int(11) NOT NULL,
  `descripcion` varchar(100) NOT NULL,
  PRIMARY KEY (`id_ruta`,`id_recorrido`) USING BTREE,
  KEY `recorrido_constraint` (`id_recorrido`),
  CONSTRAINT `recorrido_constraint` FOREIGN KEY (`id_recorrido`) REFERENCES `recorridos` (`id_recorrido`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rutas`
--

LOCK TABLES `rutas` WRITE;
/*!40000 ALTER TABLE `rutas` DISABLE KEYS */;
/*!40000 ALTER TABLE `rutas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `situaciones_climaticas`
--

DROP TABLE IF EXISTS `situaciones_climaticas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `situaciones_climaticas` (
  `id_ruta` int(11) NOT NULL,
  `id_condicion_climatica` int(11) NOT NULL,
  `id_periodo_ano` int(11) NOT NULL,
  PRIMARY KEY (`id_ruta`,`id_condicion_climatica`,`id_periodo_ano`) USING BTREE,
  KEY `condicion_climatica_constraint` (`id_condicion_climatica`),
  CONSTRAINT `condicion_climatica_constraint` FOREIGN KEY (`id_condicion_climatica`) REFERENCES `condiciones_climaticas` (`id_condicion_climatica`),
  CONSTRAINT `id_ruta_constraint` FOREIGN KEY (`id_ruta`) REFERENCES `rutas` (`id_ruta`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `situaciones_climaticas`
--

LOCK TABLES `situaciones_climaticas` WRITE;
/*!40000 ALTER TABLE `situaciones_climaticas` DISABLE KEYS */;
/*!40000 ALTER TABLE `situaciones_climaticas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test` (
  `id_tipo_test` int(11) NOT NULL,
  `dni` varchar(10) NOT NULL,
  `id_viaje` int(11) NOT NULL,
  `resultado` varchar(100) NOT NULL,
  `fecha_realizacion` datetime NOT NULL,
  PRIMARY KEY (`id_tipo_test`,`dni`,`id_viaje`),
  KEY `viaje_test_constraint` (`id_viaje`),
  KEY `dni_test_constraint` (`dni`),
  CONSTRAINT `dni_test_constraint` FOREIGN KEY (`dni`) REFERENCES `choferes` (`dni`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tipo_test_constraint` FOREIGN KEY (`id_tipo_test`) REFERENCES `tipo_test` (`id_tipo_test`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `viaje_test_constraint` FOREIGN KEY (`id_viaje`) REFERENCES `viajes` (`id_viaje`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_test`
--

DROP TABLE IF EXISTS `tipo_test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipo_test` (
  `id_tipo_test` int(11) NOT NULL,
  `descripcion` varchar(100) NOT NULL,
  PRIMARY KEY (`id_tipo_test`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_test`
--

LOCK TABLES `tipo_test` WRITE;
/*!40000 ALTER TABLE `tipo_test` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipo_test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehiculos`
--

DROP TABLE IF EXISTS `vehiculos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vehiculos` (
  `patente` varchar(10) NOT NULL,
  `ano` int(11) NOT NULL,
  `situacion` tinyint(1) NOT NULL,
  `id_modelo` int(11) NOT NULL,
  `id_marca` int(11) NOT NULL,
  `fecha_inicio_servicio` datetime NOT NULL,
  PRIMARY KEY (`patente`),
  KEY `veh_modelo_constraint` (`id_modelo`,`id_marca`),
  CONSTRAINT `veh_modelo_constraint` FOREIGN KEY (`id_modelo`, `id_marca`) REFERENCES `modelos` (`id_modelo`, `id_marca`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehiculos`
--

LOCK TABLES `vehiculos` WRITE;
/*!40000 ALTER TABLE `vehiculos` DISABLE KEYS */;
/*!40000 ALTER TABLE `vehiculos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehiculos_en_reparacion`
--

DROP TABLE IF EXISTS `vehiculos_en_reparacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vehiculos_en_reparacion` (
  `patente` varchar(10) NOT NULL,
  `fecha_ingreso_taller` datetime NOT NULL,
  PRIMARY KEY (`patente`),
  CONSTRAINT `patenet_fk_constraint` FOREIGN KEY (`patente`) REFERENCES `vehiculos` (`patente`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehiculos_en_reparacion`
--

LOCK TABLES `vehiculos_en_reparacion` WRITE;
/*!40000 ALTER TABLE `vehiculos_en_reparacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `vehiculos_en_reparacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `viajes`
--

DROP TABLE IF EXISTS `viajes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `viajes` (
  `id_viaje` int(11) NOT NULL,
  `id_vehiculo` varchar(10) NOT NULL,
  `contingencias` varchar(300) NOT NULL,
  `fecha_hora_partida` datetime NOT NULL,
  `id_recorrido` int(11) NOT NULL,
  `fecha_hora_llegada_estimada` datetime NOT NULL,
  PRIMARY KEY (`id_viaje`),
  KEY `new_fk_constraint` (`id_vehiculo`),
  KEY `recorridos_constraint` (`id_recorrido`),
  CONSTRAINT `new_fk_constraint` FOREIGN KEY (`id_vehiculo`) REFERENCES `vehiculos` (`patente`),
  CONSTRAINT `recorridos_constraint` FOREIGN KEY (`id_recorrido`) REFERENCES `recorridos` (`id_recorrido`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `viajes`
--

LOCK TABLES `viajes` WRITE;
/*!40000 ALTER TABLE `viajes` DISABLE KEYS */;
/*!40000 ALTER TABLE `viajes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `viajes_realizados`
--

DROP TABLE IF EXISTS `viajes_realizados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `viajes_realizados` (
  `id_viaje` int(11) NOT NULL,
  `fecha_hora_llegada` datetime NOT NULL,
  `id_ruta` int(11) NOT NULL,
  PRIMARY KEY (`id_viaje`),
  KEY `rutas_constraint` (`id_ruta`),
  CONSTRAINT `rutas_constraint` FOREIGN KEY (`id_ruta`) REFERENCES `rutas` (`id_ruta`),
  CONSTRAINT `viajes_realizados_constraint` FOREIGN KEY (`id_viaje`) REFERENCES `viajes` (`id_viaje`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `viajes_realizados`
--

LOCK TABLES `viajes_realizados` WRITE;
/*!40000 ALTER TABLE `viajes_realizados` DISABLE KEYS */;
/*!40000 ALTER TABLE `viajes_realizados` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-09-16  4:44:28
