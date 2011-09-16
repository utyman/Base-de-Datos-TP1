INSERT INTO marcas (id_marca, nombre) VALUES 
  (1, 'RENAULT'), 
  (2, 'MERCEDES BENZ'), 
  (3, 'FORD');

INSERT INTO modelos (id_modelo, nombre, id_marca) VALUES 
  (1, 'M1FZ', 1),
  (2, 'M1FZ-2', 1),
  (1, 'MB34', 2),
  (2, 'MB48', 2),
  (1, 'F100', 3),
  (2, 'F200', 3);

INSERT INTO vehiculos (patente, ano, situacion, id_modelo, id_marca, fecha_inicio_servicio) VALUES
  ('CFK-678', 1999, 1, 1, 1, '2001-10-09'),
  ('JMM-199', 2007, 0, 2, 3, '2008-10-04'),
  ('GGA-592', 2006, 1, 1, 2, '2007-07-23'),
  ('ABR-712', 2001, 1, 1, 2, '2002-06-17'),
  ('PKM-729', 1999, 1, 1, 3, '2003-11-01');

INSERT INTO ciudades VALUES 
  (1,'Buenos Aires'),
  (2,'La Plata'),
  (3,'Mendoza'),
  (4,'Mar del Plata'),
  (5,'Dolores'),
  (6,'Santiago del Estero');

INSERT INTO choferes VALUES 
  ('11236455','44444444','1980-05-05','Rincon 555','Bruce','Sterling'),
  ('11263789','56666666','1980-01-01','Rincon 543','Gilbert Keith','Chesterton'),
  ('23555555','76666666','1980-05-05','Rincon 222','William','Gibson'),
  ('29152731','49420845','1981-06-11','Rincon 545','Fabian Sergio','Gonzalez'),
  ('33222333','45555555','2000-04-11','Rincon 542','Juan Alberto','Badia'),
  ('24920110','47789921','1981-07-19','Yatay 1218','Alanis', 'Turinga');

INSERT INTO licencias VALUES 
  (11236455,'2009-01-01','D1','sin obs.','2011-01-01 00:00:00','11236455','2020-05-05 00:00:00'),
  (29152731,'2010-01-01','D1','...','2011-01-01 00:00:00','29152731','2020-05-05 00:00:00'),
  (29152731,'2009-01-01','B1','...','2010-01-01 00:00:00','29152731','2020-05-05 00:00:00'),
  (55555555,'2010-01-01','B1','Usa monoculo','2011-01-01 00:00:00','29152731','2013-05-05 00:00:00');

INSERT INTO condiciones_climaticas VALUES 
  (1,'Granizo'),
  (2,'Nublado'),
  (3,'Frio y seco'),
  (4,'Lluvia'),
  (5,'Neblina'),
  (6,'Niebla');

INSERT INTO periodos_ano VALUES 
  (1,'otoño'),
  (2,'invierno'),
  (3,'primavera'),
  (4,'verano');

INSERT INTO recorridos VALUES 
  (1,1,2,'Rincon 545','Calle 4 45'),
  (2,1,3,'Sarandi 444','Libertador 2003'),
  (3,1,4,'Sarandi 333','Libertador 2222'),
  (4,1,5,'Sarandi 222','Calle 5 55');

INSERT INTO rutas VALUES 
  (1,1,23,24,2,'Derecho por Rincon'),
  (1,2,35,4,0,'Sarandi-Yatay-Libertador'),
  (1,3,45,4,0,'Sarandi-Yatay-Libertador'),
  (1,4,15,42,2,'Sarandi-AUBLP-Calle 5'),
  (2,1,35,34,1,'Rincon hasta Malabia'),
  (2,2,55,40,0,'Sarandi-Charcas-Libertador'),
  (2,4,25,41,3,'Sarandi-Charcas-AU'),
  (3,4,12,14,2,'Sarandi-Yatay-AU');

INSERT INTO situaciones_climaticas VALUES 
  (1,1,1),
  (1,1,2),
  (1,1,3),
  (1,2,4);

