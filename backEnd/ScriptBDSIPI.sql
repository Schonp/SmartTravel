# BASE DE DATOS DE SIPI

CREATE DATABASE seminario;
DROP DATABASE seminario;

# VISTAS 

SELECT * FROM seminario.usuarios;
SELECT * FROM seminario.viajes;
SELECT * FROM seminario.miembros;
SELECT * FROM seminario.gastos;
SELECT * FROM seminario.documentos;
SELECT * FROM seminario.actividades;
SELECT * FROM seminario.destinos;


# MODIFICACIONES

update seminario.miembros
set  miembros.balance = 0
where miembros.id != 100;