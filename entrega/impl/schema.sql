DROP DATABASE IF EXISTS viajes;
CREATE DATABASE viajes;

USE viajes;

CREATE TABLE "ciudades" (
    "id" integer NOT NULL PRIMARY KEY,
    "nombre" text,
);

CREATE TABLE "recorridos" (
    "id" integer NOT NULL PRIMARY KEY,
    "ciudad_origen_id" integer REFERENCES "ciudades" ("id"),
    "direccion_origen" text,
    "ciudad_destino_id" integer REFERENCES "ciudades" ("id"),
    "direccion_destino" text
);

CREATE TABLE "rutas" (
    "id" integer NOT NULL PRIMARY KEY,
    "recorrido_id" integer REFERENCES "recorridos" ("id"),
    "descripcion" text,
    "kms" integer NOT NULL,
    "tiempo_estimado" integer NOT NULL,
    "nro_peajes" integer NOT NULL
);

CREATE TABLE "situacion_climatica" (
    "ruta_id" integer REFERENCES "rutas" ("id"),
    "periodo_id" integer REFERENCES "periodos" ("id"),
    "condicion_climatica_id" integer REFERENCES "condiciones_climaticas" ("id")
)
    