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
  ('CFK-678', 1999, 1, 1, 1, '2001-10-09');