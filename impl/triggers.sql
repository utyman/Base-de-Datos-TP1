delimiter $$

drop trigger if exists agregar_vehiculo $$

CREATE TRIGGER agregar_vehiculo 
AFTER insert ON vehiculos
FOR EACH ROW BEGIN
    if (@DESACTIVA_TRIGGER IS NULL) then
	set @DESACTIVA_TRIGGER = 1;
	if (new.situacion = 0) then
		insert into vehiculos_en_reparacion values(new.patente, current_date());
	end if;
	set @DESACTIVA_TRIGGER = NULL;
    end if;
end $$ 
	

drop trigger if exists agregar_vehiculo_reparacion $$

CREATE TRIGGER agregar_vehiculo_reparacion
AFTER insert ON vehiculos_en_reparacion
FOR EACH ROW BEGIN
    if (@DESACTIVA_TRIGGER IS NULL) then
	set @DESACTIVA_TRIGGER = 1;
	update vehiculos set situacion = 0 where patente = new.patente;
	set @DESACTIVA_TRIGGER = NULL;
    end if;
end $$ 


delimiter $$

drop trigger if exists borrar_vehiculo $$

CREATE TRIGGER borrar_vehiculo
before delete ON vehiculos
FOR EACH ROW BEGIN
    if (@DESACTIVA_TRIGGER IS NULL) then
	set @DESACTIVA_TRIGGER = 1;
	if (old.situacion = 0) then
		delete from vehiculos_en_reparacion where patente = old.patente;
	end if;
	set @DESACTIVA_TRIGGER = NULL;
    end if;
end $$ 

delimiter $$

drop trigger if exists borrar_vehiculo_reparacion $$

CREATE TRIGGER borrar_vehiculo_reparacion
after delete ON vehiculos_en_reparacion
FOR EACH ROW BEGIN
    if (@DESACTIVA_TRIGGER IS NULL) then
      set @DESACTIVA_TRIGGER = 1;
      update vehiculos set situacion = 1 where patente = old.patente;
      set @DESACTIVA_TRIGGER = NULL;
    end if;
end $$ 


delimiter $$

drop trigger if exists update_vehiculo $$

CREATE TRIGGER update_vehiculo 
before update ON vehiculos
FOR EACH ROW BEGIN
	if (@DESACTIVA_TRIGGER IS NULL) then
	  set @DESACTIVA_TRIGGER = 1;
	  if (new.situacion = 0) then
		  insert into vehiculos_en_reparacion values (new.patente, current_date()); 
	  else
		  delete from vehiculos_en_reparacion where patente = old.patente; 
	  end if;
	  set @DESACTIVA_TRIGGER = NULL;
	end if;
end $$ 

delimiter $$
CREATE TRIGGER tres_choferes
before insert ON asignaciones_chofer
FOR EACH ROW BEGIN
    set @choferes = (select count(*) from asignaciones_chofer where id_viaje = new.id_viaje);
    if (@choferes >= 3) then
    	CALL trigger_error_ya_hay_tres_choferes_asignados;
    end if;
end $$
