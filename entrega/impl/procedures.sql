DELIMITER $$
drop procedure if exists bases.RecorridosTodasRutasUsadas $$
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

