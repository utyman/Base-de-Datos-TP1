DELIMITER $$
drop procedure if exists RecorridosTodasRutasUsadas $$
	CREATE PROCEDURE RecorridosTodasRutasUsadas(IN ano integer)
		BEGIN
		       SELECT r
	       	       FROM recorridos r
	       	       WHERE NOT EXISTS
			 (SELECT rut.id_ruta
			  FROM rutas rut
			  WHERE NOT EXISTS 
				(SELECT v.id_ruta
				 FROM viajes_realizados v
				 WHERE YEAR(v.fechaLlegada) = ano
				       AND v.id_ruta = rut.id_rutaa
				       AND v.id_recorrido = r.recorrido)
			 ) AND ((SELECT count(rut.id_ruta) FROM rutas WHERE rut.id_recorrido = r.id_recorrido) = 1);
		END $$

drop function if exists `promedioViajesRealizadosPorAno` $$
CREATE DEFINER=`root`@`localhost` FUNCTION  `promedioViajesRealizadosPorAno`
								(patente varchar(10)) RETURNS int(11)
begin
    DECLARE ano_inicio_servicio int;
    DECLARE viajes_realizados int;
    SELECT count(*) into viajes_realizados FROM viajes_realizados 
    		NATURAL JOIN viajes v WHERE  v.id_vehiculo = patente;
    SELECT YEAR(fecha_inicio_servicio)  into ano_inicio_servicio 
    		FROM vehiculos v where v.patente = patente;
    RETURN (viajes_realizados / (YEAR(NOW()) - ano_inicio_servicio + 1)) ;
end $$

drop procedure if exists `promedioViajesYEstado` $$
CREATE DEFINER=`root`@`localhost` PROCEDURE  `promedioViajesYEstado`()
BEGIN
		SELECT (SELECT promedioViajesRealizadosPorAno(patente)), situacion from vehiculos;
		END $$



DROP PROCEDURE IF EXISTS `ChoferesTodosLosVehiculosUltimoSemestre` $$
DROP FUNCTION IF EXISTS `agregardosanos` $$
DROP FUNCTION IF EXISTS `agregarseismeses` $$
CREATE DEFINER=`root`@`localhost` PROCEDURE  
		`ChoferesTodosLosVehiculosUltimoSemestre`()
BEGIN
		       SELECT *
	       	       FROM choferes c
	       	       WHERE NOT EXISTS
			 (SELECT *
			  FROM vehiculos v
			  WHERE (SELECT agregardosanos(v.patente)) > NOW() AND NOT EXISTS (
				SELECT *
				 FROM viajes_realizados v1
				 NATURAL JOIN viajes v2
				 WHERE (SELECT agregarseismeses(v1.fecha_hora_llegada)) > NOW()
				       AND v2.id_vehiculo = v.patente
				 AND EXISTS (
				      SELECT * FROM asignaciones_chofer WHERE
					 dni = c.dni AND id_viaje = v2.id_viaje
				)      
				) 
			 );
		END
		
		CREATE DEFINER=`root`@`localhost` FUNCTION  
		`agregardosanos`(id_vehiculo varchar(10)) RETURNS datetime
BEGIN
		return DATE_ADD((SELECT fecha_inicio_servicio FROM vehiculos where patente = id_vehiculo), 
						INTERVAL 2 YEAR);
		END
		

		CREATE DEFINER=`root`@`localhost` FUNCTION  `agregarseismeses`(fecha DATETIME) 
		RETURNS datetime
BEGIN
		return DATE_ADD(fecha, INTERVAL 6 MONTH);
		END

