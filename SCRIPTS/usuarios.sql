USE gemaplusdb;

--Primer usuario
CREATE USER 'AsesorVentas'@'localhost' IDENTIFIED BY '2025bd1pa';

--Permisos del primer usuario

GRANT SELECT ON gemaplusdb.Producto TO 'AsesorVentas'@'localhost';
GRANT SELECT ON gemaplusdb.reporteHistorialDeComprasDeClientes TO 'est2'@'localhost';
GRANT SELECT ON gemaplusdb.reporteCategoriaMasVendida TO 'AsesorVentas'@'localhost';

--Segundo usuario
CREATE USER 'est2'@'localhost' IDENTIFIED BY '2025bd2pa';

--Permisos del segundo usuario
GRANT EXECUTE ON PROCEDURE gemaplusdb.insertarCliente TO 'est2'@'localhost';
GRANT SELECT, UPDATE ON gemaplusdb.Pedido TO 'est2'@'localhost';

GRANT SELECT ON gemaplusdb.reporteHistorialDeComprasDeClientes TO 'est2'@'localhost';

--Tercer usuario
CREATE USER "tesorero"@"localhost" IDENTIFIED BY "managementdb";

--Permisos del tercer usuario
GRANT ALL PRIVILEGES ON gemaplusdb.pago TO "tesorero"@"localhost";
GRANT ALL PRIVILEGES ON gemaplusdb.reportePagosDelMes TO "tesorero"@"localhost";
GRANT SELECT ON gemaplusdb.cliente TO "tesorero"@"localhost";
GRANT SELECT ON gemaplusdb.pedido TO "tesorero"@"localhost";
GRANT SELECT ON gemaplusdb.detalle TO "tesorero"@"localhost";

--Cuarto usuario
CREATE USER "repartidor"@"localhost" IDENTIFIED BY "deliverydb";

--Permisos del cuarto usuario
GRANT SELECT ON gemaplusdb.entrega TO "repartidor"@"localhost";
GRANT SELECT ON gemaplusdb.pedido TO "repartidor"@"localhost";
GRANT SELECT ON gemaplusdb.cliente TO "repartidor"@"localhost";

--Quinto usuario

--Permisos del quinto usuario