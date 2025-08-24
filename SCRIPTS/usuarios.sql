USE gemaplusdb;

--Primer usuario
CREATE USER 'AsesorVentas'@'localhost' IDENTIFIED BY '2025bd1pa';

--Permisos del primer usuario

GRANT SELECT ON gemaplusdb.Producto TO 'AsesorVentas'@'localhost';
GRANT SELECT ON gemaplusdb.reporteHistorialDeComprasDeClientes TO 'est2'@'localhost';
GRANT SELECT ON gemaplusdb.reporteCategoriaMasVendida TO 'AsesorVentas'@'localhost';

-- Segundo usuario 
CREATE USER 'Gerente'@'localhost' IDENTIFIED BY '2025bd2pa';

-- Permisos del segundo usuario 
GRANT ALL PRIVILEGES ON gemaplusdb.Cliente TO 'Gerente'@'localhost';
GRANT ALL PRIVILEGES ON gemaplusdb.Pedido TO 'Gerente'@'localhost';
GRANT ALL PRIVILEGES ON gemaplusdb.Producto TO 'Gerente'@'localhost';
GRANT SELECT ON gemaplusdb.vw_clientes_mas_pedidos TO 'Gerente'@'localhost';
GRANT SELECT ON gemaplusdb.vw_clientes_historial_compras TO 'Gerente'@'localhost';

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
CREATE USER "EncargadoInventario"@"localhost" IDENTIFIED BY "Bodega";

--Permisos del quinto usuario
GRANT ALL PRIVILEGES ON gemaplusdb.Categoria TO "EncargadoInventario"@"localhost";
GRANT ALL PRIVILEGES ON gemaplusdb.producto TO "EncargadoInventario"@"localhost";
GRANT SELECT ON gemaplusdb.detalle TO "EncargadoInventario"@"localhost";
