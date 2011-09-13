delimiter $$

use bases $$
drop trigger if exists bases.agregar_vehiculo $$

CREATE TRIGGER agregar_vehiculo 
AFTER insert ON vehiculos
FOR EACH ROW BEGIN
	if (new.situacion = 0) then
		insert into vehiculos_en_reparacion values(new.patente, current_date());
	else
		insert into vehiculos_en_uso values(new.patente);
	end if;
end $$ 
	

delimiter $$

use bases $$
drop trigger if exists bases.borrar_vehiculo $$

CREATE TRIGGER borrar_vehiculo 
before delete ON vehiculos
FOR EACH ROW BEGIN
	if (old.situacion = 0) then
		delete from vehiculos_en_reparacion where patente = old.patente;
	else
		delete from vehiculos_en_uso where patente = old.patente;
	end if;
end $$ 


delimiter $$

use bases $$
drop trigger if exists bases.update_vehiculo $$

CREATE TRIGGER update_vehiculo 
before update ON vehiculos
FOR EACH ROW BEGIN
	if (new.situacion = 0) then
		delete from vehiculos_en_uso where patente = old.patente; 
		insert into vehiculos_en_reparacion values (new.patente, current_date()); 
	else
		delete from vehiculos_en_reparacion where patente = old.patente; 
		insert into vehiculos_en_uso values (new.patente); 
	end if;
end $$ 
